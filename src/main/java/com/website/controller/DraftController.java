package com.website.controller;

import com.website.Service.UserService;
import com.website.domains.User;
import com.website.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipal;

@RestController
@RequestMapping("/api")
public class DraftController {

    @Autowired(required=true)
    private UserService userService;

    @RequestMapping(value="/getUser", method = RequestMethod.GET)
    public ResponseEntity<?> index(@CurrentUser UserPrincipal currentUser) {
        return ResponseEntity.ok(currentUser);
    }

    @RequestMapping(value="/createUser", method = RequestMethod.POST)
    public void createUser(@RequestBody User theUser) {
        userService.addUser(theUser);
    }


//
//    @RequestMapping(value="/auth", method = RequestMethod.GET)
//    @PreAuthorize("hasAuthority('STANDARD_USER')")
//    public void auth() {
//
//    }

}
