package com.example.diary.domain.bestie.repository;

import com.example.diary.domain.bestie.domain.Bestie;
import com.example.diary.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BestieRepository extends JpaRepository<Bestie, Long> {
    List<Bestie> findByUsers(Users users);
    @Modifying
    @Query("DELETE FROM Bestie b WHERE b.users.id= :id AND b.bestie = :bestie")
    void deleteByUsersAndBestie(@Param("id") Long id, @Param("bestie") String bestie);
    void deleteByBestie(String bestie);
    void deleteByUsers(Users users);

    @Query("SELECT b.users.id FROM Bestie b WHERE b.bestie = :userNickname")
    List<Long> findUserIdByBestie(@Param("userNickname") String userNickname);
}