package com.skyapi.weatherforecast;

import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.realtime.IPApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeolocationService {

    private final RestTemplate restTemplate;

    @Value("${ip2location.api.key}")
    private String apiKeyLocation;

    public Location getLocation(String ipAddress) {
        String url = String.format("https://api.ip2location.io/?key=%s&ip=%s",
                apiKeyLocation, ipAddress);

        IPApiResponse response = restTemplate.getForEntity(url, IPApiResponse.class).getBody();
        log.debug("Response from IP2Location API: {}", response);

        if (Objects.isNull(response) || Objects.isNull(response.getCountryName()))
            throw new GeolocationException("Invalid response from IP API");

        return new Location(response.getCityName(), response.getRegionName(), response.getCountryName(),
                response.getCountryCode());
    }
}
