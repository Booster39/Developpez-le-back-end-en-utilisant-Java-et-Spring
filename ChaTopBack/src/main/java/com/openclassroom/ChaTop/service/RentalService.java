package com.openclassroom.ChaTop.service;

import com.openclassroom.ChaTop.models.Rental;
import com.openclassroom.ChaTop.repository.RentalRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class RentalService {
  private final RentalRepository rentalRepository;

  public RentalService(RentalRepository rentalRepository) {this.rentalRepository = rentalRepository;}
  public List<Rental> findAll() {
    return this.rentalRepository.findAll();
  }

  public Rental findById(Long id) {
    return this.rentalRepository.findById(id).orElse(null);
  }

  public Rental create(Rental rental) {
    return this.rentalRepository.save(rental);
  }

  public Rental update(Long id, Rental rental) {
    rental.setId(id);
    return this.rentalRepository.save(rental);
  }

}
