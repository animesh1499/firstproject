package org.example.firstproject.projectcontrollers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.firstproject.modals.Users;
import org.example.firstproject.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/user/login")
    public String login(@RequestBody Users user){
        return userServices.verify(user);
    }

    @PostMapping(value = "/user/logout")
    public String logout(@RequestHeader("Authorization") String authHeader, HttpServletRequest request){
        System.out.println("Inside logout!");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            System.out.println("This is token in logout " + token);
            // Set expiry time equal to token expiration (30 min in this case)
            redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "true", 30, TimeUnit.MINUTES);
            HttpSession session = request.getSession(false); // Get existing session
            if (session != null) {
                System.out.println("Session : " + session.getId());
                session.invalidate(); // Invalidate session
            }

            return "Logged out successfully!";
        }
        return "Invalid token!";
    }

}
