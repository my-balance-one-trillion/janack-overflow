package com.example.janackoverflow.global.security.Service;

import com.example.janackoverflow.global.security.DTO.MailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;

    public boolean sendSimpleMessage(MailDTO data) {

        System.out.println(data);

        SimpleMailMessage message = new SimpleMailMessage();

        try {

            message.setFrom(data.getEmail());
            message.setTo("보낼 메일 즉 나의 메일!");
            message.setSubject(data.getName());
            message.setText(data.getMessage() + data.getEmail());

            emailSender.send(message);

            System.out.println("전송 성공! message : " + message);

            return true;

        } catch (Exception e){
            System.out.println("전송 실패");
            return false;
        }
    }
}
