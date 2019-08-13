package com.website.doa;

import com.website.domains.Draft;
import com.website.domains.Picks;

import java.math.BigInteger;
import java.util.List;

public interface DraftDao {

    long getDraftCreatedByUser(String username);

    long countNumberOfDrafts();

    void createDraft(Draft draft);

    List<BigInteger> getDraftsThatAreMoreThanADay();

    void removeDrafts(List<BigInteger> ids);

    List<Object> getDraft(int draftId);

    void addToInvitedUser(String username, Integer id);

    Integer getCreatedDraftId(String username);

    List<Object> getPublicDrafts();

    public List<Object> getPublicDrafts(String username);

    List<Object> getInvitedDrafts(String username);

    List<Object> getDraftsCreated(String username);

    boolean checkIfUserWasInvited(String username, int draftId);

    void removeInvited(String username, int draftId);

    void addToAcceptedUsers(String username, int draftId);

    boolean checkIfDraftOwner(String username, int draftId);

    boolean checkIfDraftHasStarted(int draftId);

    void inviteUser(String username, int draftId);

    void startDraft(int draftId);

    void insertPick(Picks pick);

    List<Object> getPicks(int draftId);

    List<Object> getPlayersRemaining(int draftId);

    void checkDraftsThatWereRunning();

    void resumeDraft(int draftId);

    void draftPlayer(int playerId, Picks pick);

    Object getMostRecentPicksDraft(int draftId);

    List<Object> getPickHistory(int draftId);

    String getDraftOwner(int draftId);

    List<Object> getPlayersTeamDrafted(int draftId, String username);

    Object[] getLowestRankedPlayer(int draftId);

    long checkPicksRemaining(int draftId);

    void endDraft(int draftId);

    List<Integer> getDraftRunning();
}
