package com.spring.gamebackend.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MailService {
    private JavaMailSender mailSender;
    private TempUserRepository tempUserRepository;
    @Autowired
    public MailService(JavaMailSender mailSender,TempUserRepository tempUserRepository){
        this.mailSender=mailSender;
        this.tempUserRepository=tempUserRepository;
    }

    public void sendVerificationMail(TemporaryUser tempUser){
        int tempPassword=temporaryPassword(0,1000);
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("aminecnar@gmail.com");
        message.setTo(tempUser.getUserName());
        message.setSubject("Verification Password.");
        message.setText(tempPassword+ " is your password.Send it to us please.");

        mailSender.send(message);
        tempUser.setSendPasswordTime(LocalDateTime.now());
        tempUser.setTempPassword(tempPassword);
        tempUserRepository.save(tempUser);

    }

    private int temporaryPassword(int min,int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);

    }

}
