package com.travelwits.travel_suggest.repo;

import com.travelwits.travel_suggest.entity.RefreshLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RefreshLogRepository extends JpaRepository<RefreshLog, Integer> {

    @Query(value = "SELECT MAX(REFRESHED_AT) FROM REFRESH_LOG where ENTITY like :entityName", nativeQuery = true)
    Date getLastRefreshedDate(@Param("entityName") String entityName);

}
