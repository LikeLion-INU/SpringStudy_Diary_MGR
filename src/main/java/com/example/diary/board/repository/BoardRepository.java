package com.example.diary.board.repository;

import com.example.diary.board.domain.Board;
import com.example.diary.board.domain.Scope;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByDate(@NonNull LocalDate date);

    List<Board> findAllByScope(Scope scope);

    List<Board> findAllByScopeAndUserId(Scope scope, Long userId);
}
