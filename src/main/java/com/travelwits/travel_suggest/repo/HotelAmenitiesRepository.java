package com.travelwits.travel_suggest.repo;

import com.travelwits.travel_suggest.entity.HotelAmenities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelAmenitiesRepository extends JpaRepository<HotelAmenities, Integer> {

    @Query("select h.id from HotelAmenities h where h.amenity = :amenity")
    Integer findIdByAmenity(String amenity);

    @Query("select h.amenity from HotelAmenities h where h.id in :amenitiesId")
    List<String> findAllIdsByAmenity(List<Integer> amenitiesId);

}
