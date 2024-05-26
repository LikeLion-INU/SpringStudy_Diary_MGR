package com.example.diary.domain.like.service;

import com.example.diary.domain.board.domain.Board;
import com.example.diary.domain.like.domain.Like;
import com.example.diary.domain.like.repository.LikeRepository;
import com.example.diary.domain.board.repository.BoardRepository;
import com.example.diary.domain.user.domain.Users;
import com.example.diary.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    public String setLike(Long id) {
        Long userId = (Long) httpSession.getAttribute("userId");
        Users user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);
        Board board = boardRepository.findById(id)
             .orElseThrow(RuntimeException::new);
        Like like = likeRepository.findByBoardIdAndUserId(board.getId(), userId);
        if (like == null){
            like = Like.builder()
                    .board(board)
                    .user(user)
                    .build();
            likeRepository.save(like);
            board.toUpdateLike(board.getLikeCnt()+1);
            return "Like";
        } else {
            likeRepository.delete(like);
            board.toUpdateLike(board.getLikeCnt()-1);
            return "Unlike";
        }
    }
}
