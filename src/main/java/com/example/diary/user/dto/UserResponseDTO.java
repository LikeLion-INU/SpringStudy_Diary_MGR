package com.example.diary.user.dto;

<<<<<<< HEAD
import com.example.diary.user.domain.Users;
=======
import com.example.diary.user.entity.UserEntity;
>>>>>>> ee555ed5055bb2c4aaef0da1b5e7611521ef330a
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

    public static UserResponseDTO toUserOptionalDTO(Optional<Users> userEntity) {
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
