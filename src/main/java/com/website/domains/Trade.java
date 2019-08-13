package com.website.domains;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Trade {

    @JsonProperty
    private String userPropsed;

    @JsonProperty
    private String userToSend;

    @JsonProperty
    private List<Picks> userPropsedPicks;

    @JsonProperty
    private List<Picks> userToSendPicks;

    public Trade() {

    }

    public String getUserPropsed() {
        return userPropsed;
    }

    public void setUserPropsed(String userPropsed) {
        this.userPropsed = userPropsed;
    }

    public String getUserToSend() {
        return userToSend;
    }

    public void setUserToSend(String userToSend) {
        this.userToSend = userToSend;
    }

    public List<Picks> getUserPropsedPicks() {
        return userPropsedPicks;
    }

    public void setUserPropsedPicks(List<Picks> userPropsedPicks) {
        this.userPropsedPicks = userPropsedPicks;
    }

    public List<Picks> getUserToSendPicks() {
        return userToSendPicks;
    }

    public void setUserToSendPicks(List<Picks> userToSendPicks) {
        this.userToSendPicks = userToSendPicks;
    }
}
