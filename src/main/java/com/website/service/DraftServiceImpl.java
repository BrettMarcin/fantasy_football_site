package com.website.service;

import com.google.common.collect.Lists;
import com.website.bean.DraftTimers;
import com.website.doa.DraftDao;
import com.website.doa.UserDao;
import com.website.domains.Draft;
import com.website.domains.GetDrafts;
import com.website.domains.Picks;
import com.website.domains.User;
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
    public boolean checkIfCreatedTwo(int userId) {
        long count = draftDao.getDraftCreatedByUser(userId);
        return count > 1;
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
    public List<Picks> getPlayersRemaining(int draftId) {
        // select * from picks where draft_id_picks = 12 order by round ASC;
        List<Object> list = draftDao.getPicks(draftId);
        List<Picks> picks = new ArrayList<>();
        for (Object pick : list) {
            Object[] ob = (Object[])pick;
            picks.add(new Picks((String)ob[0], (int)ob[4], (int)ob[5]));
        }
        return picks;
    }

}
