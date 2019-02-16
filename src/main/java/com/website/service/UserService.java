package com.website.service;

import com.website.domains.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    void addUser(User theUser);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    User findByUsernameOriginal(String username);

    UserDetails findByUsername(String username);

    UserDetails loadUserByUsername(String username);
}
