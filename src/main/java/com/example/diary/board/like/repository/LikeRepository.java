package com.example.diary.board.like.repository;

import com.example.diary.board.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByBoardIdAndUserId(Long board, Long user);
}
