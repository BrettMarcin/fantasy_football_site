package com.website.service;

import com.website.doa.UserDao;
import com.website.domains.User;
import com.website.domains.api_specific.UserInvitedAndAccepted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    PasswordEncoder customPasswordEncoder;

    @Override
    @Transactional
    public void addUser(User theUser) {
        theUser.setPassword(customPasswordEncoder.encode(theUser.getPassword()));
        userDao.addUser(theUser);
    }

    @Override
    @Transactional
    public List<String> getUserNames() {
        return userDao.getUserNames();
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

    @Override
    @Transactional
    public UserInvitedAndAccepted getUsersInDraft(Integer id) {
        UserInvitedAndAccepted userInvitedAndAccepted = new UserInvitedAndAccepted();
        userInvitedAndAccepted.setUsersAccepted(userDao.getAcceptedUsers(id));
        userInvitedAndAccepted.setUsersInvited(userDao.getInvitedUsers(id));
        return userInvitedAndAccepted;
    }
}
