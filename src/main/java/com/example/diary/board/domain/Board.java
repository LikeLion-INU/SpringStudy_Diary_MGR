package com.example.diary.board.domain;

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

//    private User user;
}
