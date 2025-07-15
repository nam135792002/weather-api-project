package com.skyapi.weatherforecast.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "realtime_weather")
@Data
public class RealtimeWeather {

    @Id
    @Column(name = "location_code")
    private String locationCode;

    private int temperature;

    private int humidity;

    private int precipitation;

    private int windSpeed;

    @Column(length = 50)
    private String status;

    private LocalDateTime lastUpdated;

    @OneToOne
    @JoinColumn(name = "location_code")
    @MapsId
    private Location location;

}
