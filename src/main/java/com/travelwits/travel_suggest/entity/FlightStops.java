package com.travelwits.travel_suggest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FLIGHT_STOPS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(FlightStopsKeyVO.class)
public class FlightStops {

    @Id
    @Column(name = "FLIGHT_ID")
    private Integer flightId;
    private String stop;

    @Id
    @Column(name = "FLIGHT_ORDER")
    private Integer flightOrder;
}
