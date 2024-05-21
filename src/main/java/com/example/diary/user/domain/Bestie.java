package com.example.diary.user.domain;

import com.example.diary.global.BaseEntity;
import jakarta.persistence.*;

@Entity
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
}
