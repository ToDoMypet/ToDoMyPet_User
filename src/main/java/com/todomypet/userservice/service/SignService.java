package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.DuplicationCheckResDTO;
import com.todomypet.userservice.dto.SignUpReqDTO;
import org.springframework.web.multipart.MultipartFile;

public interface SignService {
    String signUp(SignUpReqDTO signUpInfo, MultipartFile multipartFile);

    Boolean duplicationCheck(String checkedId);
}
