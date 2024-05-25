package com.example.diary.domain.bestie.dto;

import com.example.diary.domain.bestie.domain.Bestie;
import com.example.diary.domain.friend.domain.Friend;
import com.example.diary.domain.friend.dto.FriendResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BestieResponseDTO {
    protected Long id;
    protected String userNickname;
    protected String bestie;

    public BestieResponseDTO(){}

    public static BestieResponseDTO toBestieDTO(Bestie bestie) {
        BestieResponseDTO bestieDTO = new BestieResponseDTO();
        bestieDTO.setId(bestie.getId());
        bestieDTO.setUserNickname(bestie.getUsers().getUserNickname());
        bestieDTO.setBestie(bestie.getBestie());
        return bestieDTO;
    }
}
