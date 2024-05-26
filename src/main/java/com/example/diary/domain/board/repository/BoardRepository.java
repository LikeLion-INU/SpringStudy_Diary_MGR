package com.example.diary.domain.board.repository;

import com.example.diary.domain.board.domain.Board;
import com.example.diary.domain.board.domain.Scope;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByDateAndUserId(@NonNull LocalDate date, Long userId);

    List<Board> findAllByScope(Scope scope);

    List<Board> findAllByUserId(Long userId);

    List<Board> findAllByScopeAndUserId(Scope scope, Long userId);
}
