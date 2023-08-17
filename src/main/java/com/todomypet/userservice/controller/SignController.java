package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.*;
import com.todomypet.userservice.service.SignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Sign Controller")
public class SignController {

    private final SignService signService;

    @Operation(summary = "회원가입", description = "회원을 등록합니다.")
    @PostMapping("/sign-up")
    public SuccessResDTO<SignUpResDTO> signUp(@Valid @RequestPart(value = "signUpInfo") SignUpReqDTO signUpInfo,
                                              @RequestPart(value = "profilePic", required = false) MultipartFile multipartFile) {
        SignUpResDTO response = SignUpResDTO.builder().id(signService.signUp(signUpInfo, multipartFile)).build();
        return new SuccessResDTO<>(response);
    }

    @Operation(summary = "이메일 중복 체크",
            description = "입력된 이메일을 사용하는 회원이 이미 존재하면 false, 아니면 true를 return합니다.")
    @GetMapping("/duplication-check")
    public SuccessResDTO<DuplicationCheckResDTO> emailDuplicationCheck(@RequestBody DuplicationCheckReqDTO duplicationCheckReqDTO) {
        DuplicationCheckResDTO response = DuplicationCheckResDTO.builder()
                .response(signService.duplicationCheck(duplicationCheckReqDTO.getCheckedEmail())).build();
        return new SuccessResDTO<>(response);
    }

    @Operation(summary = "확인 메일 발송", description = "랜덤 코드를 생성하여 확인 메일을 발송합니다.")
    @GetMapping("/send-check-email")
    public SuccessResDTO<SendCheckEmailResDTO> sendCheckEmail(@RequestBody SendCheckEmailReqDTO request) throws Exception {
        String code = signService.sendCheckEmail(request.getReceiveEmail());
        SendCheckEmailResDTO response = SendCheckEmailResDTO.builder().checkCode(code).build();
        return new SuccessResDTO<>(response);
    }
}
