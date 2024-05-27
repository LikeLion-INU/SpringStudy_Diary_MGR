package com.example.diary.domain.friend.controller;

import com.example.diary.domain.friend.service.FriendService;
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
public class FriendController {
    private final FriendService friendService;
    private final HttpSession httpSession;

    // 친구 요청 (친구 요청 건 사람 follower & 받은 사람 receiver)
    @PostMapping("/friend/requestFriend")
    public ResponseEntity<?> requestFriend(@RequestBody UserRequestDTO userRequestDTO) {
        // id -> follower, nickname -> receiver
        Long id = (Long) httpSession.getAttribute("userId");
        String nickname = userRequestDTO.getUserNickname();
        return ResponseEntity.ok().body(friendService.requestFriend(id, nickname));
    }

    // 요청 건 내역 조회
    @GetMapping("/friend/searchRequestFriend")
    public ResponseEntity<?> searchRequestFriend() {
        String nickname = (String) httpSession.getAttribute("userName");
        // 닉네임으로 조회 (요청 건 내역만)
        return ResponseEntity.ok().body(friendService.searchRequestFriend(nickname));
    }

    // 요청 받은 내역 조회
    @GetMapping("/friend/searchReceiveFriend")
    public ResponseEntity<?> searchReceiveFriend() {
        String nickname = (String) httpSession.getAttribute("userName");
        // 닉네임으로 조회 (요청 받은 내역만)
        return ResponseEntity.ok().body(friendService.searchReceiveFriend(nickname));
    }

    // 친구 요청 승인
    @PostMapping("/friend/acceptFriend/{friend_id}")
    public ResponseEntity<?> acceptFriend(@PathVariable("friend_id") Long friendId) {
        // id -> follower, nickname -> receiver
        return ResponseEntity.ok().body(friendService.acceptFriend(friendId));
    }

    // 친구 조회
    @PostMapping("/friend/searchFriend")
    public ResponseEntity<?> searchFriend() {
        Long id = (Long) httpSession.getAttribute("userId");
        return ResponseEntity.ok().body(friendService.searchFriend(id));
    }

    // 친구 삭제
    @PostMapping("/friend/deleteFriend")
    public ResponseEntity<?> deleteFriend(@RequestBody UserRequestDTO userRequestDTO) {
        Long id = (Long) httpSession.getAttribute("userId");
        String receiver = userRequestDTO.getUserNickname();
        return ResponseEntity.ok().body(friendService.deleteFriend(id, receiver));
    }
}