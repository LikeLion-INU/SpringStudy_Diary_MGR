package com.example.diary.domain.bestie.repository;

import com.example.diary.domain.bestie.domain.Bestie;
import com.example.diary.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BestieRepository extends JpaRepository<Bestie, Long> {
    List<Bestie> findByUsers(Users users);
}