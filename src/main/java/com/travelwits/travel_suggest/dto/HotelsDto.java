package com.travelwits.travel_suggest.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class HotelsDto extends BaseDto {

    private Integer id;
    private String name;
    private String address;
    private String location;
    private short stars;
    private BigDecimal rating;
    private List<String> amenities;
    private BigDecimal pricePerNight;

}
