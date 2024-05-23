package com.example.diary.domain.like.controller;

import com.example.diary.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/{board_id}")
    @Transactional
    public ResponseEntity<?> boardLike(@PathVariable("board_id") Long id){
        return ResponseEntity.ok()
                .body(likeService.setLike(id));
    }
}
