package com.example.diary.domain.user.repository;

import com.example.diary.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserNickname(String userNickname);
    Optional<Users> findByUserEmail(String userEmail);
}