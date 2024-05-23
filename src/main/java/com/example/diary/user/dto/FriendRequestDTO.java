package com.example.diary.user.dto;

import com.example.diary.user.domain.Friend;
import com.example.diary.user.domain.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 요청받은 값 DTO -> Entity
@Getter
@Setter
@NoArgsConstructor
public class FriendRequestDTO {
    protected Long id;
    protected String follower;
    protected String receiver;
    protected String accept;

    public FriendRequestDTO(String follower, String receiver, String accept) {
        this.follower = follower;
        this.receiver = receiver;
        this.accept = accept;
    }

    //회원가입
    public static Friend toRequestFriend(FriendRequestDTO friendRequestDTO) {
        String follower = friendRequestDTO.getFollower();
        String receiver = friendRequestDTO.getReceiver();
        String accept = friendRequestDTO.getAccept();

        return new Friend(follower, receiver, accept);
    }
}
