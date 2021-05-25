package com.user.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String to, String subject, String message){
        SimpleMailMessage registrationEmail=new SimpleMailMessage();
        registrationEmail.setTo(to);
        registrationEmail.setSubject(subject);
        registrationEmail.setText(message);

        javaMailSender.send(registrationEmail);
    }
}
