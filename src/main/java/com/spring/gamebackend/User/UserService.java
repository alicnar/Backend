package com.spring.gamebackend.User;

import com.spring.gamebackend.Exceptions.*;
import com.spring.gamebackend.Security.SecurityConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class UserService {
    private UserRepository userRepository;
    private TempUserRepository tempUserRepository;
    private MailService mailService;

    private PasswordEncoder passwordEncoder;



    public UserService(UserRepository userRepository, TempUserRepository tempUserRepository,MailService mailService,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.tempUserRepository=tempUserRepository;
        this.mailService=mailService;
        this.passwordEncoder=passwordEncoder;

    }

    /*----------------------------------------------------------------------------------------------*/

    public String saveNewUser(TemporaryUser tempUser){
        User savedUser=userRepository.findById(tempUser.getUserName()).orElse(null);
        if(savedUser==null){
            String activePassword=tempUser.getPassword();
            String cryptoPassword=passwordEncoder.encode(activePassword);
            tempUser.setPassword(cryptoPassword);
            tempUserRepository.save(tempUser);
            mailService.sendVerificationMail(tempUser);
            return "Registration received. Please verify the code sent to your email.";
        }
        throw new RegisteredMailException("Mail address is already registered in the app.");
    }

    /*----------------------------------------------------------------------------------------------*/

    public String verifyAndSaveRealUser(String mail,int code){
        LocalDateTime nowTime = LocalDateTime.now();
        TemporaryUser tempUser=tempUserRepository.findById(mail).orElse(null);
        if(tempUser!=null){
            LocalDateTime sendTime=tempUser.getSendPasswordTime();
            long difference= ChronoUnit.MINUTES.between(sendTime, nowTime);
            if(difference>3){
                throw new ExpiredCodeException("Your password has expired. Please try registering again.");
            }
            if(code==tempUser.getTempPassword()){
                User user=new User();
                user.setUserName(tempUser.getUserName());
                user.setPassword(tempUser.getPassword());
                user.setLogin(true);
                userRepository.save(user);

                tempUserRepository.delete(tempUser);
                return "Email verified, registration confirmed.";
            }else{
                throw new InvalidCodeException("You entered the wrong code.");
            }
        }
        throw new WrongMailException("You entered the wrong mail, registration was not approved. You can try with a new email.");
    }

    /*----------------------------------------------------------------------------------------------*/

    public String logInToApp(TemporaryUser tempUser){
        User searchUser=userRepository.findById(tempUser.getUserName()).orElse(null);
        if(searchUser!=null){
            String tempPassword=tempUser.getPassword();
            String searchPassword=searchUser.getPassword();
            if(passwordEncoder.matches(tempPassword,searchPassword)){
                searchUser.setLogin(true);
                userRepository.save(searchUser);
                return "You log in to app.";
            }else{
                throw new WrongPasswordException("You entered the wrong password. Please enter the true password.");
            }
        }
        throw new WrongMailException("There is no such a user name.Please enter true one or sign up.");

    }

    /*----------------------------------------------------------------------------------------------*/

    public String logOutFromApp(String mail){
        User searchUser=userRepository.findById(mail).orElse(null);
        if(searchUser!=null){
            searchUser.setLogin(false);
            userRepository.save(searchUser);
            return "You log out from app.See you later:)";
        }
        throw new WrongMailException("You entered the wrong mail. Please enter the true mail.");
    }

    /*----------------------------------------------------------------------------------------------*/

    public String deleteUser(String mail){
        User deletedUser=userRepository.findById(mail).orElse(null);
        if(deletedUser!=null){
            userRepository.delete(deletedUser);
            return mail+"is deleted from database.";
        }
        throw new WrongMailException("You entered the wrong mail. Please enter the true mail.");
    }


}
