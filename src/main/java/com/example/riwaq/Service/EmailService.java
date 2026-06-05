package com.example.riwaq.Service;

import lombok.RequiredArgsConstructor;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String to,
                          String subject,
                          String message){

//        SimpleMailMessage mail = new SimpleMailMessage();
//
//        mail.setTo(to);
//        mail.setSubject(subject);
//        mail.setText(message);
//
//        mailSender.send(mail);
        try {
            MimeMessage mail = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(mail, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true);

            mailSender.send(mail);

        } catch (Exception e) {
            throw new RuntimeException("Email not sent");
        }
    }
}
