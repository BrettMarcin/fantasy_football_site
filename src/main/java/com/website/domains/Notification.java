package com.website.domains;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Notification {

    @JsonProperty
    private String content;

    @JsonProperty
    private String userBelongs;

    @JsonProperty
    private int draftId;

    @JsonProperty
    private boolean hasBeenRead;

    public Notification() {

    }

    public Notification(String content, String userBelongs, int draftId, boolean hasBeenRead) {
        this.content = content;
        this.userBelongs = userBelongs;
        this.draftId = draftId;
        this.hasBeenRead = hasBeenRead;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserBelongs() {
        return userBelongs;
    }

    public void setUserBelongs(String userBelongs) {
        this.userBelongs = userBelongs;
    }

    public int getDraftId() {
        return draftId;
    }

    public void setDraftId(int draftId) {
        this.draftId = draftId;
    }

    public boolean isHasBeenRead() {
        return hasBeenRead;
    }

    public void setHasBeenRead(boolean hasBeenRead) {
        this.hasBeenRead = hasBeenRead;
    }
}
