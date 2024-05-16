package com.example.diary.board.controller;

import com.example.diary.board.dto.BoardRequestDto;
import com.example.diary.board.dto.BoardResponseDto;
import com.example.diary.board.image.dto.BoardImageRequestDto;
import com.example.diary.board.image.dto.BoardImageResponseDto;
import com.example.diary.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/upload")
    public ResponseEntity<BoardResponseDto.BoardUploadDto> upload(
            @ModelAttribute BoardRequestDto.BoardUploadDto boardUploadDto) throws IOException {
        return ResponseEntity.ok()
                .body(boardService.upload(boardUploadDto));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<BoardResponseDto.BoardListDto>> findAll(){
        return ResponseEntity.ok()
                .body(boardService.findAll());
    }

    @Transactional
    @PutMapping("/update/{board_id}")
    public ResponseEntity<BoardResponseDto.BoardInfoDto> update(
            @PathVariable("board_id") Long id, @RequestBody BoardRequestDto.BoardUpdateDto boardUpdateDto) throws IOException {
        return ResponseEntity.ok()
                .body(boardService.update(id, boardUpdateDto));
    }

    @Transactional
    @PatchMapping("/update/boardImage")
    public ResponseEntity<BoardImageResponseDto> updateImage(@ModelAttribute BoardImageRequestDto boardImageRequestDto) throws IOException {
        return ResponseEntity.ok()
                .body(boardService.updateImage(boardImageRequestDto));
    }

    @Transactional
    @PutMapping("/update/weather/{board_id}")
    public ResponseEntity<BoardResponseDto.BoardInfoDto> updateWeather(
            @PathVariable("board_id") Long id, @RequestBody Map<String, String> requestBody){
        return ResponseEntity.ok()
                .body(boardService.updateWeather(id, requestBody.get("weather")));
    }

    @Transactional
    @PutMapping("/update/city/{board_id}")
    public ResponseEntity<BoardResponseDto.BoardInfoDto> updateCity(
            @PathVariable("board_id") Long id, @RequestBody Map<String, String> requestBody){
        return ResponseEntity.ok()
                .body(boardService.updateCity(id, requestBody.get("city")));
    }

    @GetMapping("/get/{board_id}")
    public ResponseEntity<BoardResponseDto.BoardInfoDto> get(@PathVariable("board_id") Long board_id){
        return ResponseEntity.ok()
                .body(boardService.get(board_id));
    }

    @Transactional
    @DeleteMapping("/delete/{board_id}")
    public ResponseEntity<?> delete(@PathVariable("board_id") Long board_id){
        return ResponseEntity.ok()
                .body(boardService.delete(board_id));
    }
}
