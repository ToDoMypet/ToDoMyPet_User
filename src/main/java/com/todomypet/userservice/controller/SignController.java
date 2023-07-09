package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.SignUpReqDTO;
import com.todomypet.userservice.dto.SignUpResDTO;
import com.todomypet.userservice.dto.SuccessResDTO;
import com.todomypet.userservice.service.SignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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
}
