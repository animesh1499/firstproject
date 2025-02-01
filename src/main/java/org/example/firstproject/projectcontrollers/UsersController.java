package org.example.firstproject.projectcontrollers;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.example.firstproject.modals.Users;
import org.example.firstproject.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UsersController {

    @Autowired
    private UserServices userServices;

    @PostMapping("/register")
    public Users register(@RequestBody Users user){
        return userServices.register(user);
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody Users user){
        return userServices.verify(user);
    }

}
