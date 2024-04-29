package com.openclassroom.ChaTop.service;

import com.openclassroom.ChaTop.models.Message;
import com.openclassroom.ChaTop.models.Rental;
import com.openclassroom.ChaTop.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
