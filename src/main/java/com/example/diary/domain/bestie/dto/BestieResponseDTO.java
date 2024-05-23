package com.example.diary.domain.bestie.dto;

import com.example.diary.domain.friend.domain.Friend;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BestieResponseDTO {
    protected Long id;
    protected String userid;
    protected String bestie;

    public BestieResponseDTO(){}
}
