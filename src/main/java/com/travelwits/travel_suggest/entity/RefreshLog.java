package com.travelwits.travel_suggest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "REFRESH_LOG")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REFRESH_LOG_ID")
    private Integer refreshLogId;

    private String entity;

    @Column(name = "REFRESH_COUNT")
    private Integer refreshCount;

    @Column(name = "REFRESHED_AT")
    private Date refreshedAt;

    public RefreshLog(String entity, Integer refreshCount) {
        this.entity = entity;
        this.refreshCount = refreshCount;
        this.refreshedAt = new Date();
    }

}
