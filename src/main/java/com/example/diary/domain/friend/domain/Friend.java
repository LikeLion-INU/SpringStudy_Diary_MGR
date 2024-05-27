package com.example.diary.domain.friend.domain;

import com.example.diary.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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

    public Friend() {}

    public Friend(String follower, String receiver, String accept) {
        this.follower = follower;
        this.receiver = receiver;
        this.accept = accept;
    }

    public void acceptFriend(String follower, String receiver, String accept) {
        this.follower = follower;
        this.receiver = receiver;
        this.accept = accept;
    }

    public void toUpdateAccept(String accept){
        this.accept = accept;
    }
}
