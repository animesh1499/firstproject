package org.example.firstproject.services.impl;

import org.example.firstproject.modals.Users;
import org.example.firstproject.repositories.UserDAO;
import org.example.firstproject.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        //user.setAuthProvider(Users.AuthProvider.LOCAL);
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

    /*@Transactional
    public void processOAuthPostLogin(OAuth2User auth2User){
        String email = auth2User.getAttribute("email");
        Users user = userDAO.findByEmail(email);

        if(user == null){
            Users newUser = new Users();
            newUser.setEmail(email);
            newUser.setUsername(auth2User.getAttribute("name"));
            newUser.setAuthProvider(Users.AuthProvider.GOOGLE);
            userDAO.save(newUser);
        }
    }*/
}
