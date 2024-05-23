package com.example.diary.user.service;

import com.example.diary.user.domain.Friend;
import com.example.diary.user.dto.FriendResponseDTO;
import com.example.diary.user.dto.UserRequestDTO;
import com.example.diary.user.dto.UserResponseDTO;
import com.example.diary.user.domain.Users;
import com.example.diary.user.repository.FriendRepository;
import com.example.diary.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    //회원가입
    public UserResponseDTO save(UserRequestDTO userRequestDTO) {
        Users user = UserRequestDTO.toSaveEntity(userRequestDTO);
        userRepository.save(user);
        return UserResponseDTO.toUserDTO(user);
    }

    //회원조회(전체)
    public List<Users> getAllUser(){return userRepository.findAll();}

    //회원조회(개인)
    public UserResponseDTO searchOneUser(Long id) {
        Optional<Users> userId = userRepository.findById(id);

        if (userId.isPresent()) {
            return UserResponseDTO.toUserOptionalDTO(userId);
        } else return null;
    }

    //회원 정보 수정
    @Transactional
    public UserResponseDTO editUser(UserRequestDTO userRequestDTO) {
        Optional<Users> userEntity = userRepository.findByUserNickname(userRequestDTO.getUserNickname());

        if (userEntity.isPresent()) {
            Users user  = userEntity.get();
            user.editUser(userRequestDTO.getUserNickname(), userRequestDTO.getUserGender(), userRequestDTO.getUserBirth(), userRequestDTO.getUserArea(), userRequestDTO.getUserMbti());

            return UserResponseDTO.toUserDTO(user);
        } else return null;
    }

    // 회원 삭제
    @Transactional
    public UserResponseDTO deleteUser(Long id) {
        Optional<Users> userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            Users user = userEntity.get();
            userRepository.deleteById(user.getId());
            return UserResponseDTO.toUserDTO(user);
        } else return null;
    }

    // 로그인
    public UserResponseDTO login(UserRequestDTO userRequestDTO) {
        Optional<Users> byUserEmail = userRepository.findByUserEmail(userRequestDTO.getUserEmail());

        if (byUserEmail.isPresent()) {
            Users users = byUserEmail.get();

            if (users.getPassword().equals(userRequestDTO.getPassword())) {
                return UserResponseDTO.toUserDTO(users);
            } else return null;
        } else return null;
    }

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
    public List<Friend> searchFriend(String nickname) {
        return friendRepository.findByFollowerAndAccept(nickname, "Y");
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
