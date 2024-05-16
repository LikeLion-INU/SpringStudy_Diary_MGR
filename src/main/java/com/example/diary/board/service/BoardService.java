package com.example.diary.board.service;

import com.example.diary.board.domain.Board;
import com.example.diary.board.domain.BoardImage;
import com.example.diary.board.domain.Scope;
import com.example.diary.board.dto.BoardRequestDto;
import com.example.diary.board.dto.BoardResponseDto;
import com.example.diary.board.repository.BoardImageRepository;
import com.example.diary.board.repository.BoardRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService implements BoardServiceImpl{

    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;
    private final JdbcTemplate jdbcTemplate;
    private final HttpSession httpSession;
    @Override
    public BoardResponseDto.BoardUploadDto upload(BoardRequestDto.BoardUploadDto boardUploadDto) throws IOException {
        Board board = insertBoard(boardUploadDto);
        log.info("[Save] Save Board");
        boardRepository.save(board);
        return BoardResponseDto.BoardUploadDto.toDto(board);
    }

    @Override
    public List<BoardResponseDto.BoardListDto> findAll() {
//        User user = httpSession.getAttribute("user");
        //todo 유저까지 반영
        List<Board> boards = boardRepository.findAll();
        log.info("[findAll] Find all Boards List");
        return boards.stream()
                .map(BoardResponseDto.BoardListDto::toDto)
                .toList();
    }

    @Override
    public BoardResponseDto.BoardInfoDto update(Long id, BoardRequestDto.BoardUpdateDto boardUpdateDto) throws IOException {
        Board board = updateBoard(id, boardUpdateDto);
        return BoardResponseDto.BoardInfoDto.toDto(board);
    }

    @Override
    public BoardResponseDto.BoardInfoDto updateWeather(Long id, String weather) {
        Board board = boardRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        board.toUpdateWeather(weather);
        return BoardResponseDto.BoardInfoDto.toDto(board);
    }

    @Override
    public BoardResponseDto.BoardInfoDto updateCity(Long id, String city) {
        Board board = boardRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        String weather = WeatherService.getWeather(city, String.valueOf(board.getDate()), jdbcTemplate);
        board.toUpdateWeatherAndCity(weather, city);
        log.info("[Update] City Update & Weather Update");
        return BoardResponseDto.BoardInfoDto.toDto(board);
    }

    private Board updateBoard(Long id, BoardRequestDto.BoardUpdateDto boardUpdateDto) throws IOException {
        Board board = boardRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        if (boardUpdateDto.getImage() == null){
            log.info("[Update] No Image File");
            boardImageRepository.deleteById(board.getBoardImage().getId());
        } else{
            if (board.getBoardImage().getData() != boardUpdateDto.getImage().getBytes()) {
                log.info("[Update] Image File Update");
                board.getBoardImage().toUpdateData(boardUpdateDto.getImage().getBytes());
            } else{
                log.info("[Update] Same Image File");
            }
        }

        board.toUpdateBoard(boardUpdateDto);
        log.info("[Update] Board Update Success");
        return board;
    }

    private Board insertBoard(BoardRequestDto boardRequestDto) throws IOException {
//        User user = httpSession.getAttribute("user");
        Board board = boardRepository.findByDate(DateService.strToDate(boardRequestDto.getDate()));
        if (board == null) {
            BoardImage boardImage = null;
            if (boardRequestDto.getImage() != null) {
                boardImage = BoardImage.builder()
                        .data(boardRequestDto.getImage().getBytes())
                        .build();
                boardImageRepository.save(boardImage);
            }
            String weather = WeatherService.getWeather(boardRequestDto.getCity(), boardRequestDto.getDate(), jdbcTemplate);
            if (weather == null) {
                throw new IllegalArgumentException();
            }
            return Board.builder()
                    .title(boardRequestDto.getTitle())
                    .date(DateService.strToDate(boardRequestDto.getDate()))
                    .content(boardRequestDto.getContent())
                    .city(boardRequestDto.getCity())
                    .scope(Scope.valueOf(boardRequestDto.getScope()))
                    .weather(weather)
                    .boardImage(boardImage)
//                .user(user)
                    .build();
        } else {
            throw new IllegalArgumentException("일기장 이미 존재");
        }
    }

    @Override
    public BoardResponseDto.BoardInfoDto get(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(IllegalArgumentException::new);
        log.info("[Get] Get Board");
        return BoardResponseDto.BoardInfoDto.toDto(board);
    }

    @Override
    public String delete(Long boardId) {
        Board board = boardRepository.findById(boardId)
                        .orElseThrow(IllegalArgumentException::new);
        Long boardImageId = board.getBoardImage().getId();
        boardImageRepository.deleteById(boardImageId);
        boardRepository.deleteById(boardId);
        log.info("[Delete] Board & BoardImage Delete");
        return "Delete";
    }
}
