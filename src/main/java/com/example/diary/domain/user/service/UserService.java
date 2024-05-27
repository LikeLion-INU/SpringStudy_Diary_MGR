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
        if (checkEmailDuplicate(userRequestDTO)) {
            Users user = userRepository.findByUserEmail(userRequestDTO.getUserEmail())
                    .orElseThrow(() -> new RuntimeException("존재하지 않은 회원입니다."));

            // 세션 값 저장
            session.setAttribute("userId", user.getId());
            session.setAttribute("userEmail", user.getUserEmail());
            session.setAttribute("userName", user.getUserNickname());
            log.info("[Login] User login success, {} {}", user.getUserNickname(), user.getUserEmail());

            // 비밀번호 일치 하면 로그인 성공
            if (user.getPassword().equals(userRequestDTO.getPassword())) {
                return UserResponseDTO.toUserDTO(user);
            } else {
                log.warn("Password Error");
                throw new RuntimeException("Password Error");
            }
        } else {
            log.warn("User Not Found");
            throw new RuntimeException("User Not Found");
        }
    }

    // 로그아웃
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        log.info("Logout Success");
    }

    //회원가입
    public UserResponseDTO save(UserRequestDTO userRequestDTO) {
        //이메일 중복 검사
        Optional<Users> userInfo = userRepository.findByUserEmail(userRequestDTO.getUserEmail());
        if (checkEmailDuplicate(userRequestDTO)) throw new RuntimeException("이미 존재하는 이메일입니다.");
        else {
            Users user = UserRequestDTO.toSaveEntity(userRequestDTO);
            userRepository.save(user);
            log.info("[Join] User join success, {} {}", userRequestDTO.getUserNickname(), userRequestDTO.getUserEmail());
            return UserResponseDTO.toUserDTO(user);
        }
    }

    public boolean checkEmailDuplicate(UserRequestDTO userDTO) {
        String email = userDTO.getUserEmail();
        Optional<Users> user = userRepository.findByUserEmail(email);
        log.info("Check Email Duplicate");
        // Email 중복일 경우 true 리턴
        return user.isPresent();
    }

    public boolean checkNicknameDuplicate(UserRequestDTO userDTO) {
        String nickname = userDTO.getUserNickname();
        Optional<Users> user = userRepository.findByUserNickname(nickname);
        log.info("Check NickName Duplicate");
        // Nickname 중복일 경우 true 리턴
        return user.isPresent();
    }

    //회원조회(전체)
    public List<UserResponseDTO> getAllUser(){
        List<Users> usersList = userRepository.findAll();
        log.info("Get All Users");
                return usersList.stream()
                        .map(m -> UserResponseDTO.toSearchAllUser(m.getId(), m.getUserEmail(), m.getUserNickname(), m.getUserGender(), m.getUserBirth(), m.getUserArea(), m.getUserMbti()))
                        .collect(Collectors.toList());
    }

    //회원조회(개인)
    public UserResponseDTO searchOneUser(Long id) {
        Optional<Users> user = userRepository.findById(id);
        if (user.isPresent()) {
            log.info("Get User {} info", user.get().getUserNickname());
            return UserResponseDTO.toUserOptionalDTO(user);
        } else return null;
    }

    //회원 정보 수정
    @Transactional
    public UserResponseDTO editUser(UserRequestDTO userRequestDTO) {
        Optional<Users> userEntity = userRepository.findByUserNickname(userRequestDTO.getUserNickname());
        log.info("[Edit] User {} info", userRequestDTO.getUserNickname());
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
            log.info("[Delete] user");
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
