package com.skyapi.weatherforecast.location;

import com.skyapi.weatherforecast.common.Location;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.location.root}")
public class LocationApiController {

    private final LocationService locationService;

    @Value("${api.location.root}")
    private String locationApiPath;

    @PostMapping
    public ResponseEntity<Location> addLocation(@RequestBody @Valid Location location) {
        Location addedLocation = locationService.add(location);
        URI uri = URI.create(locationApiPath.concat("/").concat(location.getCode()));
        return ResponseEntity.created(uri).body(addedLocation);
    }

    @GetMapping
    public ResponseEntity<List<Location>> listLocations() {
        List<Location> locations = locationService.listLocations();
        if(locations.isEmpty()) return ResponseEntity.noContent().build();
        return  ResponseEntity.ok(locations);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Location> getLocations(@PathVariable(value = "code") String code) {
        Location location = locationService.get(code);
        if(location == null) return ResponseEntity.notFound().build();
        return  ResponseEntity.ok(location);
    }

    @PutMapping
    public ResponseEntity<Location> updateLocation(@RequestBody @Valid Location location) {
        try {
            Location updatedLocation = locationService.update(location);
            return ResponseEntity.ok(updatedLocation);
        } catch (LocationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return  ResponseEntity.ok("Hi Ri Nguyen");
    }
}
