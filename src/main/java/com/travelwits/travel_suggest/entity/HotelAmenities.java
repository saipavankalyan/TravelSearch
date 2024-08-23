package com.travelwits.travel_suggest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HOTEL_AMENITIES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelAmenities {

    @Id
    private Integer id;
    private String amenity;

}
