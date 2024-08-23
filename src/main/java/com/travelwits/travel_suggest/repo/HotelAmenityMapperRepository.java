package com.travelwits.travel_suggest.repo;

import com.travelwits.travel_suggest.entity.HotelAmenityKeyVO;
import com.travelwits.travel_suggest.entity.HotelAmenityMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelAmenityMapperRepository extends JpaRepository<HotelAmenityMapper, HotelAmenityKeyVO> {

    @Query("select h.amenityId from HotelAmenityMapper h where h.hotelId = :hotelId")
    List<Integer> findIdByHotelId(Integer hotelId);

}
