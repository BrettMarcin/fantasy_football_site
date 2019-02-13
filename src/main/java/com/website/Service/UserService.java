package com.website.Service;

import com.website.domains.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

public interface UserService {

    void addUser(User theUser);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
