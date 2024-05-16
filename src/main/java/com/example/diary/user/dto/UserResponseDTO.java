package com.example.diary.user.dto;

import com.example.diary.user.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

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
        userDTO.setUserEmail(userEntity.getUserEmail());
        userDTO.setUserNickname(userEntity.getUserNickname());
        userDTO.setUserGender(userEntity.getUserGender());
        userDTO.setUserBirth(userEntity.getUserBirth());
        userDTO.setUserArea(userEntity.getUserArea());
        userDTO.setUserMbti(userEntity.getUserMbti());
        return userDTO;
    }

    public static UserResponseDTO toUserOptionalDTO(Optional<UserEntity> userEntity) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(userEntity.get().getId());
        userDTO.setUserEmail(userEntity.get().getUserEmail());
        userDTO.setUserNickname(userEntity.get().getUserNickname());
        userDTO.setUserGender(userEntity.get().getUserGender());
        userDTO.setUserBirth(userEntity.get().getUserBirth());
        userDTO.setUserArea(userEntity.get().getUserArea());
        userDTO.setUserMbti(userEntity.get().getUserMbti());
        return userDTO;
    }
}
