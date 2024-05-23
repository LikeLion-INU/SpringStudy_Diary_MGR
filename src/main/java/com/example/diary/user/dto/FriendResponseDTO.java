package com.example.diary.user.dto;

import com.example.diary.user.domain.Friend;
import com.example.diary.user.domain.Users;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class FriendResponseDTO {
    protected Long id;
    protected String follower;
    protected String receiver;
    protected String accept;

    public FriendResponseDTO(){}

    public static FriendResponseDTO toFriendDTO(Friend friend) {
        FriendResponseDTO friendDTO = new FriendResponseDTO();
        friendDTO.setId(friend.getId());
        friendDTO.setFollower(friend.getFollower());
        friendDTO.setReceiver(friend.getReceiver());
        friendDTO.setAccept(friend.getAccept());
        return friendDTO;
    }
}
