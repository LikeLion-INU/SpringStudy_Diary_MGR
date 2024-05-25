package com.example.diary.domain.bestie.service;

import com.example.diary.domain.bestie.domain.Bestie;
import com.example.diary.domain.bestie.dto.BestieRequestDTO;
import com.example.diary.domain.bestie.dto.BestieResponseDTO;
import com.example.diary.domain.bestie.repository.BestieRepository;
import com.example.diary.domain.friend.domain.Friend;
import com.example.diary.domain.friend.dto.FriendResponseDTO;
import com.example.diary.domain.friend.repository.FriendRepository;
import com.example.diary.domain.user.domain.Users;
import com.example.diary.domain.user.dto.UserRequestDTO;
import com.example.diary.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BestieService {
    private final UserRepository userRepository;
    private final BestieRepository bestieRepository;

    // 친한 친구 설정
    public BestieResponseDTO setBestie(Long id, String bestieNickname) {
        Optional<Users> user = userRepository.findById(id);

        if (user.isPresent()) {
            Users nickname = user.get();

            Bestie bestie = new Bestie(nickname, bestieNickname);
            bestieRepository.save(bestie);

            return BestieResponseDTO.toBestieDTO(bestie);
        } else return null;
    }
}
