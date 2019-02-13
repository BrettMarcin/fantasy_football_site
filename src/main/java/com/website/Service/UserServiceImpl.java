package com.website.Service;

import com.website.Doa.UserDao;
import com.website.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

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
}
