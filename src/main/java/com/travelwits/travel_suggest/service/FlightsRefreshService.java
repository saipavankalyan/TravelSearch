package com.travelwits.travel_suggest.service;

import com.travelwits.travel_suggest.dto.FlightDto;
import com.travelwits.travel_suggest.entity.FlightStops;
import com.travelwits.travel_suggest.entity.Flights;
import com.travelwits.travel_suggest.repo.FlightStopsRepository;
import com.travelwits.travel_suggest.repo.FlightsRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Getter
@Setter
@NoArgsConstructor
public class FlightsRefreshService {

    @Autowired
    private FlightsService flightsService;

    @Autowired
    private FlightsRepository flightsRepository;

    @Autowired
    private FlightStopsRepository flightStopsRepository;

    private void insertIntoDb(FlightDto dto) {
        Integer price = dto.getPrice().multiply(new BigDecimal(100)).intValue();
        Flights flights = new Flights(dto.getId(), dto.getFrom(), dto.getTo(), price, dto.getDepartureTime(), dto.getArrivalTime(), new Date());
        int order = 1;
        List<FlightStops> stops = new ArrayList<>();
        for (String stop: dto.getStops()) {
            stops.add(new FlightStops(dto.getId(), stop, order));
            order += 1;
        }
        flightsRepository.save(flights);
        flightStopsRepository.saveAll(stops);
    }

    @Transactional(timeout = 120, propagation = Propagation.REQUIRES_NEW)
    public int refreshFlights(String src, String desc, Date lastUpdatedAt) {
        List<FlightDto> flights = flightsService.getFlights(src, desc, lastUpdatedAt);
        flights.forEach(this::insertIntoDb);
        return flights.size();
    }

}
