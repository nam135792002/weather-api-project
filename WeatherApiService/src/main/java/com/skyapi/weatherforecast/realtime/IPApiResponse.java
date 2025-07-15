package com.skyapi.weatherforecast.realtime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class IPApiResponse {
    @JsonProperty("city_name")
    private String cityName;

    @JsonProperty("region_name")
    private String regionName;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("country_code")
    private String countryCode;
}
