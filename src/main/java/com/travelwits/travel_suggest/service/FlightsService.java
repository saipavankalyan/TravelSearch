package com.travelwits.travel_suggest.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelwits.travel_suggest.dto.FlightDto;
import com.travelwits.travel_suggest.entity.FlightStops;
import com.travelwits.travel_suggest.entity.Flights;
import com.travelwits.travel_suggest.repo.FlightStopsRepository;
import com.travelwits.travel_suggest.repo.FlightsRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightsService {

    private static List<FlightDto> flights = new ArrayList<>();

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private FlightsRepository flightsRepository;

    @Autowired
    private FlightStopsRepository flightStopsRepository;

    @PostConstruct
    public void setup() throws IOException {
        String flightsString = Files.readString(resourceLoader.getResource("classpath:flights.json").getFile().toPath());
        List<FlightDto> flights = new ObjectMapper().readValue(flightsString, new TypeReference<List<FlightDto>>() {});
        flights.forEach(flightDto -> flightDto.setUpdatedAt(new Date()));
        this.flights = flights;
    }

    public List<FlightDto> getFlights(String src, String dest) {
        return flights.stream().filter(flight -> flight.getFrom().equals(src) && flight.getTo().equals(dest)).toList();
    }

    public List<FlightDto> getFlights(String src, String dest, Date lastUpdatedTime) {
        if (lastUpdatedTime == null) {
            return getFlights(src, dest);
        }
        return getFlights(src, dest).stream().filter(flightDto -> flightDto.getUpdatedAt().after(lastUpdatedTime)).toList();
    }

    public FlightDto getFlight(Integer flightId) {
        Flights flights = flightsRepository.getById(flightId);
        List<String> stops = flightStopsRepository.findByFlightId(flightId);
        FlightDto flightDto = new FlightDto(flightId, flights.getSrc(), flights.getDest(), stops, new BigDecimal(flights.getPrice() / 100), flights.getDepartureTime(), flights.getArrivalTime());
        return flightDto;
    }

}
