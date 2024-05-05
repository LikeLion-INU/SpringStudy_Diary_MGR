package com.example.diary.board.dto;

import lombok.Data;

@Data
public class WeatherRequestDto {
    private String key;
    private String date;
    private String city;
    private String info;
}
