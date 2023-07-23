package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.DuplicationCheckResDTO;
import com.todomypet.userservice.dto.GetUserDetailsDTO;
import com.todomypet.userservice.dto.SignUpReqDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

public interface SignService extends UserDetailsService {
    String signUp(SignUpReqDTO signUpInfo, MultipartFile multipartFile);

    Boolean duplicationCheck(String checkedId);

    String sendCheckEmail(String receiveEmail) throws Exception;

    GetUserDetailsDTO getUserDetailsByEmail(String email);
}
