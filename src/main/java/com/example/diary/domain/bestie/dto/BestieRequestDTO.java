package com.example.diary.domain.bestie.dto;

import com.example.diary.domain.friend.domain.Friend;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 요청받은 값 DTO -> Entity
@Getter
@Setter
@NoArgsConstructor
public class BestieRequestDTO {
    protected Long id;
    protected String userNickname;
    protected String bestie;

//    public BestieRequestDTO(String userid, String bestie) {
//        this.userid = userid;
//        this.bestie = bestie;
//    }
}
