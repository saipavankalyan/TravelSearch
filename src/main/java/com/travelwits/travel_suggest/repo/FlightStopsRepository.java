package com.travelwits.travel_suggest.repo;

import com.travelwits.travel_suggest.entity.FlightStops;
import com.travelwits.travel_suggest.entity.FlightStopsKeyVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightStopsRepository extends JpaRepository<FlightStops, FlightStopsKeyVO> {


    @Query("select f.stop from FlightStops f where f.flightId = :flightId order by f.flightOrder")
    List<String> findByFlightId(Integer flightId);

}
