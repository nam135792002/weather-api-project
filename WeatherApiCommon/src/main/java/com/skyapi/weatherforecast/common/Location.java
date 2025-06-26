package com.skyapi.weatherforecast.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "locations")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Column(length = 12, nullable = false, unique = true)
    @Id
    @NotBlank
    private String code;

    @Column(length = 128, nullable = false)
    @NotBlank
    private String cityName;

    @Column(length = 128)
    @NotNull
    private String regionName;

    @Column(length = 64, nullable = false)
    @NotBlank
    private String countryName;

    @Column(length = 2, nullable = false)
    @NotBlank
    private String countryCode;

    @JsonIgnore
    private boolean enabled;

    private boolean trashed;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(code, location.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }
}
