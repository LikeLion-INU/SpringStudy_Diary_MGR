package com.example.diary.domain.user.service;

import com.example.diary.domain.bestie.dto.BestieResponseDTO;
import com.example.diary.domain.bestie.repository.BestieRepository;
import com.example.diary.domain.friend.domain.Friend;
import com.example.diary.domain.user.domain.Users;
import com.example.diary.domain.friend.dto.FriendResponseDTO;
import com.example.diary.domain.user.dto.UserRequestDTO;
import com.example.diary.domain.user.dto.UserResponseDTO;
import com.example.diary.domain.friend.repository.FriendRepository;
import com.example.diary.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final BestieRepository bestieRepository;

    // 로그인
    public UserResponseDTO login(UserRequestDTO userRequestDTO) {
        Optional<Users> byUserEmail = userRepository.findByUserEmail(userRequestDTO.getUserEmail());

        if (byUserEmail.isPresent()) {
            Users users = byUserEmail.get();

            if (users.getPassword().equals(userRequestDTO.getPassword())) {
                return UserResponseDTO.toUserDTO(users);
            } else return null;
        } else return null;
    }

    //회원가입
    public UserResponseDTO save(UserRequestDTO userRequestDTO) {
        Users user = UserRequestDTO.toSaveEntity(userRequestDTO);
        userRepository.save(user);
        return UserResponseDTO.toUserDTO(user);
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

    // 회원 삭제
    @Transactional
    public String deleteUser(Long id) {
        Optional<Users> userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            Users user = userEntity.get();

            // FRIEND, BESTIE 테이블에 존재하는 회원 삭제
            friendRepository.deleteByFollowerOrReceiver(user.getUserNickname(), user.getUserNickname());
            bestieRepository.deleteByUsers(user);
            bestieRepository.deleteByBestie(user.getUserNickname());

            // USERS 테이블에 존재하는 회원 삭제
            userRepository.deleteById(user.getId());
            return "User Delete Success";
        } else return "User Delete Fail";
    }
}
