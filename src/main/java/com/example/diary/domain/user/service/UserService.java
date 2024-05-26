package com.example.diary.domain.user.service;

import com.example.diary.domain.bestie.domain.Bestie;
import com.example.diary.domain.bestie.dto.BestieResponseDTO;
import com.example.diary.domain.bestie.repository.BestieRepository;
import com.example.diary.domain.user.domain.Users;
import com.example.diary.domain.user.dto.UserRequestDTO;
import com.example.diary.domain.user.dto.UserResponseDTO;
import com.example.diary.domain.friend.repository.FriendRepository;
import com.example.diary.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final BestieRepository bestieRepository;

    // 로그인
    public UserResponseDTO login(UserRequestDTO userRequestDTO, HttpSession session) {
        Optional<Users> user = userRepository.findByUserEmail(userRequestDTO.getUserEmail());

        if (user.isPresent()) {
            // 세션 값 저장
            session.setAttribute("userId", user.get().getId());
            session.setAttribute("userEmail", user.get().getUserEmail());
            session.setAttribute("userName", user.get().getUserNickname());

            // 비밀번호 일치 하면 로그인 성공
            Users users = user.get();
            if (users.getPassword().equals(userRequestDTO.getPassword())) {
                return UserResponseDTO.toUserDTO(users);
            } else {
                log.info("Password Error!");
                return null;
            }
        } else {
            log.info("User Not Found!");
            return null;
        }
    }

    // 로그아웃
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
    }

    //회원가입
    public UserResponseDTO save(UserRequestDTO userRequestDTO) {
        Users user = UserRequestDTO.toSaveEntity(userRequestDTO);
        userRepository.save(user);
        return UserResponseDTO.toUserDTO(user);
    }

    public String checkEmailDuplicate(UserRequestDTO userDTO) {
        String email = userDTO.getUserEmail();
        Optional<Users> user = userRepository.findByUserEmail(email);

        // Email 중복일 경우 true 리턴
        if (user.isPresent()) {
            return "true";
        } else return "false";
    }

    public String checkNicknameDuplicate(UserRequestDTO userDTO) {
        String nickname = userDTO.getUserNickname();
        Optional<Users> user = userRepository.findByUserNickname(nickname);

        // Nickname 중복일 경우 true 리턴
        if (user.isPresent()) {
            return "true";
        } else return "false";
    }

    //회원조회(전체)
    public List<UserResponseDTO> getAllUser(){
        List<Users> usersList = userRepository.findAll();
                return usersList.stream()
                        .map(m -> UserResponseDTO.toSearchAllUser(m.getId(), m.getUserEmail(), m.getUserNickname(), m.getUserGender(), m.getUserBirth(), m.getUserArea(), m.getUserMbti()))
                        .collect(Collectors.toList());
    }

    //회원조회(개인)
    public UserResponseDTO searchOneUser(Long id) {
        Optional<Users> user = userRepository.findById(id);

        if (user.isPresent()) {
            return UserResponseDTO.toUserOptionalDTO(user);
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
