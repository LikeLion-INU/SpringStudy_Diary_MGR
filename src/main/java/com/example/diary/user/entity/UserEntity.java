package com.example.diary.user.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "diary_user")
public class UserEntity {
    //entity로 받은거 DTO 넣고, entity로 꺼낸거 DTO로 바꾸고
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userEmail;

    @Column
    private String password;

    @Column
    private String userNickname;

    @Column
    private String userGender;

    @Column
    private String userBirth;

    @Column
    private String userArea;

    @Column
    private String userMbti;

    public UserEntity() {}

    public UserEntity(String userEmail, String password, String userNickname, String userGender, String userBirth, String userArea, String userMbti) {
        this.userEmail = userEmail;
        this.password = password;
        this.userNickname = userNickname;
        this.userGender = userGender;
        this.userBirth = userBirth;
        this.userArea = userArea;
        this.userMbti = userMbti;
    }

    public void editUser(String userNickname, String userGender, String userBirth, String userArea, String userMbti) {
        this.userNickname = userNickname;
        this.userGender = userGender;
        this.userBirth = userBirth;
        this.userArea = userArea;
        this.userMbti = userMbti;
    }
}
