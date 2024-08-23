package com.travelwits.travel_suggest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HOTELS_AMENITY_MAPPER")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(HotelAmenityKeyVO.class)
public class HotelAmenityMapper {

    @Id
    @Column(name = "HOTEL_ID")
    private Integer hotelId;

    @Id
    @Column(name = "AMENITY_ID")
    private Integer amenityId;

}
