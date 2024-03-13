package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.DuplicationCheckResDTO;
import com.todomypet.userservice.dto.GetUserDetailsDTO;
import com.todomypet.userservice.dto.SignUpReqDTO;
import com.todomypet.userservice.dto.TokenResponseDTO;
import com.todomypet.userservice.dto.user.ChangePasswordReqDTO;
import com.todomypet.userservice.dto.user.LogOutReqDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

public interface SignService extends UserDetailsService {
    String signUp(SignUpReqDTO signUpInfo);

    String addAdminInfo(SignUpReqDTO signUpInfo);

    DuplicationCheckResDTO duplicationCheck(String checkedId);

    String sendCheckEmail(String receiveEmail) throws Exception;

    GetUserDetailsDTO getUserDetailsByEmail(String email);

    void deleteAccount(String userId);

    boolean checkPassword(String userId, String password);

    String changePasswordByEmail(ChangePasswordReqDTO req);

    void logout(String userId, LogOutReqDTO req);
}
