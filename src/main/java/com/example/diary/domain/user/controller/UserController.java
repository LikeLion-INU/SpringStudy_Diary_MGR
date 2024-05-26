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
        return ResponseEntity.ok().body(userService.login(userRequestDTO, session));
    }

    // 로그아웃
    @PostMapping("/user/logout")
    public ResponseEntity<?> logout (HttpServletRequest request){
        userService.logout(request);
        return ResponseEntity.ok().body("Logout Success!");
    }

    //회원가입
    @PostMapping("/user/save")
    public ResponseEntity<?> save (@RequestBody UserRequestDTO userDTO) {
        return ResponseEntity.ok().body(userService.save(userDTO));
    }

    // 이메일 중복 체크
    @PostMapping("/user/checkEmailDuplicate")
    public ResponseEntity<?> checkEmailDuplicate (@RequestBody UserRequestDTO userDTO) {
        return ResponseEntity.ok().body(userService.checkEmailDuplicate(userDTO));
    }

    // 닉네임 중복 체크
    @PostMapping("/user/checkNicknameDuplicate")
    public ResponseEntity<?> checkNicknameDuplicate (@RequestBody UserRequestDTO userDTO) {
        return ResponseEntity.ok().body(userService.checkNicknameDuplicate(userDTO));
    }

    //회원 정보 조회 (전체)
    @GetMapping("/user/searchAllUser")
    public ResponseEntity<?> searchAllUser() {
        return ResponseEntity.ok().body(userService.getAllUser());
    }

    //회원 정보 조회 (개인)
    @GetMapping("/user/searchOneUser/{id}")
    public ResponseEntity<?> searchOneUser (@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.searchOneUser(id));
    }

    //회원 정보 수정
    @PostMapping("/user/editUser")
    public ResponseEntity<?> editUser (@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok().body(userService.editUser(userRequestDTO));
    }

    //회원 정보 삭제
    @PostMapping("/user/deleteUser/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }
}
