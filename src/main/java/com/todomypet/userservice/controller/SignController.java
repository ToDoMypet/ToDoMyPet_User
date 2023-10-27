package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.*;
import com.todomypet.userservice.service.RefreshTokenService;
import com.todomypet.userservice.service.SignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "Sign Controller")
public class SignController {

    private final SignService signService;
    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "회원 가입", description = "회원을 등록합니다.")
    @PostMapping("/sign-up")
    public SuccessResDTO<SignUpResDTO> signUp(@Valid @RequestBody SignUpReqDTO signUpInfo) {
        SignUpResDTO response = SignUpResDTO.builder().id(signService.signUp(signUpInfo)).build();
        return new SuccessResDTO<SignUpResDTO>(response);
    }

    @Operation(summary = "회원 탈퇴", description = "회원을 탈퇴합니다. soft delete 방식을 사용합니다.")
    @DeleteMapping("/delete-account")
    public SuccessResDTO<Void> deleteAccount(@Parameter(hidden = true) @RequestHeader String userId) {
        signService.deleteAccount(userId);
        return new SuccessResDTO<Void>(null);
    }

    @Operation(summary = "이메일 중복 체크",
            description = "입력된 이메일을 사용하는 회원이 이미 존재하면 false, 아니면 true를 return합니다.")
    @GetMapping("/duplication-check/{checkedEmail}")
    public SuccessResDTO<DuplicationCheckResDTO> emailDuplicationCheck(@PathVariable String checkedEmail) {
        DuplicationCheckResDTO response = DuplicationCheckResDTO.builder()
                .response(signService.duplicationCheck(checkedEmail)).build();
        return new SuccessResDTO<>(response);
    }

    @Operation(summary = "확인 메일 발송", description = "랜덤 코드를 생성하여 확인 메일을 발송합니다.")
    @GetMapping("/send-check-email/{receiveEmail}")
    public SuccessResDTO<SendCheckEmailResDTO> sendCheckEmail(@PathVariable String receiveEmail) throws Exception {
        String code = signService.sendCheckEmail(receiveEmail);
        SendCheckEmailResDTO response = SendCheckEmailResDTO.builder().checkCode(code).build();
        return new SuccessResDTO<>(response);
    }

    @Operation(summary = "토큰 재발급", description = "refresh token을 통해 access token, refresh token을 재발급합니다.")
    @GetMapping("/reissue-token")
    public ResponseEntity<SuccessResDTO<Void>> reissueToken(@RequestHeader String refreshToken) {
        TokenResponseDTO tokens = refreshTokenService.reissueToken(refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add("accessToken", tokens.getAccessToken());
        headers.add("refreshToken", tokens.getRefreshToken());
        SuccessResDTO<Void> response = new SuccessResDTO<>(null);

        return ResponseEntity.ok().headers(headers).body(response);
    }
}
