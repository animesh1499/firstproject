package org.example.firstproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.firstproject.modals.UserDetailsModal;
import org.example.firstproject.modals.Users;
import org.example.firstproject.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userDAO.findByUsername(username);
        if(user == null){
            log.error("User not found!");
            throw new UsernameNotFoundException("User not found!");
        }

        return new UserDetailsModal(user);
    }
}
