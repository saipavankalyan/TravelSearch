package com.travelwits.travel_suggest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "HOTELS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotels {

    @Id
    @Column(name = "HOTEL_ID")
    private Integer hotelId;
    @Column(name = "HOTEL_NAME")
    private String hotelName;
    private String address;
    private String city;
    private Integer stars;
    private Integer rating;
    private Integer price;
    @Column(name = "LAST_UPDATED")
    private Date lastUpdated;

}
