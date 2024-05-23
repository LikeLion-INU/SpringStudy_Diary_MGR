package com.example.diary.domain.friend.service;

import com.example.diary.domain.friend.domain.Friend;
import com.example.diary.domain.friend.dto.FriendResponseDTO;
import com.example.diary.domain.friend.repository.FriendRepository;
import com.example.diary.domain.user.domain.Users;
import com.example.diary.domain.user.dto.UserRequestDTO;
import com.example.diary.domain.user.dto.UserResponseDTO;
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
public class FriendService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    // 친구 요청
    public FriendResponseDTO requestFriend(Long id, String receiver) {
        Optional<Users> user = userRepository.findById(id);

        if (user.isPresent()) {
            String follower = user.get().getUserNickname();

            Friend friend = new Friend(follower, receiver, "N");
            friendRepository.save(friend);

            return FriendResponseDTO.toFriendDTO(friend);
        } else return null;
    }

    // 요청 건 친구 조회
    public List<Friend> searchRequestFriend(String nickname) {
        return friendRepository.findByFollower(nickname);
    }

    // 요청 받은 친구 조회
    public List<Friend> searchReceiveFriend(String nickname) {
        return friendRepository.findByReceiver(nickname);
    }

    // 친구 요청 승인
    @Transactional
    public FriendResponseDTO acceptFriend(Long id, String receiver) {
        Optional<Users> users = userRepository.findById(id);

        if (users.isPresent()) {
            String follower = users.get().getUserNickname();
            Optional<Friend> res = friendRepository.findByFollowerAndReceiver(follower, receiver);

            if (res.isPresent()) {
                String accept = "Y";
                Friend friend = res.get();
                friend.acceptFriend(follower, receiver, accept);

                return FriendResponseDTO.toAcceptFriendDTO(friend);
            } else return null;
        } else return null;
    }

    // 친구 조회
    public List<Friend> searchFriend(Long id) {
        Optional<Users> users = userRepository.findById(id);

        if (users.isPresent()) {
            String follower = users.get().getUserNickname();
            return friendRepository.findByFollowerAndAccept(follower, "Y");
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
