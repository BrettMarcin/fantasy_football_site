package com.website.controller;

import com.website.domains.Token;
import com.website.service.UserService;
import com.website.domains.api_specific.ApiResponse;
import com.website.domains.api_specific.LoginRequest;
import com.website.domains.User;
import com.website.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder customPasswordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    HttpServletRequest request;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            String username = loginRequest.getUsernameOrEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword()));
            User user = userService.findByUsernameOriginal(username);
            if (user == null) {
                throw new BadCredentialsException("User does not exitst");
            }
            String token = jwtTokenProvider.createToken(username, user.getRoles());
            Token token1 = new Token(token);
            return ResponseEntity.ok(token1);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        if(userService.existsByUsername(user.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userService.existsByEmail(user.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        userService.addUser(user);
        return new ResponseEntity(new ApiResponse(true, "User registered successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> signOut(@AuthenticationPrincipal User userDetails, HttpServletRequest req) {
        jwtTokenProvider.deleteToken(jwtTokenProvider.resolveToken(req));
        return new ResponseEntity(new ApiResponse(true, "User logout successfully"), HttpStatus.resolve(200));
    }
}