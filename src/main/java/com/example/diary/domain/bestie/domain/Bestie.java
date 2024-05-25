package com.example.diary.domain.bestie.domain;

import com.example.diary.domain.user.domain.Users;
import com.example.diary.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name="bestie")
public class Bestie extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id", updatable = false)
    Users users;

    @Column
    private String bestie;

    public Bestie() {}

    public Bestie(Users nickname, String bestieNickname) {
        this.users = nickname;
        this.bestie = bestieNickname;
    }
}
