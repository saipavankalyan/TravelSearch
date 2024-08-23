package com.travelwits.travel_suggest.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Getter
@Setter
@Service
@Slf4j
public class DbRefreshService {

    @Autowired
    FlightsRefreshService flightsRefreshService;

    @Autowired
    HotelsRefreshService hotelsRefreshService;

    @Async(value = "dbRefreshThreadPool")
    public CompletableFuture<Integer> refreshFlights(String from, String to, Date lastUpdatedDate) {
        int updatedCount = flightsRefreshService.refreshFlights(from, to, lastUpdatedDate);
        log.info("Refreshed {} flights from {} to {}", updatedCount, from, to);
        return CompletableFuture.completedFuture(updatedCount);
    }

    @Async(value = "dbRefreshThreadPool")
    public CompletableFuture<Integer> refreshHotels(String city, Date lastUpdatedDate) {
        int updatedCount = hotelsRefreshService.refreshHotels(city, lastUpdatedDate);
        log.info("Refreshed {} hotels from {} to {}", updatedCount, city, lastUpdatedDate);
        return CompletableFuture.completedFuture(updatedCount);
    }

}
