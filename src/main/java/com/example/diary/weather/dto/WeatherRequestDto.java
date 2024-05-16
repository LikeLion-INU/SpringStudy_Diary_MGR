package com.example.diary.weather.dto;

import lombok.Data;

@Data
public class WeatherRequestDto {
    private String key;
    private String date;
    private String city;
    private String info;
}
