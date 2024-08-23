package com.travelwits.travel_suggest.controller;

import com.travelwits.travel_suggest.dto.FlightDto;
import com.travelwits.travel_suggest.service.FlightsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Getter
public class FlightsController {

    private FlightsService flightsService;

    @GetMapping
    public List<FlightDto> flights(@RequestParam String src, @RequestParam String dest) {
        return flightsService.getFlights(src, dest);
    }

    @GetMapping("/{flightId}")
    public FlightDto flight(@PathVariable Integer flightId) {
        return flightsService.getFlight(flightId);
    }

}
