package com.openclassroom.ChaTop.service;

import com.openclassroom.ChaTop.models.Rental;
import com.openclassroom.ChaTop.models.User;
import com.openclassroom.ChaTop.repository.RentalRepository;
import com.openclassroom.ChaTop.repository.UserRepository;
import com.openclassroom.ChaTop.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
@Service
public class RentalService {
  @Autowired
  private RentalRepository rentalRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JwtUtils jwtUtils;
  @Autowired
  private FileStorageService fileStorageService;

  public List<Rental> findAll() {
    return this.rentalRepository.findAll();
  }

  public Rental findById(Long id) {
    return this.rentalRepository.findById(id).orElse(null);
  }

  public Rental create(String name, float surface, float price, String description, String picturePath, User owner) {
    var dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
    String formattedDateString = owner.getCreated_at().format(dateTimeFormatter);
    Rental rental = Rental.builder()
            .owner(owner)
            .name(name)
            .surface(surface)
            .price(price)
            .description(description)
            .picture(picturePath)
            .created_at(owner.getCreated_at())
            .build();
    return this.rentalRepository.save(rental);
  }

  public Rental update(Long id, String name, float surface, float price, String description, User owner) {
    Rental existingRental = this.rentalRepository.findById(id).orElse(null);
    if (existingRental != null) {
      existingRental.setName(name);
      existingRental.setSurface(surface);
      existingRental.setPrice(price);
      existingRental.setDescription(description);
      existingRental.setOwner(owner);
      existingRental.setCreated_at(owner.getCreated_at());
      return this.rentalRepository.save(existingRental);
    }
    throw new RuntimeException("Rental not found");
  }
}
