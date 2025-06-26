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

    public Location get(String code){
        return locationRepository.findLocationByCode(code).orElse(null);
    }
}
