package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.dto.MyPageResDTO;
import com.todomypet.userservice.dto.UserInfoResDTO;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import com.todomypet.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public MyPageResDTO getMyPage(String userId) {
        User user = userRepository.getOneUserById(userId).orElseThrow(()
                -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        // todo: mapstruct 적용
        MyPageResDTO myPageResDTO = MyPageResDTO.builder()
                .nickname(user.getNickname())
                .bio(user.getBio())
                .profilePicUrl(user.getProfilePicUrl())
                .personalCode(user.getPersonalCode())
                .Protected(user.getProtected()).build();

        return myPageResDTO;
    }
}
