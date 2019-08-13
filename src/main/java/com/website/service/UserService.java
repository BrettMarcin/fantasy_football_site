package com.website.service;

import com.website.domains.Notification;
import com.website.domains.User;
import com.website.domains.api_specific.UserInvitedAndAccepted;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    void addUser(User theUser);

    List<String> getUserNames();

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    User findByUsernameOriginal(String username);

    UserDetails findByUsername(String username);

    UserDetails loadUserByUsername(String username);

    UserInvitedAndAccepted getUsersInDraft(Integer id);

    long getNumberOfNotification(String username);

    List<Notification> getNotifications(String username);

    void deleteNotification(Notification not, String usernam);
}
