package com.example.diary.domain.bestie.repository;

import com.example.diary.domain.bestie.domain.Bestie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BestieRepository extends JpaRepository<Bestie, Long> {
}