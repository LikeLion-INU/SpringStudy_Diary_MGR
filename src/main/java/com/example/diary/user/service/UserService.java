package com.example.diary.user.service;

import com.example.diary.user.dto.UserRequestDTO;
import com.example.diary.user.dto.UserResponseDTO;
import com.example.diary.user.domain.Users;
import com.example.diary.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //회원가입
    public UserRequestDTO save(UserRequestDTO userRequestDTO) {
        //회원가입 dto -> entitiy
        Users user = UserRequestDTO.toSaveEntity(userRequestDTO);
        userRepository.save(user);
        return userRequestDTO;
    }

    //회원조회(전체)
    public List<Users> getAllUser(){return userRepository.findAll();}

    //회원조회(개인)
    public UserResponseDTO searchOneUser(Long id) {
        Optional<Users> userId = userRepository.findById(id);

        if (userId.isPresent()) {
            return UserResponseDTO.toUserOptionalDTO(userId);
        } else return null;
    }

    //회원 정보 수정
    @Transactional
    public UserResponseDTO editUser(UserRequestDTO userRequestDTO) {
        Optional<Users> userEntity = userRepository.findByUserNickname(userRequestDTO.getUserNickname());

        if (userEntity.isPresent()) {
            Users user  = userEntity.get();
            user.editUser(userRequestDTO.getUserNickname(), userRequestDTO.getUserGender(), userRequestDTO.getUserBirth(), userRequestDTO.getUserArea(), userRequestDTO.getUserMbti());

            return UserResponseDTO.toUserDTO(user);
        } else return null;
    }

    @Transactional
    public UserResponseDTO deleteUser(Long id) {
        Optional<Users> userEntity = userRepository.findById(id);

        log.info(userEntity.toString());

        if (userEntity.isPresent()) {
            Users user = userEntity.get();
            userRepository.deleteById(user.getId());
            return UserResponseDTO.toUserDTO(user);
        } else return null;
    }

    public UserResponseDTO login(UserRequestDTO userRequestDTO) {
        Optional<Users> byUserEmail = userRepository.findByUserEmail(userRequestDTO.getUserEmail());

        if (byUserEmail.isPresent()) {
            Users users = byUserEmail.get();

            if (users.getPassword().equals(userRequestDTO.getPassword())) {
                return UserResponseDTO.toUserDTO(users);
            } else return null;
        } else return null;
    }
}
