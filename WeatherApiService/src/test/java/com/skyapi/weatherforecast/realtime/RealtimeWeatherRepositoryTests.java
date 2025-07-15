package com.skyapi.weatherforecast.realtime;

import com.skyapi.weatherforecast.common.RealtimeWeather;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RealtimeWeatherRepositoryTests {

    @Autowired
    private RealtimeWeatherRepository realtimeWeatherRepository;

    @Test
    public void updateRealtimeWeather_whenUpdate_thenSuccess() {
        String locationCode = "NYC_USA";
        RealtimeWeather realtimeWeather = realtimeWeatherRepository.findById(locationCode).get();

        realtimeWeather.setTemperature(-2);
        realtimeWeather.setHumidity(32);
        realtimeWeather.setPrecipitation(42);
        realtimeWeather.setStatus("Snowy");
        realtimeWeather.setWindSpeed(12);
        realtimeWeather.setLastUpdated(LocalDateTime.now());

        RealtimeWeather updatedRealtimeWeather = realtimeWeatherRepository.save(realtimeWeather);
        Assertions.assertThat(updatedRealtimeWeather.getLocationCode()).isEqualTo(locationCode);
        Assertions.assertThat(updatedRealtimeWeather.getTemperature()).isEqualTo(-2);
    }

    @Test
    public void givenRealtimeWeather_whenGet_thenNotFound() {
        String countryCode = "JP";
        String cityName = "Tokyo";

        RealtimeWeather weather = realtimeWeatherRepository.findByCountryCodeAndCityName(countryCode, cityName)
                .orElse(null);

        Assertions.assertThat(weather).isNull();
    }

    @Test
    public void givenRealtimeWeather_whenGet_thenFound() {
        String countryCode = "US";
        String cityName = "New York City";

        RealtimeWeather weather = realtimeWeatherRepository.findByCountryCodeAndCityName(countryCode, cityName)
                .orElse(null);

        Assertions.assertThat(weather).isNotNull();
        Assertions.assertThat(weather.getLocation().getCityName()).isEqualTo(cityName);
    }
}
