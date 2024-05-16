package com.example.diary.user.dto;

import com.example.diary.user.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 요청받은 값 DTO -> Entity
@Getter
@Setter
@NoArgsConstructor
public class UserRequestDTO {
    protected Long id;
    protected String userEmail;
    protected String password;
    protected String userNickname;
    protected String userGender;
    protected String userBirth;
    protected String userArea;
    protected String userMbti;

    public UserRequestDTO(String userEmail, String password, String userNickname, String userGender, String userBirth, String userArea, String userMbti) {
        this.userEmail = userEmail;
        this.password = password;
        this.userNickname = userNickname;
        this.userGender = userGender;
        this.userBirth = userBirth;
        this.userArea = userArea;
        this.userMbti = userMbti;
    }

    //회원가입
    public static UserEntity toSaveEntity(UserRequestDTO userRequestDTO) {
        String userEmail = userRequestDTO.getUserEmail();
        String password = userRequestDTO.getPassword();
        String userNickname = userRequestDTO.getUserNickname();
        String userGender = userRequestDTO.getUserGender();
        String userBirth = userRequestDTO.getUserBirth();
        String userArea = userRequestDTO.getUserArea();
        String userMbti = userRequestDTO.getUserMbti();

        return new UserEntity(userEmail, password, userNickname, userGender, userBirth, userArea, userMbti);
    }
}
