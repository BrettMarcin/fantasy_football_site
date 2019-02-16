package com.website.service;

import com.website.domains.Draft;
import com.website.domains.User;

public interface DraftService {

    boolean checkIfCreatedTwo(int userId);

    boolean numberOfDraftIsAtLimit();

    void createDraft(User theUser, Draft draft);

    void checkIfDraftsHasExpired();
}
