package com.example.diary.board.dto;

import com.example.diary.board.domain.Board;
import com.example.diary.board.domain.BoardImage;
import com.example.diary.board.domain.Scope;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@SuperBuilder
public class BoardResponseDto {

    protected Long id;
    protected String title;
    protected LocalDate date;
    protected String city;
    protected String content;
    protected String weather;
    protected Scope scope;
    protected BoardImageResponseDto image;

    @SuperBuilder
    public static class BoardUploadDto extends BoardResponseDto {

        public static BoardResponseDto.BoardUploadDto toDto(Board b){

            return BoardUploadDto.builder()
                    .id(b.getId())
                    .title(b.getTitle())
                    .date(b.getDate())
                    .content(b.getContent())
                    .scope(b.getScope())
                    .city(b.getCity())
                    .weather(b.getWeather())
                    .image(byteToBase64(b.getBoardImage()))
                    .build();

        }

        private static BoardImageResponseDto byteToBase64(BoardImage boardImage) {
            BoardImageResponseDto boardImageResponseDto = new BoardImageResponseDto();
            String image = BoardImageResponseDto.convertByteArrayToBase64(boardImage.getData());
            boardImageResponseDto.setImageDataBase64(image);
            return boardImageResponseDto;
        }

    }

    @SuperBuilder
    public static class BoardInfoDto extends BoardResponseDto{

    }

    @SuperBuilder
    public static class BoardUpdateDto extends BoardResponseDto{

    }

}
