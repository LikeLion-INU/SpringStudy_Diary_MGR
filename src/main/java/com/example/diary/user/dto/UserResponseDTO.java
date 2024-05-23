package com.example.diary.user.dto;

import com.example.diary.user.domain.Users;
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

    public static UserResponseDTO toUserDTO(Users users) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(users.getId());
        userDTO.setUserEmail(users.getUserEmail());
        userDTO.setUserNickname(users.getUserNickname());
        userDTO.setUserGender(users.getUserGender());
        userDTO.setUserBirth(users.getUserBirth());
        userDTO.setUserArea(users.getUserArea());
        userDTO.setUserMbti(users.getUserMbti());
        return userDTO;
    }

    public static UserResponseDTO toUserOptionalDTO(Optional<Users> users) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(users.get().getId());
        userDTO.setUserEmail(users.get().getUserEmail());
        userDTO.setUserNickname(users.get().getUserNickname());
        userDTO.setUserGender(users.get().getUserGender());
        userDTO.setUserBirth(users.get().getUserBirth());
        userDTO.setUserArea(users.get().getUserArea());
        userDTO.setUserMbti(users.get().getUserMbti());
        return userDTO;
    }
}
