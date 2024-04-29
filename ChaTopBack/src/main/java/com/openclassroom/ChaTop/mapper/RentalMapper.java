package com.openclassroom.ChaTop.mapper;

import com.openclassroom.ChaTop.dto.RentalDto;
import com.openclassroom.ChaTop.models.Rental;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class RentalMapper implements EntityMapper<RentalDto, Rental> {

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
    rental.createdAt( dto.getCreatedAt() );
    rental.updatedAt( dto.getUpdatedAt() );

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
    rentalDto.setCreatedAt( entity.getCreatedAt() );
    rentalDto.setUpdatedAt( entity.getUpdatedAt() );

    return rentalDto;
  }

  @Override
  public List<Rental> toEntity(List<RentalDto> dtoList) {
    if ( dtoList == null ) {
      return null;
    }

    List<Rental> list = new ArrayList<Rental>( dtoList.size() );
    for ( RentalDto rentalDto : dtoList ) {
      list.add( toEntity( rentalDto ) );
    }

    return list;
  }

  @Override
  public List<RentalDto> toDto(List<Rental> entityList) {
    if ( entityList == null ) {
      return null;
    }

    List<RentalDto> list = new ArrayList<RentalDto>( entityList.size() );
    for ( Rental rental : entityList ) {
      list.add( toDto( rental ) );
    }

    return list;
  }
}
