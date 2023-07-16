package com.todomypet.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    FILE_UPLOAD_FAIL(HttpStatus.BAD_REQUEST, "U001", "파일 업로드에 실패했습니다."),
    USER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "U002", "존재하지 않는 유저입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
