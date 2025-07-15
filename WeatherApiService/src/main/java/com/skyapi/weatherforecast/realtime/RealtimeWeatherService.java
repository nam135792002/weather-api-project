package com.skyapi.weatherforecast.realtime;

import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.common.RealtimeWeather;
import com.skyapi.weatherforecast.location.LocationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RealtimeWeatherService {
    private final RealtimeWeatherRepository realtimeWeatherRepository;

    public RealtimeWeather getByLocation(Location location) {
        String countryCode = location.getCountryCode();
        String cityName = location.getCityName();

        return realtimeWeatherRepository.findByCountryCodeAndCityName(countryCode, cityName)
                .orElseThrow(() -> new LocationNotFoundException("No location found with the " +
                        "given country code and city name"));
    }
}
