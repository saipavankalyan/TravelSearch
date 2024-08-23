package com.travelwits.travel_suggest.service;

import com.travelwits.travel_suggest.dto.HotelsDto;
import com.travelwits.travel_suggest.entity.HotelAmenityMapper;
import com.travelwits.travel_suggest.entity.Hotels;
import com.travelwits.travel_suggest.repo.HotelAmenitiesRepository;
import com.travelwits.travel_suggest.repo.HotelAmenityMapperRepository;
import com.travelwits.travel_suggest.repo.HotelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HotelsRefreshService {

    @Autowired
    HotelsService hotelsService;

    @Autowired
    HotelsRepository hotelsRepository;

    @Autowired
    HotelAmenitiesRepository hotelAmenitiesRepository;

    @Autowired
    HotelAmenityMapperRepository hotelAmenityMapperRepository;

    private void insertToDb(HotelsDto hotelsDto) {
        int rating = hotelsDto.getRating().multiply(new BigDecimal(10)).intValue();
        Hotels hotels = new Hotels(hotelsDto.getId(), hotelsDto.getName(), hotelsDto.getAddress(), hotelsDto.getLocation(), (int) hotelsDto.getStars(), rating, hotelsDto.getPricePerNight().multiply(new BigDecimal(100)).intValue(), new Date());
        List<HotelAmenityMapper> amenityMappers = new ArrayList<>();
        for (String amenity: hotelsDto.getAmenities()) {
            Integer amenityId = hotelAmenitiesRepository.findIdByAmenity(amenity);
            amenityMappers.add(new HotelAmenityMapper(hotelsDto.getId(), amenityId));
        }
        hotelsRepository.save(hotels);
        hotelAmenityMapperRepository.saveAll(amenityMappers);
    }

    @Transactional(timeout = 120, propagation = Propagation.REQUIRES_NEW)
    public int refreshHotels(String location, Date lastUpdatedAt) {
        List<HotelsDto> hotels = hotelsService.getHotels(location, lastUpdatedAt);
        hotels.forEach(this::insertToDb);
        return hotels.size();
    }

}
