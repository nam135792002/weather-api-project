package com.skyapi.weatherforecast.location;

import com.skyapi.weatherforecast.common.Location;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class LocationRepositoryTests {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void givenNewLocation_whenSave_thenSuccess() {
        Location location = Location.builder()
                .code("DELHI_IN")
                .cityName("New Delhi")
                .regionName("Delhi")
                .countryName("India")
                .countryCode("IN")
                .enabled(true)
                .build();

        Location savedLocation = locationRepository.save(location);
        Assertions.assertThat(savedLocation).isNotNull();
        Assertions.assertThat(savedLocation.getCode()).isEqualTo("DELHI_IN");
        Assertions.assertThat(savedLocation.getCityName()).isEqualTo("New Delhi");
        Assertions.assertThat(savedLocation.getCountryName()).isEqualTo("India");
        Assertions.assertThat(savedLocation.getCountryCode()).isEqualTo("IN");
        Assertions.assertThat(savedLocation.getRegionName()).isEqualTo("Delhi");
        Assertions.assertThat(savedLocation.isEnabled()).isEqualTo(true);
    }

    @Test
    public void givenLocations_whenList_thenSuccess() {
        List<Location> locations = locationRepository.findUntrashed();
        Assertions.assertThat(locations).isNotEmpty();
        locations.forEach(System.out::println);
    }

    @Test
    public void giveLocation_whenGet_thenNotFound() {
        String code = "ABCD";

        Location location = locationRepository.findLocationByCode(code).orElse(null);
        Assertions.assertThat(location).isNull();
    }

    @Test
    public void giveLocation_whenGet_thenFound() {
        String code = "DELHI_IN";

        Location location = locationRepository.findLocationByCode(code).orElse(null);
        Assertions.assertThat(location).isNotNull();
        Assertions.assertThat(location.getCode()).isEqualTo(code);
    }
}
