package com.example.diary.domain.image.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BoardImageRequestDto {
    private Long id;
    private MultipartFile image;
}
