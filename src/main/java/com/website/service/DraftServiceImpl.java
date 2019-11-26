package com.website.service;

import com.google.common.collect.Lists;
import com.website.bean.DraftTimers;
import com.website.doa.DraftDao;
import com.website.doa.UserDao;
import com.website.domains.*;
import com.website.domains.api_specific.DraftHasStarted;
import com.website.domains.api_specific.UserInvitedAndAccepted;
import com.website.exception.TooManyDraftException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class DraftServiceImpl implements DraftService {

    @Autowired
    private DraftDao draftDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DraftTimers draftTimers;

    private SimpMessagingTemplate template;

    @Autowired
    public DraftServiceImpl(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    @Transactional
    public boolean checkIfCreatedTwo(String username) {
        long count = draftDao.getDraftCreatedByUser(username);
        return count > 2;
    }

    @Override
    @Transactional
    public boolean numberOfDraftIsAtLimit() {
        long count = draftDao.countNumberOfDrafts();
        return count > 9;
    }

    @Override
    @Transactional
    public void createDraft(User theUser, Draft draft) {
        draft.setUserCreated(theUser);
        draftDao.createDraft(draft);
        // get the id of the draft that was just created
        Integer id = draftDao.getCreatedDraftId(theUser.getUsername());

        // Created the many-to-many relationship
        for (String invitedUser : draft.getUsersInvited()) {
            draftDao.addToInvitedUser(invitedUser, id);
        }
    }

    @Override
    @Transactional
    public void checkIfDraftsHasExpired() {
        List<BigInteger> ids = draftDao.getDraftsThatAreMoreThanADay();
        if (ids.size() > 0) {
            draftDao.removeDrafts(ids);
        }
    }

    @Override
    @Transactional
    public Draft getDraft(int draftId) {
        Draft theDraft = new Draft(draftDao.getDraft(draftId));
        return theDraft;
    }

    @Override
    @Transactional
    public GetDrafts getDrafts(String user) {
        GetDrafts theDrafts = new GetDrafts();
        List<Object> listDraft = draftDao.getPublicDrafts(user);
        for (Object draft : listDraft) {
            Draft newDraft = new Draft();
            newDraft.buildDraft(draft);
            theDrafts.setPublicDrafts(newDraft);
        }
        listDraft = draftDao.getInvitedDrafts(user);
        for (Object draft : listDraft) {
            Draft newDraft = new Draft();
            newDraft.buildDraft(draft);
            theDrafts.setInvitedDrafts(newDraft);
        }
        listDraft = draftDao.getDraftsCreated(user);
//        draftDao.get
        for (Object draft : listDraft) {
            Draft newDraft = new Draft();
            newDraft.buildDraft(draft);
            theDrafts.setYourDrafts(newDraft);
        }
        return theDrafts;
    }

    @Override
    @Transactional
    public boolean checkIfUserWasInvited(String username, int draftId) {
        return draftDao.checkIfUserWasInvited(username, draftId);
    }

    @Override
    @Transactional
    public void joinDraft(String username, int draftId) {
        draftDao.removeInvited(username, draftId);
        draftDao.addToAcceptedUsers(username, draftId);
    }

    @Override
    @Transactional
    public boolean checkIfDraftOwner(String username, int draftId) {
        return draftDao.checkIfDraftOwner(username, draftId);
    }

    @Override
    @Transactional
    public DraftHasStarted checkIfDraftHasStarted(int draftId) {
        return new DraftHasStarted(draftDao.checkIfDraftHasStarted(draftId));
    }

    @Override
    @Transactional
    public void inviteMoreUsers(UserInvitedAndAccepted users, Integer draftId) {
        for (String theUser : users.getUsersInvited()) {
            draftDao.addToInvitedUser(theUser, draftId);
        }
    }

    @Override
    @Transactional
    public void startDraft(int draftId, String userName) {
        draftDao.startDraft(draftId);
        List<String> users = userDao.getAcceptedUsers(draftId);
        users.add(userName);
        for (int i = 1; i < 15; i++) {
            if (i % 2 == 1) {
                for (int k = 1; k < users.size()+1; k++) {
                    // TODO: Add pick to database
                    // String username, int draftId, int round, int pickNumber
                    draftDao.insertPick(new Picks(users.get(k-1),draftId, i, k));
                }
            } else {
                for (int k = users.size(); k >= 1; k--) {
                    // TODO: Add pick to database
                    draftDao.insertPick(new Picks(users.get(k-1),draftId, i, k));
                }
            }
        }
        draftTimers.createNewTimer(draftId);
        this.template.convertAndSend("/draft/draftStarted/"+draftId, "Draft has Started!");
    }

    @Override
    @Transactional
    public List<Picks> getPicks(int draftId) {
        // select * from picks where draft_id_picks = 12 order by round ASC;
        List<Object> list = draftDao.getPicks(draftId);
        List<Picks> picks = new ArrayList<>();
        for (Object pick : list) {
            Object[] ob = (Object[])pick;
            picks.add(new Picks((String)ob[0], (int)ob[4], (int)ob[5]));
        }
        return picks;
    }

    @Override
    @Transactional
    public List<Player> getPlayersRemaining(int draftId) {
        List<Object> list = draftDao.getPlayersRemaining(draftId);
        List<Player> players = new ArrayList<>();
        for (Object pick : list) {
            Object[] ob = (Object[])pick;
            Player player = new Player((int)ob[0],(String)ob[1],(String)ob[2],(int)ob[3],(String)ob[4],(float)ob[5],((Byte)ob[6]).intValue(),(int)ob[7],(int)ob[8],(int)ob[9],(int)ob[10],(int)ob[11],(int)ob[12],(String)ob[13],(int)ob[14],(int)ob[15]);
            players.add(player);
        }
        return players;
    }

    @Override
    @Transactional
    public void checkDraftsThatWereRunning() {
        draftDao.checkDraftsThatWereRunning();
    }

    @Override
    @Transactional
    public void resumeDraft(int draftId) {
        draftDao.resumeDraft(draftId);
        draftTimers.createNewTimer(draftId);
        this.template.convertAndSend("/draft/draftStarted/"+draftId, "Draft has Started!");
    }

    @Override
    @Transactional
    public void draftPlayer(int draftId, Picks pick) {
        draftDao.draftPlayer(draftId, pick);
        this.template.convertAndSend("/draft/pickSelected/"+draftId, pick);
        draftTimers.resetTimer(draftId);
        this.checkEndDraft(draftId);
    }

    @Override
    @Transactional
    public Picks getMostRecentPicksDraft(int draftId) {
        Object[] pick = (Object[])draftDao.getMostRecentPicksDraft(draftId);
        return new Picks((String)pick[0], (int)pick[1], (int)pick[4],(int)pick[5]);
        //        this.template.convertAndSend("/draft/draftStarted/"+draftId, "Draft has Started!");
    }
    //
    @Override
    @Transactional
    public List<Picks> getPickHistory(int draftId) {
        List<Object> picks = (List<Object>)draftDao.getPickHistory(draftId);
        List<Picks> completePicks = new ArrayList<>();
        for (Object pick : picks) {
            Object[] pickArray = (Object[])pick;
            // Picks(String username, int draftId, int round, int pickNumber)
            Picks thePick = new Picks((String)pickArray[0], (int)pickArray[1], (int)pickArray[4],(int)pickArray[5]);
            Player player = new Player((int)pickArray[6],(String)pickArray[7],(String)pickArray[8],(int)pickArray[9],(String)pickArray[10],(float)pickArray[11],((Byte)pickArray[12]).intValue(),(int)pickArray[13],(int)pickArray[14],(int)pickArray[15],(int)pickArray[16],(int)pickArray[17],(int)pickArray[18],(String)pickArray[19],(int)pickArray[20],(int)pickArray[21]);
            thePick.setThePlayer(player);
            completePicks.add(thePick);
        }
        return completePicks;
    }

    @Override
    @Transactional
    public String getDraftOwner(int draftId) {
        return draftDao.getDraftOwner(draftId);
    }

    @Override
    @Transactional
    public List<String> getPlayersInDraft(int draftId) {
        List<String> users = userDao.getAcceptedUsers(draftId);
        users.add(this.getDraftOwner(draftId));
        return users;
    }

    @Override
    @Transactional
    public List<Player> getPlayersTeamDrafted(int draftId, String username) {
        List<Object> players = draftDao.getPlayersTeamDrafted(draftId, username);
        List<Player> playersList = new ArrayList<>();
        for (Object pick : players) {
            Object[] ob = (Object[])pick;
            Player player = new Player((int)ob[0],(String)ob[1],(String)ob[2],(int)ob[3],(String)ob[4],(float)ob[5],((Byte)ob[6]).intValue(),(int)ob[7],(int)ob[8],(int)ob[9],(int)ob[10],(int)ob[11],(int)ob[12],(String)ob[13],(int)ob[14],(int)ob[15]);
            playersList.add(player);
        }
//        users.add(this.getDraftOwner(draftId));
//        return users;
        return playersList;
    }

    @Override
    @Transactional
    public void draftHighestRankedPlayer(int draftId) {
        Object[] pickArray = (Object[])draftDao.getMostRecentPicksDraft(draftId);
        Object[] ob = (Object[])draftDao.getLowestRankedPlayer(draftId);
        Picks pick = new Picks((String)pickArray[0], (int)pickArray[1], (int)pickArray[4],(int)pickArray[5]);
        Player player = new Player((int)ob[0],(String)ob[1],(String)ob[2],(int)ob[3],(String)ob[4],(float)ob[5],((Byte)ob[6]).intValue(),(int)ob[7],(int)ob[8],(int)ob[9],(int)ob[10],(int)ob[11],(int)ob[12],(String)ob[13],(int)ob[14],(int)ob[15]);
        pick.setThePlayer(player);
        this.draftPlayer(draftId, pick);
    }

    @Override
    @Transactional
    public void checkEndDraft(int draftId) {
        long pickRemain = draftDao.checkPicksRemaining(draftId);
        if (pickRemain == 0) {
            draftDao.endDraft(draftId);
            this.template.convertAndSend("/draft/hasDraftEnded/" + draftId, true);
            draftTimers.stopTimer(draftId);
        }
    }

    @Override
    @Transactional
    public void changeEndedDraftsToEndStatus() {
        List<Integer> drafts = draftDao.getDraftRunning();
        for (Integer draftId : drafts) {
            this.checkEndDraft(draftId.intValue());
        }
    }

    @Override
    @Transactional
    public void deleteDraft(int draftId) {
        List<BigInteger> list = new ArrayList<>(draftId);
        list.add(BigInteger.valueOf(draftId));
        draftDao.removeDrafts(list);
    }

}
