package com.example.diary.domain.bestie.controller;

import com.example.diary.domain.bestie.service.BestieService;
import com.example.diary.domain.friend.service.FriendService;
import com.example.diary.domain.user.dto.UserRequestDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BestieController {
    private final BestieService bestieService;
    private final HttpSession httpSession;

    // 친한 친구 설정
    @PostMapping("/bestie/setBestie")
    public ResponseEntity<?> requestBestie(@RequestBody UserRequestDTO userRequestDTO) {
        // 내 id 값, 친친으로 설정할 상대 닉네임
        Long id = (Long) httpSession.getAttribute("userId");
        String bestie = userRequestDTO.getUserNickname();
        return ResponseEntity.ok().body(bestieService.setBestie(id, bestie));
    }
}