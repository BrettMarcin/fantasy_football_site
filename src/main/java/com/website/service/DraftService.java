package com.website.service;

import com.website.domain.Draft;
import com.website.domain.User;

public interface DraftService {

    boolean checkIfCreatedTwo(int userId);

    boolean numberOfDraftIsAtLimit();

    void createDraft(User theUser, Draft draft);

    void checkIfDraftsHasExpired();
}
