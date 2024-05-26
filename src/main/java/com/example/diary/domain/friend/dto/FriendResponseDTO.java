package com.example.diary.domain.friend.dto;

import com.example.diary.domain.friend.domain.Friend;
import lombok.Getter;
import lombok.Setter;

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
