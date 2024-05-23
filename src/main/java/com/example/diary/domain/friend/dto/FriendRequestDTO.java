package com.example.diary.domain.friend.dto;

import com.example.diary.domain.friend.domain.Friend;
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

    // 친구 요청
    public static Friend toRequestFriend(FriendRequestDTO friendRequestDTO) {
        String follower = friendRequestDTO.getFollower();
        String receiver = friendRequestDTO.getReceiver();
        String accept = friendRequestDTO.getAccept();

        return new Friend(follower, receiver, accept);
    }
}
