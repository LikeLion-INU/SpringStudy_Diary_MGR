package com.example.diary.domain.like.repository;

import com.example.diary.domain.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByBoardIdAndUserId(Long board, Long user);
}
