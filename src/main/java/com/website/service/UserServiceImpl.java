package com.website.service;

import com.website.doa.UserDao;
import com.website.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public void addUser(User theUser) {
        userDao.addUser(theUser);
    }

    @Override
    @Transactional
    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    @Transactional
    public boolean existsByUsername(String username) {
        return userDao.existsByUsername(username);
    }

    @Override
    @Transactional
    public User findByUsernameOriginal(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        UserDetails user = userDao.findByUsername(username);
        return user;
    }
}
