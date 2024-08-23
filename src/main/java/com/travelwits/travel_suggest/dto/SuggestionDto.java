package com.travelwits.travel_suggest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class SuggestionDto {

    private Integer fromFlightId;
    private Integer toFlightId;
    private String source;
    private String destination;
    private Integer fromFlightPrice;
    private Integer toFlightPrice;
    private Integer hotelId;
    private Integer hotelPricePerNight;
    private Integer totalCost;

}
