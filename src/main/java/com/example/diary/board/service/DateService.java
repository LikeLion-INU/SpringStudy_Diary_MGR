package com.example.diary.board.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateService {
    public static LocalDate strToDate(String date){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dtf);
    }
}
