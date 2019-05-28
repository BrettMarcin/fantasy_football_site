package com.website.service;

import com.website.doa.UserRepository;
import com.website.domain.User;
import com.website.exception.EmailInvalidException;
import com.website.exception.UsernameInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    PasswordEncoder customPasswordEncoder;

    @Override
    @Transactional
    public void addUser(User theUser) {
        if(existsByUsername(theUser.getUsername())) {
            throw new UsernameInvalidException();
        }

        if(existsByEmail(theUser.getEmail())) {
            throw new EmailInvalidException();
        }
        theUser.setPassword(customPasswordEncoder.encode(theUser.getPassword()));
        userRepo.save(theUser);
    }

    @Override
    @Transactional
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email) >= 1;
    }

    @Override
    @Transactional
    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username) >= 1;
    }

    @Override
    @Transactional
    public User findByUsernameOriginal(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        UserDetails user = userRepo.findByUsername(username);
        return user;
    }
}
