package com.example.diary.board.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Setter
@Getter
@NoArgsConstructor @AllArgsConstructor
public class BoardImageResponseDto {
    private String imageDataBase64;

    public static String convertByteArrayToBase64(byte[] byteArray) {
        return Base64.getEncoder().encodeToString(byteArray);
    }
}
