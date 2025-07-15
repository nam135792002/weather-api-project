package com.skyapi.weatherforecast;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;
import com.skyapi.weatherforecast.common.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class GeolocationService {
    private final String DBPath = "C:\\Users\\TGC\\IdeaProjects\\WeatherApiProject\\WeatherApiService\\ip2locdb\\IP2LOCATION-LITE-DB3.BIN";
    private IP2Location ip2Location = new IP2Location();

    public GeolocationService() {
        try {
            ip2Location.Open(DBPath);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public Location getLocation(String ipAddress) {
        try {
            IPResult ipResult = ip2Location.IPQuery(ipAddress);
            if(!ipResult.getStatus().equals("OK")) {
                throw new GeolocationException("Geolocation failed with status: " + ipResult.getStatus());
            }
            return new Location(ipResult.getCity(), ipResult.getRegion(), ipResult.getCountryLong(),
                    ipResult.getCountryShort());
        } catch (IOException e) {
            throw new GeolocationException("Error querying IP database", e);
        }
    }
}
