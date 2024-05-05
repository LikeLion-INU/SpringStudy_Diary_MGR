package com.example.diary.board.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BoardRequestDto {

    private String title;
    private String date;
    //String 형식으로 받고 LocalDate 형식으로 변경
    private String content;
    private String scope;
    private MultipartFile image;

    @Getter
    @Setter
    public static class BoardUploadDto extends BoardRequestDto {
        private String city;
    }

    public static class BoardInfoDto extends BoardRequestDto{

    }

    public static class BoardUpdateDto extends BoardRequestDto{

    }

}
