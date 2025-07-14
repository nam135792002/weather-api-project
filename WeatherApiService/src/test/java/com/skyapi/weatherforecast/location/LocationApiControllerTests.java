package com.skyapi.weatherforecast.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyapi.weatherforecast.common.Location;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebMvcTest(controllers = LocationApiController.class)
public class LocationApiControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LocationService locationService;

    @Value("${api.location.root}")
    private String locationApiPath;

    @Test
    public void addLocation_parameterEmpty_response400BadRequestStatus() throws Exception {
        Location location = new Location();

        String bodyContent = objectMapper.writeValueAsString(location);
        this.mockMvc.perform(MockMvcRequestBuilders.post(locationApiPath)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(bodyContent))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void addLocation_parameterLocationValid_response201CreatedStatus() throws Exception {
        Location location = Location.builder()
                .code("NYC_USA")
                .cityName("New York City")
                .regionName("New York")
                .countryName("United States of America")
                .countryCode("US")
                .enabled(true)
                .build();

        Mockito.when(locationService.add(location)).thenReturn(location);

        String bodyContent = objectMapper.writeValueAsString(location);
        this.mockMvc.perform(MockMvcRequestBuilders.post(locationApiPath)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(bodyContent))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("NYC_USA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cityName").value("New York City"))
                .andExpect(MockMvcResultMatchers.header().string("Location", locationApiPath.concat("/").concat(location.getCode())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void addLocation_parameterCodeEmpty_response400BadRequestStatus() throws Exception {
        Location location = Location.builder()
                .code("")
                .cityName("New York City")
                .regionName("New York")
                .countryName("United States of America")
                .countryCode("US")
                .enabled(true)
                .build();

        String bodyContent = objectMapper.writeValueAsString(location);
        this.mockMvc.perform(MockMvcRequestBuilders.post(locationApiPath)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(bodyContent))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error[0]")
                        .value("Location code must have 3-12 characters"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void listLocations_parameterEmpty_response204NoContentStatus() throws Exception {
        Mockito.when(locationService.listLocations()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(MockMvcRequestBuilders.get(locationApiPath))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void listLocations_parameterEmpty_response200OkStatus() throws Exception {
        Location firstLocation = Location.builder()
                .code("NYC_USA")
                .cityName("New York City")
                .regionName("New York")
                .countryName("United States of America")
                .countryCode("US")
                .enabled(true)
                .build();

        Location secondLocation = Location.builder()
                .code("LACA_USA")
                .cityName("Los Angeles")
                .regionName("California")
                .countryName("United States of America")
                .countryCode("US")
                .enabled(true)
                .build();

        List<Location> locations = Arrays.asList(firstLocation, secondLocation);
        Mockito.when(locationService.listLocations()).thenReturn(locations);

        this.mockMvc.perform(MockMvcRequestBuilders.get(locationApiPath))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(locations.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].code").value("NYC_USA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].cityName").value("New York City"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].code").value("LACA_USA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].cityName").value("Los Angeles"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void get_parameterCodeInvalid_response405MethodNotAllowedStatus() throws Exception {
        String requestUri = locationApiPath.concat("/").concat("ABCDEF");

        this.mockMvc.perform(MockMvcRequestBuilders.post(requestUri))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void get_parameterCodeInvalid_response404NotFoundStatus() throws Exception {
        String requestUri = locationApiPath.concat("/").concat("ABCDEF");

        this.mockMvc.perform(MockMvcRequestBuilders.get(requestUri))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void get_parameterCodeValid_response200OkStatus() throws Exception {
        String code = "NYC_USA";
        String requestUri = locationApiPath.concat("/").concat(code);

        Location location = Location.builder()
                .code("NYC_USA")
                .cityName("New York City")
                .regionName("New York")
                .countryName("United States of America")
                .countryCode("US")
                .enabled(true)
                .build();

        Mockito.when(locationService.get(code)).thenReturn(location);

        this.mockMvc.perform(MockMvcRequestBuilders.get(requestUri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("NYC_USA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cityName").value("New York City"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateLocation_parameterCodeInvalid_response404NotFoundStatus() throws Exception {
        Location location = Location.builder()
                .code("ABCDEF")
                .cityName("New York City")
                .regionName("New York")
                .countryName("United States of America")
                .countryCode("US")
                .enabled(true)
                .build();

        Mockito.when(locationService.update(location))
                .thenThrow(new LocationNotFoundException("No location found"));

        this.mockMvc.perform(MockMvcRequestBuilders.put(locationApiPath)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(location)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateLocation_parameterCodeMissed_response400BadRequestStatus() throws Exception {
        Location location = Location.builder()
                .cityName("New York City")
                .regionName("New York")
                .countryName("United States of America")
                .countryCode("US")
                .enabled(true)
                .build();

        Mockito.when(locationService.update(location)).thenReturn(location);

        this.mockMvc.perform(MockMvcRequestBuilders.put(locationApiPath)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(location)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateLocation_parameterLocationValid_response200OkStatus() throws Exception {
        Location location = Location.builder()
                .code("NYC_USA")
                .cityName("New York City")
                .regionName("New York")
                .countryName("United States of America")
                .countryCode("US")
                .enabled(true)
                .build();

        Mockito.when(locationService.update(location)).thenReturn(location);

        String bodyContent = objectMapper.writeValueAsString(location);
        this.mockMvc.perform(MockMvcRequestBuilders.put(locationApiPath)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(bodyContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("NYC_USA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cityName").value("New York City"))
                .andDo(MockMvcResultHandlers.print());
    }

    /*TODO: Write Unit test for API delete location
    @Test
    public void deleteLocation_parameterCodeInvalid_response404NotFoundStatus() throws Exception {
        String code = "ABCDEF";

        Mockito.when(locationService.delete(code)).thenThrow(new LocationNotFoundException("No location found"));

        this.mockMvc.perform(MockMvcRequestBuilders.put(locationApiPath)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(location)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
     */
}