package com.example.diary.board.like.service;

import com.example.diary.board.domain.Board;
import com.example.diary.board.dto.BoardRequestDto;
import com.example.diary.board.like.domain.Like;
import com.example.diary.board.like.repository.LikeRepository;
import com.example.diary.board.repository.BoardRepository;
import com.example.diary.user.domain.Users;
import com.example.diary.user.repository.UserRepository;
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
//        Long userId = (Long) httpSession.getAttribute("user");
        Long userId = (long)1;
        //todo 유저 세션 값 가져오기
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
