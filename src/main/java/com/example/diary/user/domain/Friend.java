package com.example.diary.user.domain;

import com.example.diary.global.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name="friend")
public class Friend extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String follower;

    @Column
    private String receiver;

    @Column
    private String accept;
}
