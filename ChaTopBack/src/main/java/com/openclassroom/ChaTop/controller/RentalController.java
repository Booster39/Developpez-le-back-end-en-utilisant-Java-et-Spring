package com.openclassroom.ChaTop.controller;

import com.openclassroom.ChaTop.dto.RentalDto;
import com.openclassroom.ChaTop.mapper.RentalMapper;
import com.openclassroom.ChaTop.models.Rental;
import com.openclassroom.ChaTop.models.User;
import com.openclassroom.ChaTop.payload.response.StringResponse;
import com.openclassroom.ChaTop.repository.RentalRepository;
import com.openclassroom.ChaTop.repository.UserRepository;
import com.openclassroom.ChaTop.security.jwt.JwtUtils;
import com.openclassroom.ChaTop.service.FileStorageService;
import com.openclassroom.ChaTop.service.RentalService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/rentals")
@Log4j2
@Tag(name = "Rentals", description = "API pour la gestion des locations")
public class RentalController {

  @Autowired
  private RentalMapper rentalMapper;

  @Autowired
  private RentalService rentalService;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RentalRepository rentalRepository;

  private final FileStorageService fileStorageService;

  public RentalController(FileStorageService fileStorageService) {
    this.fileStorageService = fileStorageService;
  }

  @Operation(summary = "Obtenir toutes les locations", description = "Retourne une liste de toutes les locations.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Liste des locations",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalDto.class))),
    @ApiResponse(responseCode = "400", description = "Requête invalide",
      content = @Content)
  })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HashMap<String, List<RentalDto>>> findAll() {
    try {
      List<Rental> rentals = this.rentalService.findAll();
      var response = new HashMap<String, List<RentalDto>>();
      response.put("rentals", this.rentalMapper.toDto(rentals));
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(summary = "Obtenir une location par ID", description = "Retourne une location spécifique basée sur l'ID fourni.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Détails de la location",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalDto.class))),
    @ApiResponse(responseCode = "400", description = "Requête invalide",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Location non trouvée",
      content = @Content)
  })
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RentalDto> findById(@PathVariable("id") String id) {
    try {
      Rental rental = this.rentalService.findById(Long.valueOf(id));
      if (rental == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok().body(this.rentalMapper.toDto(rental));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(summary = "Créer une nouvelle location", description = "Crée une nouvelle location et retourne un message de succès.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Location créée avec succès",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = StringResponse.class))),
    @ApiResponse(responseCode = "400", description = "Requête invalide",
      content = @Content)
  })
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<StringResponse> create(
    @RequestPart("picture") MultipartFile multipartFile,
    @RequestParam("name") @NotBlank @Size(max = 63) String name,
    @RequestParam("surface") @Min(0) float surface,
    @RequestParam("price") @Min(0) float price,
    @RequestParam("description") @Size(max = 2000) String description,
    @RequestHeader(value = "Authorization", required = false) String jwt
  ) {
    try {
      String username = jwtUtils.getUserNameFromJwtToken(jwt.substring(7));
      User owner = this.userRepository.findByEmail(username)
        .orElseThrow(() -> new RuntimeException("User not found"));
      String pictureName = fileStorageService.savePicture(multipartFile);
      String portPath = "http://localhost:3001/public/";
      String picturePath = portPath + pictureName;
      var dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
      String formattedDateString = owner.getCreated_at().format(dateTimeFormatter);
      Rental candidate = Rental.builder()
        .owner(owner)
        .name(name)
        .surface(surface)
        .price(price)
        .description(description)
        .picture(picturePath)
        .created_at(owner.getCreated_at())
        .build();

      this.rentalRepository.save(candidate);
      return ResponseEntity.ok().body(new StringResponse("Rental created !"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(summary = "Mettre à jour une location existante", description = "Met à jour une location existante et retourne un message de succès.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Location mise à jour avec succès",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = StringResponse.class))),
    @ApiResponse(responseCode = "400", description = "Requête invalide",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Location non trouvée",
      content = @Content)
  })
  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> update(
    @PathVariable("id") String id,
    @RequestParam("name") @NotBlank @Size(max = 63) String name,
    @RequestParam("surface") @Min(0) float surface,
    @RequestParam("price") @Min(0) float price,
    @RequestParam("description") @Size(max = 2000) String description,
    @RequestHeader(value = "Authorization", required = false) String jwt
  ) {
    try {
      Rental existingRental = this.rentalService.findById(Long.parseLong(id));
      if (existingRental == null) {
        return ResponseEntity.notFound().build();
      }

      String username = jwtUtils.getUserNameFromJwtToken(jwt.substring(7));
      User owner = this.userRepository.findByEmail(username)
        .orElseThrow(() -> new RuntimeException("User not found"));

      existingRental.setId(Long.valueOf(id));
      existingRental.setName(name);
      existingRental.setOwner(owner);
      existingRental.setSurface(surface);
      existingRental.setPrice(price);
      existingRental.setDescription(description);
      existingRental.setCreated_at(owner.getCreated_at());

      this.rentalRepository.save(existingRental);
      return ResponseEntity.ok().body(new StringResponse("Rental updated !"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
