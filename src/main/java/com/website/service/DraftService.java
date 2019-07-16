package com.website.service;

import com.website.domains.Draft;
import com.website.domains.GetDrafts;
import com.website.domains.Picks;
import com.website.domains.User;
import com.website.domains.api_specific.DraftHasStarted;
import com.website.domains.api_specific.UserInvitedAndAccepted;

import java.util.List;

public interface DraftService {

    boolean checkIfCreatedTwo(int userId);

    boolean numberOfDraftIsAtLimit();

    void createDraft(User theUser, Draft draft);

    void checkIfDraftsHasExpired();

    Draft getDraft(int draftId);

    GetDrafts getDrafts(String user);

    boolean checkIfUserWasInvited(String username, int draftId);

    void joinDraft(String username, int draftId);

    boolean checkIfDraftOwner(String username, int draftId);

    DraftHasStarted checkIfDraftHasStarted(int draftId);

    void inviteMoreUsers(UserInvitedAndAccepted users, Integer draftId);

    void startDraft(int draftId, String userName);

    List<Picks> getPicks(int draftId);
}
