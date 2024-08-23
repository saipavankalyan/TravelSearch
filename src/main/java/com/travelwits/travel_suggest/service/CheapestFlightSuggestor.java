package com.travelwits.travel_suggest.service;

import com.travelwits.travel_suggest.dto.SuggestionDto;
import com.travelwits.travel_suggest.repo.FlightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheapestFlightSuggestor implements IFlightsSuggestor {

    @Autowired
    FlightsRepository flightsRepository;

    @Override
    public List<SuggestionDto> findSuggestions(String src, int budget, int days, int pageNo, int pageSize) {
        return flightsRepository.findSuggestions(src, budget, days, (pageNo - 1) * pageSize, pageSize);
    }
}
