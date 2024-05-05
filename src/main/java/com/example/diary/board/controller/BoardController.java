package com.example.diary.board.controller;

import com.example.diary.board.dto.BoardRequestDto;
import com.example.diary.board.dto.BoardResponseDto;
import com.example.diary.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/upload")
    public ResponseEntity<BoardResponseDto.BoardUploadDto> upload(@ModelAttribute BoardRequestDto.BoardUploadDto boardUploadDto) throws IOException {
        return ResponseEntity.ok()
                .body(boardService.upload(boardUploadDto));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<BoardResponseDto.BoardInfoDto>> findAll(){
        return ResponseEntity.ok()
                .body(boardService.findAll());
    }

    @Transactional
    @PutMapping("/update")
    public ResponseEntity<BoardResponseDto.BoardInfoDto> update(@ModelAttribute BoardRequestDto.BoardUpdateDto boardUpdateDto){
        return ResponseEntity.ok()
                .body(boardService.update(boardUpdateDto));
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
