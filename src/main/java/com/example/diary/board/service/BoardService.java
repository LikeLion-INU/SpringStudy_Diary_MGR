package com.example.diary.board.service;

import com.example.diary.board.domain.Board;
import com.example.diary.board.domain.BoardImage;
import com.example.diary.board.domain.Scope;
import com.example.diary.board.dto.BoardRequestDto;
import com.example.diary.board.dto.BoardResponseDto;
import com.example.diary.board.repository.BoardImageRepository;
import com.example.diary.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService implements BoardServiceImpl{

    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;
    private final JdbcTemplate jdbcTemplate;
    @Override
    public BoardResponseDto.BoardUploadDto upload(BoardRequestDto.BoardUploadDto boardUploadDto) throws IOException {
        BoardImage boardImage = null;
        if (boardUploadDto.getImage()!=null){
            boardImage = BoardImage.builder()
                    .data(boardUploadDto.getImage().getBytes())
                    .build();
            boardImageRepository.save(boardImage);
        }
        String weather = WeatherService.getWeather(boardUploadDto.getCity(), boardUploadDto.getDate(), jdbcTemplate);
        Board board = Board.builder()
                .title(boardUploadDto.getTitle())
                .date(DateService.strToDate(boardUploadDto.getDate()))
                .content(boardUploadDto.getContent())
                .city(boardUploadDto.getCity())
                .scope(Scope.valueOf(boardUploadDto.getScope()))
                .weather(weather)
                .boardImage(boardImage)
//                .user(user)
                .build();
        boardRepository.save(board);
        return BoardResponseDto.BoardUploadDto.toDto(board);
    }

    @Override
    public List<BoardResponseDto.BoardInfoDto> findAll() {
        return null;
    }

    @Override
    public BoardResponseDto.BoardInfoDto update(BoardRequestDto.BoardUpdateDto boardUpdateDto) {
        return null;
    }

    @Override
    public BoardResponseDto.BoardInfoDto get(Long boardId) {
        return null;
    }

    @Override
    public String delete(Long boardId) {
        return null;
    }
}
