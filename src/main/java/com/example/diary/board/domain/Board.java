package com.example.diary.board.domain;

import com.example.diary.board.dto.BoardRequestDto;
import com.example.diary.board.image.domain.BoardImage;
import com.example.diary.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "board")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;
    @NonNull
    private LocalDate date;
    private String content;
    private String weather;
    private String city;

    private Scope scope;

    @OneToOne(fetch = FetchType.LAZY)
    private BoardImage boardImage;

    private int likeCnt = 0;

//    private User user;

    public void toUpdateLike(int likeCnt){
        this.likeCnt = likeCnt;
    }
    public void toUpdateBoard(BoardRequestDto.BoardUpdateDto boardUpdateDto) {
        this.title = boardUpdateDto.getTitle();
        this.content = boardUpdateDto.getContent();
        this.scope = Scope.valueOf(boardUpdateDto.getScope());
    }

    public void toUpdateWeatherAndCity(String weather, String city){
        this.city = city;
        this.weather = weather;
    }

    public void toUpdateWeather(String weather) {
        this.weather = weather;
    }
}
