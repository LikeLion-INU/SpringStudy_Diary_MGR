package com.example.diary.repository;

import com.example.diary.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserNickname(String userNickname);
    Optional<UserEntity> findByUserEmail(String userEmail);
}