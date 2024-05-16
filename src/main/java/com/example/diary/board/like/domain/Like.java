package com.example.diary.board.like.domain;

import com.example.diary.board.domain.Board;
import com.example.diary.global.BaseEntity;
import com.example.diary.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "liked")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Like extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private Board board;

    @OneToOne(fetch = FetchType.EAGER)
    private UserEntity user;

}
