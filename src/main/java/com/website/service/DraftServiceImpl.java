package com.website.service;

import com.website.doa.DraftDao;
import com.website.domain.Draft;
import com.website.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Service
public class DraftServiceImpl implements DraftService {

    @Autowired
    private DraftDao draftDao;

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
    }

    @Override
    @Transactional
    public void checkIfDraftsHasExpired() {
        List<BigInteger> ids = draftDao.getDraftsThatAreMoreThanADay();
        if (ids.size() > 0) {
            draftDao.removeDrafts(ids);
        }
    }

}
