package com.website.doa;

import com.website.domains.Draft;

import java.math.BigInteger;
import java.util.List;

public interface DraftDao {

    long getDraftCreatedByUser(int userId);

    long countNumberOfDrafts();

    void createDraft(Draft draft);

    List<BigInteger> getDraftsThatAreMoreThanADay();

    void removeDrafts(List<BigInteger> ids);
}
