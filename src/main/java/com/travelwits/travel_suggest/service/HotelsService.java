package com.travelwits.travel_suggest.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelwits.travel_suggest.dto.HotelsDto;
import com.travelwits.travel_suggest.entity.Hotels;
import com.travelwits.travel_suggest.repo.HotelAmenitiesRepository;
import com.travelwits.travel_suggest.repo.HotelAmenityMapperRepository;
import com.travelwits.travel_suggest.repo.HotelsRepository;
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
public class HotelsService {

    private List<HotelsDto> hotels = new ArrayList<>();

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private HotelsRepository hotelsRepository;

    @Autowired
    private HotelAmenitiesRepository hotelAmenitiesRepository;

    @Autowired
    private HotelAmenityMapperRepository hotelAmenityMapperRepository;

    @PostConstruct
    public void setHotels() throws IOException {
        String hotelsString = Files.readString(resourceLoader.getResource("classpath:hotels.json").getFile().toPath());
        List<HotelsDto> hotels = new ObjectMapper().readValue(hotelsString, new TypeReference<List<HotelsDto>>() {});
        hotels.forEach(hotelsDto -> hotelsDto.setUpdatedAt(new Date()));
        this.hotels = hotels;
    }

    public List<HotelsDto> getHotels(String location) {
        return hotels.stream().filter(hotel -> hotel.getLocation().equals(location)).toList();
    }

    public List<HotelsDto> getHotels(String location, Date lastUpdatedAt) {
        if (lastUpdatedAt == null) {
            return getHotels(location);
        }
        return getHotels(location).stream().filter(hotelsDto -> hotelsDto.getUpdatedAt().after(lastUpdatedAt)).toList();
    }

    public HotelsDto getHotel(Integer hotelId) {
        Hotels hotels = hotelsRepository.getById(hotelId);
        List<Integer> ids = hotelAmenityMapperRepository.findIdByHotelId(hotelId);
        List<String> amenities = hotelAmenitiesRepository.findAllIdsByAmenity(ids);
        HotelsDto hotelsDto = new HotelsDto(hotelId, hotels.getHotelName(), hotels.getAddress(), hotels.getCity(), hotels.getStars().shortValue(), new BigDecimal(hotels.getRating() / 10), amenities, new BigDecimal(hotels.getPrice() / 100));
        return hotelsDto;
    }
}
