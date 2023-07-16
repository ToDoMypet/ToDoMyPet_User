package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.dto.SignUpReqDTO;
import com.todomypet.userservice.repository.UserRepository;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {

    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Override
    public String signUp(SignUpReqDTO signUpInfo, MultipartFile multipartFile) {
        StringBuffer personalCode = new StringBuffer();
        Random rnd = new Random();

        while (true) {
            for (int i = 0; i < 2; i++) {
                personalCode.append((char)(rnd.nextInt(26) + 65));
            }
            for (int i = 0; i < 9; i++) {
                personalCode.append(rnd.nextInt(10));
            }

            if (userRepository.getUserCountByPersonalCode(personalCode.toString()) <= 0) {
                break;
            }
        }

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
                .personalCode(personalCode.toString())
                .achCount(0)
                .attendCount(0)
                .petCount(0)
                .petEvolveCount(0)
                .petCompleteCount(0)
                .attendContinueCount(0)
                .friendCount(0)
                .build();

        String id = userRepository.save(user).getId();
        return id;
    }

    @Override
    public Boolean duplicationCheck(String checkedEmail) {
        if (userRepository.getUserCountByEmail(checkedEmail) <= 0) {
            return Boolean.TRUE;
        };
        return Boolean.FALSE;
    }

    @Override
    public String sendCheckEmail(String receiveEmail) throws Exception {
        return mailService.sendMail(receiveEmail);
    }
}
