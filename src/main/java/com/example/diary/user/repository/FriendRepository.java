package com.example.diary.user.repository;

import com.example.diary.user.domain.Friend;
import com.example.diary.user.domain.Users;
import jakarta.persistence.criteria.From;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByFollower(String nickname);
    List<Friend> findByReceiver(String nickname);
    List<Friend> findByFollowerAndAccept(String nickname, String accept);
    Optional<Friend> findByFollowerAndReceiver(String follower, String receiver);
}