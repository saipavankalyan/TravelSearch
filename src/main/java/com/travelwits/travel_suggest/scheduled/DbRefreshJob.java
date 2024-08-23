package com.travelwits.travel_suggest.scheduled;

import com.travelwits.travel_suggest.entity.RefreshLog;
import com.travelwits.travel_suggest.repo.RefreshLogRepository;
import com.travelwits.travel_suggest.service.DbRefreshService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class DbRefreshJob {

    private static final List<String> CITIES = List.of("JFK", "LAX", "LHR", "NRT", "SYD");

    @Autowired
    RefreshLogRepository refreshLogRepository;

    @Autowired
    DbRefreshService dbRefreshService;

    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 0)
    public void refreshDb() {
        Date lastRefresh = refreshLogRepository.getLastRefreshedDate("FLIGHT");
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        for (String src: CITIES) {
            for (String dest: CITIES) {
                if (!src.equals(dest)) {
                    futures.add(dbRefreshService.refreshFlights(src, dest, lastRefresh));
                }
            }
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
        int updatedCount = getCounts(futures);
        RefreshLog refreshLog = new RefreshLog("FLIGHT", updatedCount);
        refreshLogRepository.save(refreshLog);
        log.info("Refreshed " + updatedCount + " flights");
    }

    private static Integer getCounts(List<CompletableFuture<Integer>> futures) {
        return futures.stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException e) {
                return 0;
            } catch (ExecutionException e) {
                return 0;
            }
        }).reduce(0, Integer::sum);
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 0)
    public void refreshHotel() {
        Date lastRefresh = refreshLogRepository.getLastRefreshedDate("HOTEL");
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        for (String src: CITIES) {
            futures.add(dbRefreshService.refreshHotels(src, lastRefresh));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
        int updatedCount = getCounts(futures);
        RefreshLog refreshLog = new RefreshLog("HOTEL", updatedCount);
        refreshLogRepository.save(refreshLog);
        log.info("Refreshed " + updatedCount + " hotels");
    }

}
