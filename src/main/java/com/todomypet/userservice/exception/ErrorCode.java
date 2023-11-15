package com.todomypet.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "U001", "파일 업로드에 실패했습니다."),
    USER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "U002", "존재하지 않는 유저입니다."),
    MAIL_SEND_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "U003", "메일 전송에 실패했습니다."),
    DELETED_USER(HttpStatus.BAD_REQUEST, "U004", "탈퇴한 회원입니다."),
    EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "U005", "이미 존재하는 이메일입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "U006", "토큰이 만료되었습니다."),
    ALREADY_ACHIEVE(HttpStatus.BAD_REQUEST, "U008", "이미 달성한 업적입니다."),
    ACHIEVEMENT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "U009", "업적 id가 유효하지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
