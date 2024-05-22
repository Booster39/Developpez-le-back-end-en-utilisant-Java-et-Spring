package com.openclassroom.ChaTop.controller;

import com.openclassroom.ChaTop.dto.RentalDto;
import com.openclassroom.ChaTop.mapper.RentalMapper;
import com.openclassroom.ChaTop.models.Rental;
import com.openclassroom.ChaTop.models.User;
import com.openclassroom.ChaTop.payload.response.MessageResponse;
import com.openclassroom.ChaTop.repository.RentalRepository;
import com.openclassroom.ChaTop.repository.UserRepository;
import com.openclassroom.ChaTop.security.jwt.JwtUtils;
import com.openclassroom.ChaTop.service.FileStorageService;
import com.openclassroom.ChaTop.service.RentalService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/rentals")
@Log4j2
public class RentalController {
  private final RentalMapper rentalMapper;
  private final RentalService rentalService;
  private final JwtUtils jwtUtils;
  private final UserRepository userRepository;
  private final RentalRepository rentalRepository;

  private final FileStorageService fileStorageService;

  public RentalController(RentalMapper rentalMapper, RentalService rentalService, JwtUtils jwtUtils, UserRepository userRepository, FileStorageService fileStorageService,
                          RentalRepository rentalRepository) {
    this.rentalMapper = rentalMapper;
    this.rentalService = rentalService;
    this.jwtUtils = jwtUtils;
    this.userRepository = userRepository;
    this.fileStorageService = fileStorageService;
    this.rentalRepository = rentalRepository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HashMap<String, List<RentalDto>>> findAll() {
   List<Rental> rentals =this.rentalService.findAll();
    var response = new HashMap<String, List<RentalDto>>();
    response.put("rentals", this.rentalMapper.toDto(rentals));
    return ResponseEntity.ok().body(response);

  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public RentalDto createRental(
    //@formatter:off
    @RequestPart("picture") MultipartFile multipartFile,
    @RequestParam("name") @NotBlank @Size(max=63) String name,
    @RequestParam("surface") @Min(0) float surface,
    @RequestParam("price") @Min(0) float price,
    @RequestParam("description") @Size(max=2000) String description,
    @RequestHeader(value="Authorization",required = false) String jwt
    //@formatter:on
  ) {
    String username = jwtUtils.getUserNameFromJwtToken(jwt.substring(7));
    User owner = this.userRepository.findByEmail(username)
      .orElseThrow(() -> new RuntimeException("User not found"));
    String picturePath = fileStorageService.savePicture(multipartFile);
    var dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);

    // Format the LocalDate to a string
    String formattedDateString = owner.getCreated_at().format(dateTimeFormatter);

    Rental candidate = Rental.builder()
      .owner(owner)
      .name(name)
      .surface(surface)
      .price(price)
      .description(description)
      .picture(picturePath)
      .build();

    Rental savedRental = rentalRepository.save(candidate);
    return rentalMapper.toDto(savedRental);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RentalDto> findById(@PathVariable("id") String id) {
    try {
      Rental rental = this.rentalService.findById(Long.valueOf(id));

      if (rental == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok().body(this.rentalMapper.toDto(rental));
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping(value = "{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HashMap<
    MessageResponse, RentalDto>> update(@PathVariable("id") String id, @Valid @RequestBody RentalDto rentalDto) {
    try {
     Rental rental = this.rentalService.update(Long.parseLong(id), this.rentalMapper.toEntity(rentalDto));
      var response = new HashMap<
        MessageResponse, RentalDto>();
      response.put(new MessageResponse("Rental updated !"), this.rentalMapper.toDto(rental));
      return ResponseEntity.ok().body(response);
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().build();
    }
  }

}
