package com.website.service;

import com.website.domains.*;
import com.website.domains.api_specific.DraftHasStarted;
import com.website.domains.api_specific.UserInvitedAndAccepted;

import java.util.List;

public interface DraftService {

    boolean checkIfCreatedTwo(String username);

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

    List<Player> getPlayersRemaining(int draftId);

    void checkDraftsThatWereRunning();

    void resumeDraft(int draftId);

    void draftPlayer(int draftId, Picks pick);

    Picks getMostRecentPicksDraft(int draftId);

    List<Picks> getPickHistory(int draftId);

    String getDraftOwner(int draftId);

    List<String> getPlayersInDraft(int draftId);

    List<Player> getPlayersTeamDrafted(int draftId, String username);

    void draftHighestRankedPlayer(int draftId);

    void checkEndDraft(int draftId);

    void changeEndedDraftsToEndStatus();

    void deleteDraft(int draftId);
}
