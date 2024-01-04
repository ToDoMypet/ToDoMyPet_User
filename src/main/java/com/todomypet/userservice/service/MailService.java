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

        String msg = """
                <!DOCTYPE html>
                <html lang="en">
                    <head>
                        <style>
                            body {
                                margin: 0;
                                background-color: #ebebeb;
                            }
                                
                            table.container {
                                width: 100%;
                                height: 100vh;
                                margin: 0;
                                padding: 0;
                                border-collapse: collapse;
                            }
                                
                            td.email-container {
                                vertical-align: middle;
                                text-align: center;
                                position: relative;
                            }
                                
                            .logo {
                                position: absolute;
                                top: 7%;
                                left: 50%;
                                transform: translateX(-50%);
                                max-width: 100%;
                                height: auto;
                                z-index: 1;
                            }
                                
                            table.content-container {
                                background-color: white;
                                height: 600px;
                                width: 600px;
                                border-radius: 10px;
                                position: relative;
                                margin: 0 auto;
                                padding-top: 5%;
                                border-collapse: collapse;
                            }
                                
                            td.code-box {
                                background-color: #f9f9f9;
                                width: 260px;
                                height: 64px;
                                margin: auto;
                                text-align: center;
                                border-radius: 10px;
                                display: flex;
                                justify-content: center;
                                align-items: center;
                                margin-top: 17%;
                                margin-bottom: 10%;
                            }
                                
                            .footer {
                                background-color: #f9f9f9;
                                width: 100%;
                                height: 145px;
                                position: absolute;
                                bottom: 0;
                                border-radius: 0 0 10px 10px;
                            }
                        </style>
                    </head>
                                
                    <body>
                        <table class="container">
                            <tr>
                                <td class="email-container">
                                    <img
                                        src="https://todomypet.s3.ap-northeast-2.amazonaws.com/emaillImage.png"
                                        class="logo"
                                        height="110"
                                        width="130"
                                    />
                                
                                    <table class="content-container">
                                        <tr>
                                            <td>
                                                <h2>이메일 인증 코드 발송</h2>
                                                <br />
                                                <br />
                                                <a>안녕하세요 To Do My Pet 입니다!</a>
                                                <br />
                                                <br />
                                                <a>이메일 주소 인증을 요청하였습니다.</a>
                                                <br />
                                                <a>인증 코드 입력란에 아래 6자리 코드를 입력해 주세요!</a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="code-box">
                                                <h2>
                    """;
        msg += eCode;
        msg += """
                </h2>
                                                   </td>
                                               </tr>
                                               <tr>
                                                   <td class="footer">
                                                       <br />
                                                       <br />
                                                       <a style="color: #a1a1a1">본 메일은 이메일 인증을 위한 발신전용 메일입니다. </a>
                                                       <br />
                                                       <a style="color: #a1a1a1"
                                                           >본 메일과 관련되어 궁금하신 점이나 불편하신 사항은 고객센터에 문의해 주시기
                                                           바랍니다.</a
                                                       >
                                                       <br />
                                                       <br />
                                                       <a style="color: #a1a1a1">ⓒTO DO MY PET</a>
                                                   </td>
                                               </tr>
                                           </table>
                                       </td>
                                   </tr>
                               </table>
                           </body>
                       </html>
                """;
//        msg += "<div style='margin:100px;'>";
//        msg += "<h1> To-do My Pet 이메일 인증 코드 발송 </h1>";
//        msg += "<hr>";
//        msg += "<br>";
//        msg += "안녕하십니까? To-do My Pet입니다.";
//        msg += "<br>";
//        msg += "회원가입 페이지의 인증 코드 입력란에 아래 6자리 코드를 입력해 주세요.";
//        msg += "<br>";
//        msg += "<h3>";
//        msg += eCode;
//        msg += "</h3>";
//        msg += "</div>";

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
