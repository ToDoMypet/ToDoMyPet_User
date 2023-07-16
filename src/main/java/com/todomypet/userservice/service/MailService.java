package com.todomypet.userservice.service;

import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private String eCode;

    private String createCode() {
        StringBuffer code = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) {
            int index = rnd.nextInt(3);

            switch (index) {
                case 0:
                    code.append((char)(rnd.nextInt(26) + 97));
                    break;
                case 1:
                    code.append((char)(rnd.nextInt(26) + 65));
                    break;
                case 2:
                    code.append((rnd.nextInt(10)));
                    break;
            }
        }
        return code.toString();
    }

    public String sendMail(String to) throws Exception {
        eCode = createCode();
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("[To-do My Pet] 이메일 인증 코드 발송");

        String msg = "";
        msg += "<div style='margin:100px;'>";
        msg += "<h1> To-do My Pet 이메일 인증 코드 발송 </h1>";
        msg += "<hr>";
        msg += "<br>";
        msg += "안녕하십니까? To-do My Pet입니다.";
        msg += "<br>";
        msg += "회원가입 페이지의 인증 코드 입력란에 아래 6자리 코드를 입력해 주세요.";
        msg += "<br>";
        msg += "<h3>";
        msg += eCode;
        msg += "</h3>";
        msg += "</div>";

        message.setText(msg, "utf-8", "html");

        message.setFrom(new InternetAddress("todomypet@gmail.com", "To-do My Pet"));

        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.MAIL_SEND_FAIL);
        }

        return eCode;
    }
}
