package com.example.diary.domain.board.dto;

import com.example.diary.domain.board.domain.Scope;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BoardRequestDto {

    private String title;
    private String date;
    //String 형식으로 받고 LocalDate 형식으로 변경
    private String content;
    private String scope;
    private MultipartFile image;
    private String city;

    @Getter
    @Setter
    public static class BoardUploadDto extends BoardRequestDto {

    }

    public static class BoardInfoDto extends BoardRequestDto{

    }
    @Getter
    public static class BoardUpdateDto {
        private String title;
        private String content;
    }

    @Getter
    public static class BoardScopeUpdateDto{
        private Scope scope;
    }

}
