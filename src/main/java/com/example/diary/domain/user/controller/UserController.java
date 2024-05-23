package com.example.diary.domain.user.controller;

import com.example.diary.domain.user.dto.UserRequestDTO;
import com.example.diary.domain.user.dto.UserResponseDTO;
import com.example.diary.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 로그인
    @PostMapping("/user/login")
    public ResponseEntity<?> login (@RequestBody UserRequestDTO userRequestDTO, HttpSession session) {
        UserResponseDTO user = userService.login(userRequestDTO);

        if (user != null) {
            session.setAttribute("userId", user.getId());
            session.setAttribute("userEmail", user.getUserEmail());
            session.setAttribute("userName", user.getUserNickname());

            return ResponseEntity.ok().body(user);
        } else {
            log.info("로그인 실패");

            Map<String, String> response = new HashMap<>();
            response.put("message", "Login Fail");
            return ResponseEntity.ok().body(response);
        }
    }

    @PostMapping("/user/logout")
    public ResponseEntity<?> logout (HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화

            Map<String, String> response = new HashMap<>();
            response.put("message", "LogOut");
            return ResponseEntity.ok().body(response);
        } else return null;
    }

    //회원가입
    @PostMapping("/user/save")
    public ResponseEntity<?> save(@RequestBody UserRequestDTO userDTO) {
        return ResponseEntity.ok().body(userService.save(userDTO));
    }

    //회원 정보 조회 (전체)
    @GetMapping("/user/searchAllUser")
    public ResponseEntity<?> searchAllUser() {
        log.info("회원 정보 조회");
        return ResponseEntity.ok().body(userService.getAllUser());
    }

    //회원 정보 조회 (개인)
    @GetMapping("/user/searchOneUser/{id}")
    public ResponseEntity<?> searchOneUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.searchOneUser(id));
    }

    //회원 정보 수정
    @PostMapping("/user/editUser")
    public ResponseEntity<?> editUser(@RequestBody UserRequestDTO userRequestDTO) {
        log.info("회원 정보 수정");
        return ResponseEntity.ok().body(userService.editUser(userRequestDTO));
    }

    //회원 정보 삭제
    @PostMapping("/user/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }

    // 친구 요청
    // 친구 요청 건 사람 follower & 받은 사람 receiver
    @PostMapping("/user/friend/requestFriend/{id}")
    public ResponseEntity<?> requestFriend(@PathVariable("id") Long id, @RequestBody UserRequestDTO userRequestDTO) {
        // id -> follower, nickname -> receiver
        String nickname = userRequestDTO.getUserNickname();
        return ResponseEntity.ok().body(userService.requestFriend(id, nickname));
    }

    // 요청 건 친구 조회
    @PostMapping("/user/friend/searchRequestFriend")
    public ResponseEntity<?> searchRequestFriend(@RequestBody UserRequestDTO userRequestDTO) {
        String nickname = userRequestDTO.getUserNickname();
        // 닉네임으로 조회 (요청 건 내역만)
        return ResponseEntity.ok().body(userService.searchRequestFriend(nickname));
    }

    // 요청 받은 친구 조회
    @PostMapping("/user/friend/searchReceiveFriend")
    public ResponseEntity<?> searchReceiveFriend(@RequestBody UserRequestDTO userRequestDTO) {
        String nickname = userRequestDTO.getUserNickname();
        // 닉네임으로 조회 (요청 받은 내역만)
        return ResponseEntity.ok().body(userService.searchReceiveFriend(nickname));
    }

    // 친구 요청 승인
    @PostMapping("/user/friend/acceptFriend/{id}")
    public ResponseEntity<?> acceptFriend(@PathVariable("id") Long id, @RequestBody UserRequestDTO userRequestDTO) {
        // id -> follower, nickname -> receiver
        String receiver = userRequestDTO.getUserNickname();
        return ResponseEntity.ok().body(userService.acceptFriend(id, receiver));
        //return null;
    }

    // 친구 조회
    @PostMapping("/user/friend/searchFriend")
    public ResponseEntity<?> searchFriend(@RequestBody UserRequestDTO userRequestDTO) {
        String nickname = userRequestDTO.getUserNickname();
        // 닉네임으로 조회 (ACCEPT 값이 Y인 것만)
        return ResponseEntity.ok().body(userService.searchFriend(nickname));
    }

    // 친구 삭제
    @PostMapping("/user/friend/deleteFriend/{id}")
    public ResponseEntity<?> deleteFriend(@PathVariable("id") Long id, @RequestBody UserRequestDTO userRequestDTO) {
        String receiver = userRequestDTO.getUserNickname();
        return ResponseEntity.ok().body(userService.deleteFriend(id, receiver));
    }
}
