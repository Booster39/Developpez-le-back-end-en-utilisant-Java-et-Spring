package com.openclassroom.ChaTop.controller;

import com.openclassroom.ChaTop.dto.RentalDto;
import com.openclassroom.ChaTop.mapper.RentalMapper;
import com.openclassroom.ChaTop.models.Rental;
import com.openclassroom.ChaTop.payload.response.MessageResponse;
import com.openclassroom.ChaTop.service.RentalService;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@Log4j2
public class RentalController {
  private final RentalMapper rentalMapper;
  private final RentalService rentalService;
  public RentalController(RentalMapper rentalMapper, RentalService rentalService) {
    this.rentalMapper = rentalMapper; this.rentalService = rentalService;
  }

  @GetMapping()
  public ResponseEntity<HashMap<String, List<RentalDto>>> findAll() {
    List<Rental> rentals =this.rentalService.findAll();
    var response = new HashMap<String, List<RentalDto>>();
    response.put("rentals", this.rentalMapper.toDto(rentals));
    return ResponseEntity.ok().body(response);
  }

  @PostMapping()
  public ResponseEntity<HashMap<
    MessageResponse, RentalDto>> create(@Valid @RequestBody RentalDto rentalDto) {
    log.info(rentalDto);

    Rental rental = this.rentalService.create(this.rentalMapper.toEntity(rentalDto));
    var response = new HashMap<
      MessageResponse, RentalDto>();
    response.put(new MessageResponse("Rental created !"), this.rentalMapper.toDto(rental) );
    log.info(rental);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{id}")
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

  @PutMapping("{id}")
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
