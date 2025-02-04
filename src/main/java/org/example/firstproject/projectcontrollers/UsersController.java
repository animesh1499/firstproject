package org.example.firstproject.projectcontrollers;

import lombok.extern.slf4j.Slf4j;
import org.example.firstproject.modals.Users;
import org.example.firstproject.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class UsersController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String BLACKLIST_PREFIX = "blacklisted:";

    @PostMapping("/register")
    public Users register(@RequestBody Users user){
        return userServices.register(user);
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody Users user){
        return userServices.verify(user);
    }

    @PostMapping(value = "/user/logout")
    public String logout(@RequestHeader("Authorization") String authHeader){
        System.out.println("Inside logout!");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            System.out.println("This is token in logout " + token);
            // Set expiry time equal to token expiration (30 min in this case)
            redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "true", 30, TimeUnit.MINUTES);

            return "Logged out successfully!";
        }
        return "Invalid token!";
    }

}
