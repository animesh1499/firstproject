package org.example.firstproject.services.impl;

import org.example.firstproject.modals.Users;
import org.example.firstproject.repositories.UserDAO;
import org.example.firstproject.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private JWTServicesImpl jwtServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    @Override
    public Users register(Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setAuthProvider(Users.AuthProvider.LOCAL);
        return userDAO.save(user);
    }

    @Override
    public String verify(Users user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtServices.generateToken(user.getUsername());
        }
        return "Failure";
    }
}
