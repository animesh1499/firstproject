package org.example.firstproject.projectcontrollers;

import org.example.firstproject.modals.Users;
import org.example.firstproject.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @Autowired
    private UserServices userServices;

    @PostMapping("/register")
    public Users register(@RequestBody Users user){
        return userServices.register(user);
    }
}
