package com.example.diary.dto;

import com.example.diary.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

// 받아온 dto를
@Getter
@Setter
public class UserResponseDTO {
    protected Long id;
    protected String userEmail;
    protected String userNickname;
    protected String userGender;
    protected String userBirth;
    protected String userArea;
    protected String userMbti;
    
    public UserResponseDTO(){}

    public static UserResponseDTO toUserDTO(UserEntity userEntity) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUserEmail(userDTO.getUserEmail());
        userDTO.setUserNickname(userDTO.getUserNickname());
        return userDTO;
    }
}
