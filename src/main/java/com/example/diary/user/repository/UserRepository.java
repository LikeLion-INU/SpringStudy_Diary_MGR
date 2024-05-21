package com.example.diary.user.repository;

<<<<<<< HEAD
import com.example.diary.user.domain.Users;
=======
import com.example.diary.user.entity.UserEntity;
>>>>>>> ee555ed5055bb2c4aaef0da1b5e7611521ef330a
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
<<<<<<< HEAD
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserNickname(String userNickname);
    Optional<Users> findByUserEmail(String userEmail);
=======
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserNickname(String userNickname);
    Optional<UserEntity> findByUserEmail(String userEmail);
>>>>>>> ee555ed5055bb2c4aaef0da1b5e7611521ef330a
}