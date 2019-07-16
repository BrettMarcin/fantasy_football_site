package com.website.domains.api_specific;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DraftHasStarted {
    @JsonProperty
    boolean draftHasStarted;

    DraftHasStarted () {

    }

    public DraftHasStarted(boolean draftHasStarted) {
        this.draftHasStarted = draftHasStarted;
    }

    public boolean isDraftHasStarted() {
        return draftHasStarted;
    }

    public void setDraftHasStarted(boolean draftHasStarted) {
        this.draftHasStarted = draftHasStarted;
    }
}
