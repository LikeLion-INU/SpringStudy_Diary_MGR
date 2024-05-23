package com.example.diary.domain.board.service;

import com.example.diary.domain.board.domain.Board;
import com.example.diary.domain.image.domain.BoardImage;
import com.example.diary.domain.board.domain.Scope;
import com.example.diary.domain.board.dto.BoardRequestDto;
import com.example.diary.domain.board.dto.BoardResponseDto;
import com.example.diary.domain.image.dto.BoardImageRequestDto;
import com.example.diary.domain.image.dto.BoardImageResponseDto;
import com.example.diary.domain.image.repository.BoardImageRepository;
import com.example.diary.domain.board.repository.BoardRepository;
import com.example.diary.global.DateService;
import com.example.diary.domain.weather.service.WeatherService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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
        log.info("[Update] Board Update");
        return BoardResponseDto.BoardInfoDto.toDto(board);
    }

    @Override
    public BoardResponseDto.BoardInfoDto updateWeather(Long id, String weather) {
        Board board = boardRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        board.toUpdateWeather(weather);
        log.info("[Update] Weather Update");
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

    @Override
    public BoardImageResponseDto updateImage(BoardImageRequestDto boardImageRequestDto) throws IOException {
        BoardImage boardImage = boardImageRepository.findById(boardImageRequestDto.getId())
                .orElseThrow(() -> new RuntimeException("게시판 이미지를 찾을 수 없습니다."));
        if (boardImageRequestDto.getImage() == null){
            log.info("[Update] No Image File");
            boardImageRepository.deleteById(boardImage.getId());
        } else{
            if (boardImage.getData() != boardImageRequestDto.getImage().getBytes()) {
                log.info("[Update] Image File Update");
                boardImage.toUpdateData(boardImageRequestDto.getImage().getBytes());
            } else{
                log.info("[Update] Same Image File");
            }
        }
        return new BoardImageResponseDto(BoardImageResponseDto.convertByteArrayToBase64(boardImageRequestDto.getImage().getBytes()));

    }

    private Board updateBoard(Long id, BoardRequestDto.BoardUpdateDto boardUpdateDto) throws IOException {
        Board board = boardRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
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

    @Override
    public List<BoardResponseDto.BoardListDto> findAllCanSee() {
        //        Long userId = httpSession.getAttribute("user");
        //todo 유저까지 반영
//        List<Long> bestieId = bestieRepository.findAllByUserId(userId);
        //나를 친한 친구로 생각해주는 친구들의 아이디 리스트를 가져와서 그 친구들의 Bestie 게시물 반환

        List<Long> bestieId = new ArrayList<>();
        bestieId.add((long)2);
        bestieId.add((long)3);
        List<Board> publicBoards = boardRepository.findAllByScope(Scope.PUBLIC);
        List<Board> bestieBoards = new ArrayList<>();
        for (Long aLong : bestieId) {
            List<Board> boards = boardRepository.findAllByScopeAndUserId(Scope.BESTIE, aLong);
            bestieBoards.addAll(boards);
        }
        log.info("[findAll] Find all Boards List that User can see");

        List<BoardResponseDto.BoardListDto> boardListDtos = new ArrayList<>();
        boardListDtos.addAll(publicBoards.stream()
                .map(BoardResponseDto.BoardListDto::toDto).toList());
        boardListDtos.addAll(bestieBoards.stream()
                .map(BoardResponseDto.BoardListDto::toDto)
                .toList());

        return boardListDtos;
    }

    public Scope updateScope(Long id, BoardRequestDto.BoardScopeUpdateDto boardScopeUpdateDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow((() -> new RuntimeException("게시판을 찾을 수 없습니다.")));
        board.toUpdateScope(boardScopeUpdateDto.getScope());
        return boardScopeUpdateDto.getScope();
    }
}
