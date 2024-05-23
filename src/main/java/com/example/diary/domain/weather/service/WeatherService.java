package com.example.diary.domain.weather.service;

import com.example.diary.domain.weather.dto.WeatherRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

import static java.lang.System.getenv;

@Slf4j
@Component
public class WeatherService {


    private static final String PATH = "..\\diary\\src\\main\\resources\\static\\";

    //info, key, city, date를 넘겨주기
    static Map<String, String> env = getenv();

    private static WeatherRequestDto paramOfWthr(String city, String date, JdbcTemplate jdbcTemplate) {
        String key , info = null;
        LocalDate now = LocalDate.now();
        if (!Objects.equals(date, now.toString())){
            info = "0";
            key = env.get("PastWeather");
            String[] ymd = date.split("-");
            date = ymd[0] + ymd[1] + ymd[2];
            city = String.valueOf(jdbcTemplate.queryForObject("SELECT id FROM city_code WHERE name = ?", Integer.class, city));
            log.info("과거 날씨 조회");
        } else{
            info = "1";
            key = env.get("TodayWeather");
            log.info("현재 날씨 조회");
        }
        WeatherRequestDto result = new WeatherRequestDto();
        result.setInfo(info);
        result.setCity(city);
        result.setDate(date);
        result.setKey(key);
        return result;
    }
    public static String getWeather(String city, String date, JdbcTemplate jdbcTemplate) {
        WeatherRequestDto requestDto = paramOfWthr(city, date, jdbcTemplate);

        //info = 0 이면 과거의 날씨 요청
        //info = 1 이면 오늘의 날씨 요청
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", PATH + "weather.py",
                    requestDto.getInfo(), requestDto.getCity(), requestDto.getKey(), requestDto.getDate());

            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String result = null;
            String line;
            while ((line = reader.readLine()) !=null){
                log.info(result);
                result = line;
            }

            // 2. 프로세스 종료 대기
            int exitCode = process.waitFor();
            process.destroy();
            log.info("종료 코드: " + exitCode);

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
