package com.website.Service;

import com.website.domains.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void addUser(User theUser);


}
