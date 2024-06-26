package com.openclassroom.ChaTop.mapper;

import com.openclassroom.ChaTop.dto.RentalDto;
import com.openclassroom.ChaTop.models.Rental;

import java.util.List;
import java.util.stream.Collectors;

import com.openclassroom.ChaTop.models.User;
import com.openclassroom.ChaTop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper implements EntityMapper<RentalDto, Rental> {

  @Autowired
  UserRepository userRepository;

  @Override
  public Rental toEntity(RentalDto dto) {
    if ( dto == null ) {
      return null;
    }

    Rental.RentalBuilder rental = Rental.builder();

    rental.id( dto.getId() );
    rental.name( dto.getName() );
    rental.surface( dto.getSurface() );
    rental.price( dto.getPrice() );
    rental.picture( dto.getPicture() );
    rental.description( dto.getDescription() );
    rental.created_at(dto.getCreated_at());
    rental.updated_at(dto.getUpdated_at());

    User owner = userRepository.findById(dto.getOwner_id()).orElse(null);
    rental.owner(owner);

    return rental.build();
  }

  @Override
  public RentalDto toDto(Rental entity) {
    if ( entity == null ) {
      return null;
    }

    RentalDto rentalDto = new RentalDto();

    rentalDto.setId( entity.getId() );
    rentalDto.setName( entity.getName() );
    rentalDto.setSurface( entity.getSurface() );
    rentalDto.setPrice( entity.getPrice() );
    rentalDto.setPicture( entity.getPicture() );
    rentalDto.setDescription( entity.getDescription() );
    rentalDto.setOwner_id(entity.getOwner().getId());
    rentalDto.setCreated_at(entity.getCreated_at());
    rentalDto.setUpdated_at(entity.getUpdated_at());



    return rentalDto;
  }

  @Override
  public List<Rental> toEntity(List<RentalDto> dtoList) {
    if ( dtoList == null ) {
      return null;
    }

    return dtoList.stream()
      .map(this::toEntity)
      .collect(Collectors.toList());
  }

  @Override
  public List<RentalDto> toDto(List<Rental> entityList) {
    if ( entityList == null ) {
      return null;
    }

    return entityList.stream()
      .map(this::toDto)
      .collect(Collectors.toList());
  }
}
