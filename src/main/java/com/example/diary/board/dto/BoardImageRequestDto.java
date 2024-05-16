package com.example.diary.board.dto;

import lombok.Data;

@Data
public class BoardImageRequestDto {
    private Long id;
    private byte[] data;
}
