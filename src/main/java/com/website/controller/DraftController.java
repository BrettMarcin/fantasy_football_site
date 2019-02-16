package com.website.controller;

import com.website.domains.Draft;
import com.website.service.DraftService;
import com.website.service.UserService;
import com.website.domains.api_specific.ApiResponse;
import com.website.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class DraftController {

    @Autowired(required=true)
    private UserService userService;

    @Autowired
    private DraftService draftService;

    @RequestMapping(value="/getUser", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@AuthenticationPrincipal User userDetails) {
        return ResponseEntity.ok(userDetails);
    }

    // interceptor is used for this specific api
    @RequestMapping(value="/createDraft", method = RequestMethod.POST)
    public ResponseEntity<?> createDraft(@AuthenticationPrincipal User userDetails, @Valid @RequestBody Draft draft) {
        draftService.createDraft(userDetails, draft);
        return new ResponseEntity(new ApiResponse(true, "Successfully created draft"), HttpStatus.CREATED);
    }


//
//    @RequestMapping(value="/auth", method = RequestMethod.GET)
//    @PreAuthorize("hasAuthority('STANDARD_USER')")
//    public void auth() {
//
//    }

}
