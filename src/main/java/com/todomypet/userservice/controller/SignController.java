package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.*;
import com.todomypet.userservice.service.SignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignController {

    private final SignService signService;

    @PostMapping("/users/sign-up")
    public SuccessResDTO<SignUpResDTO> signUp(@Valid @RequestPart(value = "signUpInfo") SignUpReqDTO signUpInfo,
                                              @RequestPart(value = "profilePic", required = false) MultipartFile multipartFile) {
        SignUpResDTO response = SignUpResDTO.builder().id(signService.signUp(signUpInfo, multipartFile)).build();
        return new SuccessResDTO<>(response);
    }

    @GetMapping("/users/duplication-check")
    public SuccessResDTO<DuplicationCheckResDTO> emailDuplicationCheck(@RequestBody DuplicationCheckReqDTO duplicationCheckReqDTO) {
        DuplicationCheckResDTO response = DuplicationCheckResDTO.builder()
                .response(signService.duplicationCheck(duplicationCheckReqDTO.getCheckedEmail())).build();
        return new SuccessResDTO<>(response);
    }

    @GetMapping("/users/send-check-email")
    public SuccessResDTO<SendCheckEmailResDTO> sendCheckEmail(@RequestBody SendCheckEmailReqDTO request) throws Exception {
        String code = signService.sendCheckEmail(request.getReceiveEmail());
        SendCheckEmailResDTO response = SendCheckEmailResDTO.builder().checkCode(code).build();
        return new SuccessResDTO<>(response);
    }
}
