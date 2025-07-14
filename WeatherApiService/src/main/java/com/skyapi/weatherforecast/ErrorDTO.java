package com.skyapi.weatherforecast;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ErrorDTO {
    private LocalDateTime timestamp;
    private int status;
    private String path;
    private List<String> error = new ArrayList<>();

    public void addError(String error) {
        this.error.add(error);
    }
}
