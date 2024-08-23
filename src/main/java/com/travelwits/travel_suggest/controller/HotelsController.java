package com.travelwits.travel_suggest.controller;

import com.travelwits.travel_suggest.dto.HotelsDto;
import com.travelwits.travel_suggest.service.HotelsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/hotels")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Getter
public class HotelsController {

    private HotelsService hotelsService;

    @GetMapping
    public List<HotelsDto> getAllHotels(@RequestParam String city) {
        return hotelsService.getHotels(city);
    }

    @GetMapping("/{hotelId}")
    private HotelsDto getHotel(@PathVariable Integer hotelId) {
        return hotelsService.getHotel(hotelId);
    }

}
