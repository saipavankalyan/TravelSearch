package com.travelwits.travel_suggest.repo;

import com.travelwits.travel_suggest.entity.Hotels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelsRepository extends JpaRepository<Hotels, Integer> {
}
