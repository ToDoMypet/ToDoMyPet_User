package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.*;
import com.todomypet.userservice.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignController {

    private final SignService signService;

    @PostMapping("/users/sign-up")
    public SuccessResDTO<SignUpResDTO> signUp(@RequestPart(value = "signUpInfo") SignUpReqDTO signUpInfo,
                                              @RequestPart(value = "profilePic", required = false) MultipartFile multipartFile) {
        SignUpResDTO response = SignUpResDTO.builder().id(signService.signUp(signUpInfo, multipartFile)).build();
        return new SuccessResDTO<>(response);
    }

    @GetMapping("/users/duplication-check")
    public SuccessResDTO<DuplicationCheckResDTO> EmailDuplicationCheck(@RequestBody DuplicationCheckReqDTO duplicationCheckReqDTO) {
        DuplicationCheckResDTO response = DuplicationCheckResDTO.builder()
                .response(signService.duplicationCheck(duplicationCheckReqDTO.getCheckedEmail())).build();
        return new SuccessResDTO<>(response);
    }
}
