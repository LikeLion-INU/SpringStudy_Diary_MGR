package com.example.diary.domain.bestie.service;

import com.example.diary.domain.bestie.domain.Bestie;
import com.example.diary.domain.bestie.dto.BestieResponseDTO;
import com.example.diary.domain.bestie.repository.BestieRepository;
import com.example.diary.domain.friend.domain.Friend;
import com.example.diary.domain.friend.repository.FriendRepository;
import com.example.diary.domain.user.domain.Users;
import com.example.diary.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BestieService {
    private final UserRepository userRepository;
    private final BestieRepository bestieRepository;
    private final FriendRepository friendRepository;

    // 친한 친구 설정
    public BestieResponseDTO setBestie(Long id, String bestieNickname) {
        Optional<Users> user = userRepository.findById(id);

        if (user.isPresent()) {
            Users userInfo = user.get();
            Optional<Friend> friend = friendRepository.findByFollowerAndReceiver(user.get().getUserNickname(), bestieNickname);
            if (friend.isPresent()) {

                Bestie bestie = new Bestie(userInfo, bestieNickname);
                bestieRepository.save(bestie);
                log.info("[Set] Bestie with {}", bestie);

                return BestieResponseDTO.toBestieDTO(bestie);
            } else {
                throw new RuntimeException("친구 관계가 아니므로 Bestie를 맺을 수 없습니다.");
            }
        }
        return null;
    }

    // 친한 친구 조회 (세션 ID 값 기준)
    public List<BestieResponseDTO> searchBestie(Long id) {
        Optional<Users> user = userRepository.findById(id);
        if (user.isPresent()) {
            Users userInfo = user.get();
            List<Bestie> bestieList = bestieRepository.findByUsers(userInfo);
            log.info("[Search] find All Bestie");
            return bestieList.stream()
                    .map(m -> BestieResponseDTO.toSearchBestieDTO(m.getId(), m.getBestie(), m.getUsers().getId().toString()))
                    .collect(Collectors.toList());
        } else return null;
    }

    // 친한 친구 삭제
    @Transactional
    public String deleteBestie(Long id, String bestie) {
        bestieRepository.deleteByUsersAndBestie(id, bestie);
        log.info("[Delete] Bestie {}", bestie);
        return "Bestie Delete Success";
    }
}
