package com.example.diary.domain.board.domain;

import com.example.diary.domain.board.dto.BoardRequestDto;
import com.example.diary.domain.image.domain.BoardImage;
import com.example.diary.global.BaseEntity;
import com.example.diary.domain.user.domain.Users;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    public void toUpdateLike(int likeCnt){
        this.likeCnt = likeCnt;
    }
    public void toUpdateBoard(BoardRequestDto.BoardUpdateDto boardUpdateDto) {
        this.title = boardUpdateDto.getTitle();
        this.content = boardUpdateDto.getContent();
    }

    public void toUpdateWeatherAndCity(String weather, String city){
        this.city = city;
        this.weather = weather;
    }

    public void toUpdateWeather(String weather) {
        this.weather = weather;
    }

    public void toUpdateScope(Scope scope) {this.scope = scope;}

    public void toUpdateBoardImage(BoardImage boardImage) {
        this.boardImage = boardImage;
    }
}
