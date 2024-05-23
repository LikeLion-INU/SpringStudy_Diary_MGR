package com.example.diary.user.repository;

import com.example.diary.user.domain.Friend;
import com.example.diary.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
}