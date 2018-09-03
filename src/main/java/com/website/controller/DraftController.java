package com.website.controller;

import com.website.Service.UserService;
import com.website.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DraftController {

    @Autowired(required=true)
    private UserService userService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value="/createUser", method = RequestMethod.POST)
    public void createUser(@RequestBody User theUser) {
        userService.addUser(theUser);
    }
}
