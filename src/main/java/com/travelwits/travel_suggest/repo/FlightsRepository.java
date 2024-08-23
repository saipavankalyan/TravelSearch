package com.travelwits.travel_suggest.repo;

import com.travelwits.travel_suggest.dto.SuggestionDto;
import com.travelwits.travel_suggest.entity.Flights;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightsRepository extends JpaRepository<Flights, Integer> {

    @Query(name = "suggestionQuery", nativeQuery = true)
    List<SuggestionDto> findSuggestions(@Param("src") String src,
                                        @Param("budget") int budget,
                                        @Param("days") int days,
                                        @Param("ofst") int ofst,
                                        @Param("lmt") int lmt);

}
