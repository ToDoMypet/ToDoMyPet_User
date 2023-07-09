package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.dto.SignUpReqDTO;
import com.todomypet.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {

    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public String signUp(SignUpReqDTO signUpInfo, MultipartFile multipartFile) {
        String imageUrl = s3Uploader.upload(multipartFile);
        User user = User.builder().email(signUpInfo.getEmail())
                .password(passwordEncoder.encode(signUpInfo.getPassword()))
                .profilePicUrl(imageUrl)
                .nickname(signUpInfo.getNickname())
                .bio(signUpInfo.getBio())
                .createdAt(LocalDateTime.parse(LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))))
                .deleted(Boolean.FALSE)
                .Protected(Boolean.FALSE)
                .build();

        String id = userRepository.save(user).getId();
        return id;
    }
}
