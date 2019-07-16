package com.website.doa;

import com.website.domains.User;

import java.util.List;

public interface UserDao {

    void addUser(User theUser);

    User findByUsername(String username);

    User findById(Long id);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    List<String> getUserNames();

    List<String> getInvitedUsers(Integer id);

    List<String> getAcceptedUsers(Integer id);
}
