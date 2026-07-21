package com.spring.gamebackend.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/rest/api/user")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    /*----------------------------------------------------------------------------------------------*/

    @PostMapping(path = "/save")
    public String saveNewUser(@RequestBody TemporaryUser tempUser){
        return userService.saveNewUser(tempUser);
    }

    /*----------------------------------------------------------------------------------------------*/

    @GetMapping(path="/verificationCode/{mail}/{code}")
    public String verifyAndSaveRealUser(@PathVariable(name="mail") String mail,@PathVariable(name="code") int code){
        return userService.verifyAndSaveRealUser(mail,code);
    }

    /*----------------------------------------------------------------------------------------------*/

    @PostMapping(path="/login")
    public String logInToApp(@RequestBody TemporaryUser tempUser){
        return userService.logInToApp(tempUser);
    }

    /*----------------------------------------------------------------------------------------------*/

    @PostMapping(path="/logout")
    public String logOutFromApp(@RequestBody Map<String, String> payload){
        String mail = payload.get("mail");
        return userService.logOutFromApp(mail);
    }

    /*----------------------------------------------------------------------------------------------*/

    @DeleteMapping(path = "/delete/{mail}")
    public String deleteUser(@PathVariable(name="mail") String mail){
        return userService.deleteUser(mail);
    }
}
