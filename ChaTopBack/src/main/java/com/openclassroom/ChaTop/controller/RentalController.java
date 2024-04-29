package com.openclassroom.ChaTop.controller;

import com.openclassroom.ChaTop.dto.RentalDto;
import com.openclassroom.ChaTop.mapper.RentalMapper;
import com.openclassroom.ChaTop.models.Rental;
import com.openclassroom.ChaTop.service.RentalService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  public ResponseEntity<?> findAll() {
    List<Rental> rentals =this.rentalService.findAll();
    return ResponseEntity.ok().body(this.rentalMapper.toDto(rentals));
  }

  @PostMapping()
  public ResponseEntity<?> create(@Valid @RequestBody RentalDto rentalDto) {
    log.info(rentalDto);

    Rental rental = this.rentalService.create(this.rentalMapper.toEntity(rentalDto));

    log.info(rental);
    return ResponseEntity.ok().body(this.rentalMapper.toDto(rental));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable("id") String id) {
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
  public ResponseEntity<?> update(@PathVariable("id") String id, @Valid @RequestBody RentalDto rentalDto) {
    try {
     Rental rental = this.rentalService.update(Long.parseLong(id), this.rentalMapper.toEntity(rentalDto));

      return ResponseEntity.ok().body(this.rentalMapper.toDto(rental));
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().build();
    }
  }

}
