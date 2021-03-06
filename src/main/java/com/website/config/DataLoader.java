package com.website.config;

import com.website.domains.Role;
import com.website.domains.User;
import com.website.service.DraftService;
import com.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private UserService userService;
    @Autowired
    private DraftService draftService;

    // add test User
    public void run(ApplicationArguments args) {
        if(!userService.existsByUsername("Brett")) {
            User theUser = new User("Brett", "Brett", "brett@gmail.com", "password123");
            Role theRole = new Role();
            theRole.setRoleName("STANDARD_USER");
            theRole.setDescription("none");
            //            theRole.setTheUser(theUser);
            theUser.setRoles(new ArrayList<>());
            theUser.getRoles().add(theRole);
            userService.addUser(theUser);
        }
        if(!userService.existsByUsername("Kurt")) {
            User theUser = new User("Kurt", "Kurt", "Kurt@gmail.com", "password123");
            Role theRole = new Role();
            theRole.setRoleName("STANDARD_USER");
            theRole.setDescription("none");
            theUser.setRoles(new ArrayList<>());
            theUser.getRoles().add(theRole);
            userService.addUser(theUser);
        }
        if(!userService.existsByUsername("Joe")) {
            User theUser = new User("Joe", "Joe", "Joe@gmail.com", "password123");
            Role theRole = new Role();
            theRole.setRoleName("STANDARD_USER");
            theRole.setDescription("none");
            theUser.setRoles(new ArrayList<>());
            theUser.getRoles().add(theRole);
            userService.addUser(theUser);
        }

        draftService.changeEndedDraftsToEndStatus();
        draftService.checkDraftsThatWereRunning();

        draftService.checkIfDraftsHasExpired();
    }
}
