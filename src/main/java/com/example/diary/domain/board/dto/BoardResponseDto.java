package com.example.diary.domain.board.dto;

import com.example.diary.domain.board.domain.Board;
import com.example.diary.domain.image.domain.BoardImage;
import com.example.diary.domain.board.domain.Scope;
import com.example.diary.domain.image.dto.BoardImageResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
    protected int likeCnt;

    @SuperBuilder
    public static class BoardUploadDto extends BoardResponseDto {

        public static BoardResponseDto.BoardUploadDto toDto(Board b){
            BoardImageResponseDto boardImageResponseDto = new BoardImageResponseDto();
            if (b.getBoardImage() != null){
                boardImageResponseDto = BoardResponseDto.byteToBase64(b.getBoardImage());
            }
            return BoardUploadDto.builder()
                    .id(b.getId())
                    .title(b.getTitle())
                    .date(b.getDate())
                    .content(b.getContent())
                    .scope(b.getScope())
                    .city(b.getCity())
                    .weather(b.getWeather())
                    .image(boardImageResponseDto)
                    .build();

        }

    }

    @SuperBuilder
    public static class BoardInfoDto extends BoardResponseDto{


        public static BoardInfoDto toDto(Board board) {
            BoardImageResponseDto boardImageResponseDto = new BoardImageResponseDto();
            if (board.getBoardImage() != null){
                boardImageResponseDto = BoardResponseDto.byteToBase64(board.getBoardImage());
            }
            return BoardInfoDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .date(board.getDate())
                    .city(board.getCity())
                    .scope(board.getScope())
                    .weather(board.getWeather())
                    .likeCnt(board.getLikeCnt())
                    .image(boardImageResponseDto)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class BoardListDto{
        private Long id;
        private String title;
        private LocalDate date;
        private int likeCnt;

        public static BoardListDto toDto(Board board) {
            return BoardListDto.builder()
                    .id(board.getId())
                    .date(board.getDate())
                    .title(board.getTitle())
                    .likeCnt(board.getLikeCnt())
                    .build();
        }
    }

    @SuperBuilder
    public static class BoardUpdateDto extends BoardResponseDto{

    }

    private static BoardImageResponseDto byteToBase64(BoardImage boardImage) {
        BoardImageResponseDto boardImageResponseDto = new BoardImageResponseDto();
        String image = BoardImageResponseDto.convertByteArrayToBase64(boardImage.getData());
        boardImageResponseDto.setImageDataBase64(image);
        return boardImageResponseDto;
    }

}
