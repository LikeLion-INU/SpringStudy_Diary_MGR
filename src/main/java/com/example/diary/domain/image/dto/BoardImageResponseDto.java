package com.example.diary.domain.image.dto;

import lombok.*;

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
