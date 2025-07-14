package com.skyapi.weatherforecast.location;

import com.skyapi.weatherforecast.common.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public Location add(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> listLocations() {
        return locationRepository.findUntrashed();
    }

    public Location get(String code) {
        return locationRepository.findLocationByCode(code)
                .orElseThrow(() -> new LocationNotFoundException(String.format(
                        "No location found with the given code: %s", code)));
    }

    public Location update(Location locationRequest) {
        String code = locationRequest.getCode();
        Location locationInDB = locationRepository.findLocationByCode(code)
                .orElseThrow(() -> new LocationNotFoundException(String.format(
                        "No location found with the given code: %s", code)));

        locationInDB.setCityName(locationRequest.getCityName());
        locationInDB.setCountryName(locationRequest.getCountryName());
        locationInDB.setCountryCode(locationRequest.getCountryCode());
        locationInDB.setRegionName(locationRequest.getRegionName());
        locationInDB.setEnabled(locationRequest.isEnabled());

        return locationRepository.save(locationInDB);
    }

    public void delete(String code) {
        Location location = this.get(code);

        locationRepository.deleteById(code);
    }
}
