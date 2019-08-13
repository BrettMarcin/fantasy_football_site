package com.website.controller;

import com.website.bean.DraftTimers;
import com.website.domains.*;
import com.website.domains.api_specific.ListOfUsers;
import com.website.domains.api_specific.NumberNotifications;
import com.website.domains.api_specific.UserInvitedAndAccepted;
import com.website.service.DraftService;
import com.website.service.UserService;
import com.website.domains.api_specific.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DraftController {

    @Autowired
    private UserService userService;

    @Autowired
    private DraftService draftService;

    @Autowired
    private DraftTimers draftTimers;

//    private SimpMessagingTemplate template;
//
//    @Autowired
//    public DraftController(SimpMessagingTemplate template) {
//        this.template = template;
//    }

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

    @RequestMapping(value="/getUserNames", method = RequestMethod.GET)
    public ResponseEntity<?> getUsers(@AuthenticationPrincipal User userDetails) {
        List<String> s = userService.getUserNames();
        s.remove(userDetails.getUsername());
        return ResponseEntity.ok(new ListOfUsers(s));
    }

    @RequestMapping(value="/getDraftDetails/{draftId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDraftDetails(@PathVariable("draftId") Integer draftId) {
        return ResponseEntity.ok(draftService.getDraft(draftId));
    }

    @RequestMapping(value="/getDrafts", method = RequestMethod.GET)
    public ResponseEntity<?> getDraftDetails(@AuthenticationPrincipal User userDetails) {
        return ResponseEntity.ok(draftService.getDrafts(userDetails.getUsername()));
    }

    @RequestMapping(value="/getUsersInADraft/{draftId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayersInDraft(@PathVariable("draftId") Integer draftId) {
        return ResponseEntity.ok(userService.getUsersInDraft(draftId));
    }

    @RequestMapping(value="/joinDraft/{draftId}", method = RequestMethod.POST)
    public ResponseEntity<?> joinDraft(@AuthenticationPrincipal User userDetails, @PathVariable("draftId") Integer draftId) {
        draftService.joinDraft(userDetails.getUsername(), draftId);
        return new ResponseEntity(new ApiResponse(true, "Joined draft!"), HttpStatus.CREATED);
    }

    @RequestMapping(value="/startDraft/{draftId}", method = RequestMethod.POST)
    public ResponseEntity<?> startDraft(@AuthenticationPrincipal User userDetails, @PathVariable("draftId") Integer draftId) {
        draftService.startDraft(draftId, userDetails.getUsername());
        return new ResponseEntity(new ApiResponse(true, "Joined draft!"), HttpStatus.CREATED);
    }

    @RequestMapping(value="/invitedMoreUsers/{draftId}", method = RequestMethod.POST)
    public ResponseEntity<?> invitedMoreUsers(@AuthenticationPrincipal User userDetails, @PathVariable("draftId") Integer draftId, @RequestBody UserInvitedAndAccepted invitedUsers) {
        draftService.inviteMoreUsers(invitedUsers, draftId);
        return new ResponseEntity(new ApiResponse(true, "Successfully invited users"), HttpStatus.CREATED);
    }

    @RequestMapping(value="/getDraftTimer/{draftId}", method = RequestMethod.POST)
    public ResponseEntity<?> getDraftTimer(@AuthenticationPrincipal User userDetails, @PathVariable("draftId") Integer draftId) {
        draftTimers.getTimer(draftId);
        return new ResponseEntity(new ApiResponse(true, "Got timer"), HttpStatus.OK);
    }

    @RequestMapping(value="/getPicks/{draftId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPicks(@PathVariable("draftId") Integer draftId) {
        return ResponseEntity.ok(draftService.getPicks(draftId));
    }

    @RequestMapping(value="/getPlayersRemaining/{draftId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayers(@PathVariable("draftId") Integer draftId) {
        return ResponseEntity.ok(draftService.getPlayersRemaining(draftId));
    }

    @RequestMapping(value="/resumeDraft/{draftId}", method = RequestMethod.POST)
    public ResponseEntity<?> resumeDraft(@PathVariable("draftId") Integer draftId) {
        draftService.resumeDraft(draftId);
        return new ResponseEntity(new ApiResponse(true, "Successfully resumed draft"), HttpStatus.CREATED);
    }

    @RequestMapping(value="/pickPlayer/{draftId}", method = RequestMethod.POST)
    public ResponseEntity<?> pickPlayer(@PathVariable("draftId") Integer draftId, @RequestBody Picks pick) {
        draftService.draftPlayer(draftId, pick);
        return new ResponseEntity(new ApiResponse(true, "Successfully resumed draft"), HttpStatus.CREATED);
    }

    @RequestMapping(value="/getPickHistory/{draftId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPickHistory(@PathVariable("draftId") Integer draftId) {
        return ResponseEntity.ok(draftService.getPickHistory(draftId));
    }

    @RequestMapping(value="/getPlayersDuringDraft/{draftId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayersDuringDraft(@PathVariable("draftId") Integer draftId) {
        return ResponseEntity.ok(draftService.getPlayersInDraft(draftId));
    }

    @RequestMapping(value="/getPlayersTeamDrafted/{draftId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayersTeamDrafted(@PathVariable("draftId") Integer draftId, @RequestParam String user) {
        return ResponseEntity.ok(draftService.getPlayersTeamDrafted(draftId, user));
    }

    @RequestMapping(value="/getNumberOfNotification", method = RequestMethod.GET)
    public ResponseEntity<?> getNumberOfNotification(@AuthenticationPrincipal User userDetails) {
        return ResponseEntity.ok(new NumberNotifications(userService.getNumberOfNotification(userDetails.getUsername())));
    }

    @RequestMapping(value="/getNotifications", method = RequestMethod.GET)
    public ResponseEntity<?> getNotifications(@AuthenticationPrincipal User userDetails) {
        return ResponseEntity.ok(userService.getNotifications(userDetails.getUsername()));
    }

    @RequestMapping(value="/deleteNotification", method = RequestMethod.POST)
    public ResponseEntity<?> deleteNotification(@AuthenticationPrincipal User userDetails, @RequestBody Notification not) {
        userService.deleteNotification(not, userDetails.getUsername());
        return new ResponseEntity(new ApiResponse(true, "Successfully created draft"), HttpStatus.OK);
    }

    @RequestMapping(value="/deleteDraft/{draftId}", method = RequestMethod.GET)
    public ResponseEntity<?> deleteDraft(@AuthenticationPrincipal User userDetails, @PathVariable("draftId") Integer draftId) {
        draftService.deleteDraft(draftId);
        return new ResponseEntity(new ApiResponse(true, "Successfully deleted draft"), HttpStatus.OK);
    }
}
