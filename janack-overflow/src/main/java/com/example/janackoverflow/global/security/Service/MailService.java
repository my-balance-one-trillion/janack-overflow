package com.example.janackoverflow.global.security.Service;

import com.example.janackoverflow.global.security.DTO.MailDTO;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public boolean sendMail(MailDTO mailDTO) {

        System.out.println(mailDTO);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            mimeMessageHelper.setTo(mailDTO.getTo()); // 메일 수신자
            mimeMessageHelper.setSubject(mailDTO.getSubject()); // 메일 제목
            mimeMessageHelper.setText(mailDTO.getMessage(), true); // 메일 본문 내용, HTML 여부

            javaMailSender.send(mimeMessage);

            System.out.println("전송 성공! message : " + mimeMessage);

            return true;

        } catch (Exception e){

            System.out.println("전송 실패");

            return false;

        }
    }
}
