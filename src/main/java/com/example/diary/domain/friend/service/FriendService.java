package com.example.diary.domain.friend.service;

import com.example.diary.domain.friend.domain.Friend;
import com.example.diary.domain.friend.dto.FriendResponseDTO;
import com.example.diary.domain.friend.repository.FriendRepository;
import com.example.diary.domain.user.domain.Users;
import com.example.diary.domain.user.dto.UserRequestDTO;
import com.example.diary.domain.user.dto.UserResponseDTO;
import com.example.diary.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final HttpSession httpSession;

    // 친구 요청
    public FriendResponseDTO requestFriend(Long id, String receiver) {
        Optional<Users> user = userRepository.findById(id);

        if (user.isPresent()) {
            //현재 entity에 팔로워 와 받는 사람이 존재한다면, 친구 요청 취소(즉 삭제)
            Optional<Friend> friend = friendRepository.findByFollowerAndReceiver(user.get().getUserNickname(), receiver);
            if (friend.isPresent()) friendRepository.deleteById(friend.get().getId());
            else {
                //존재 하지 않으면 새로운 친구 요청 제작
                String follower = user.get().getUserNickname();

                Friend f = new Friend(follower, receiver, "N");
                friendRepository.save(f);

                return FriendResponseDTO.toFriendDTO(f);
            }
        }
        return null;
    }

    // 내가 요청 건 친구 조회
    public List<FriendResponseDTO> searchRequestFriend(String nickname) {
        List<Friend> friends = friendRepository.findByFollower(nickname);
        return friends.stream()
                .map(FriendResponseDTO::toFriendDTO)
                .collect(Collectors.toList());
    }

    // 요청 받은 친구 조회
    public List<FriendResponseDTO> searchReceiveFriend(String nickname) {
        List<Friend> friends = friendRepository.findByReceiver(nickname);
        return friends.stream()
                .map(FriendResponseDTO::toFriendDTO)
                .collect(Collectors.toList());
    }

    // 친구 요청 승인
    @Transactional
    public FriendResponseDTO acceptFriend(Long friendId) {
        String nickName = (String) httpSession.getAttribute("userName");
        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("친구 요청이 없습니다."));
        if (!Objects.equals(friend.getReceiver(), nickName)){
            throw new RuntimeException("현 회원이 받은 친구 요청이 아닙니다.");
        }
        String accept = "";
        //친구 요청이 존재하는데 N 이면 Y로
        if (Objects.equals(friend.getAccept(), "N")){
            accept = "Y";
        } else {
            //Y 이면 N 으로
            accept = "N";
        }
        friend.toUpdateAccept(accept);

        return FriendResponseDTO.toFriendDTO(friend);

    }

    // 친구 조회
    public List<FriendResponseDTO> searchFriend(Long id) {
        Optional<Users> users = userRepository.findById(id);

        if (users.isPresent()) {
            String follower = users.get().getUserNickname();
            // 현재 사용자의 닉네임으로 친구 조회 (ACCEPT 값이 Y인 것만)
            List<Friend> friends = friendRepository.findByFollowerAndAccept(follower, "Y");
            return friends.stream()
                    .map(FriendResponseDTO::toFriendDTO)
                    .collect(Collectors.toList());
        } else return null;
    }

    // 친구 삭제
    @Transactional
    public String deleteFriend(Long id, String receiver) {
        Optional<Users> res = userRepository.findById(id);

        if (res.isPresent()) {
            String follower = res.get().getUserNickname();
            friendRepository.deleteByFollowerAndReceiver(follower, receiver);

            return "Delete Success";
        } else return null;
    }
}
