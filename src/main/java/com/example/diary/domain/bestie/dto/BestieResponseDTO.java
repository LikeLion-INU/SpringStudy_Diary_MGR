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
    protected String userId;
    protected String bestie;

    public BestieResponseDTO(){}

    public static BestieResponseDTO toSearchBestieDTO(Long id, String bestie, String userId) {
        BestieResponseDTO bestieDTO = new BestieResponseDTO();
        bestieDTO.setId(id);
        bestieDTO.setBestie(bestie);
        bestieDTO.setUserId(userId);
        return bestieDTO;
    }

    public static BestieResponseDTO toBestieDTO(Bestie bestie) {
        BestieResponseDTO bestieDTO = new BestieResponseDTO();
        bestieDTO.setId(bestie.getId());
        bestieDTO.setUserId(bestie.getUsers().getUserNickname());
        bestieDTO.setBestie(bestie.getBestie());
        return bestieDTO;
    }
}
