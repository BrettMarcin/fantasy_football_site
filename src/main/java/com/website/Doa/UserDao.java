package com.website.Doa;

import com.website.domains.User;

public interface UserDao {

    void addUser(User theUser);

    User findByUsername(String username);

    User findById(Long id);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
