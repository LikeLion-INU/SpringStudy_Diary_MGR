package com.example.diary.board.service;

import com.example.diary.board.dto.BoardRequestDto;
import com.example.diary.board.dto.BoardResponseDto;

import java.io.IOException;
import java.util.List;

public interface BoardServiceImpl {

    /*
    일기장 업로드
    - date 정보에 따른 날씨 반환 (Api 사용)
     */
    BoardResponseDto.BoardUploadDto upload(BoardRequestDto.BoardUploadDto boardUploadDto) throws IOException;

    /*
    모든 일기장 가져오기 (목록 조회)
     */
    List<BoardResponseDto.BoardInfoDto> findAll();

    /*
    일기장 수정
     */
    BoardResponseDto.BoardInfoDto update(BoardRequestDto.BoardUpdateDto boardUpdateDto);

    /*
    일기장 하나 가져오기
     */
    BoardResponseDto.BoardInfoDto get(Long boardId);

    /*
    일기 삭제
     */
    String delete(Long boardId);
}
