package com.travelwits.travel_suggest.service;

import com.travelwits.travel_suggest.dto.SuggestionDto;

import java.util.List;

public interface IFlightsSuggestor {

    List<SuggestionDto> findSuggestions(String src, int budget, int days, int pageNo, int pageSize);

}
