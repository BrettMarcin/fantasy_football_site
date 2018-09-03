package com.website.Doa;

import com.website.domains.User;

public interface UserDao {

    void addUser(User theUser);

    User findByUsername(String username);
}
