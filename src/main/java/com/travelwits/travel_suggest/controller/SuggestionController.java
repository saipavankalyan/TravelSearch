package com.travelwits.travel_suggest.controller;

import com.travelwits.travel_suggest.dto.SuggestionDto;
import com.travelwits.travel_suggest.repo.FlightsRepository;
import com.travelwits.travel_suggest.service.CheapestFlightSuggestor;
import com.travelwits.travel_suggest.service.IFlightsSuggestor;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/suggest")
@CrossOrigin(origins = "*")
public class SuggestionController {

    @Autowired
    CheapestFlightSuggestor cheapestFlightSuggestor;

    private static final Map<String, IFlightsSuggestor> FLIGHT_SUGGESTORS = new HashMap<String, IFlightsSuggestor>();

    @PostConstruct
    public void setup() {
        FLIGHT_SUGGESTORS.put("CHEAPEST", cheapestFlightSuggestor);
    }

    @GetMapping
    public List<SuggestionDto> getSuggestions(@RequestParam("from") String from, @RequestParam("budget") BigDecimal budget,
                                              @RequestParam("days") Integer days, @RequestParam("pageNumber") int pageNo, @RequestParam("pageSize") int pageSize) {
        return FLIGHT_SUGGESTORS.get("CHEAPEST").findSuggestions(from, budget.multiply(new BigDecimal(100)).intValue(), days, pageNo, pageSize);
    }

}
