package com.skyapi.weatherforecast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyapi.weatherforecast.common.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeolocationService {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Value("${ip2location.api.key}")
    private String apiKeyLocation;

    public Location getLocation(String ipAddress) {
        String url = String.format("https://api.ip2location.io/?key=%s&ip=%s",
                apiKeyLocation, ipAddress);

        log.info("Calling IP2Location API for IP: {}", ipAddress);
        String jsonResponse = restTemplate.getForObject(url, String.class);
        log.info("Response from IP2Location API: {}", jsonResponse);

//            return new Location(ipResult.getCity(), ipResult.getRegion(), ipResult.getCountryLong(),
//                    ipResult.getCountryShort());
        return new Location();
    }
}
