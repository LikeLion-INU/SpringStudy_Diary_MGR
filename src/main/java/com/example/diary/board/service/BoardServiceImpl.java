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
    List<BoardResponseDto.BoardListDto> findAll();

    /*
    일기장 수정
    - 도시가 바뀌었을 경우 날씨도 같이 변경
    - 이미지 파일 바뀌었을 경우 변경, 이미지 삭제한 경우 삭제
     */
    BoardResponseDto.BoardInfoDto update(Long id, BoardRequestDto.BoardUpdateDto boardUpdateDto) throws IOException;

    /*
        날씨 수정
         */
    BoardResponseDto.BoardInfoDto updateWeather(Long id, String weather);

    BoardResponseDto.BoardInfoDto updateCity(Long id, String city);


    /*
    일기장 하나 가져오기
     */
    BoardResponseDto.BoardInfoDto get(Long boardId);

    /*
    일기 삭제
     */
    String delete(Long boardId);
}
