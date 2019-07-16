package com.website.domains;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Picks {

    @JsonProperty
    private String username;

    @JsonProperty
    private int draftId;

    @JsonProperty
    private Player thePlayer;

    @JsonProperty
    private boolean isUsed;

    @JsonProperty
    private int round;

    @JsonProperty
    private int pickNumber;

    public Picks(String username, int draftId, Player thePlayer, boolean isUsed, int round, int pickNumber) {
        this.username = username;
        this.draftId = draftId;
        this.thePlayer = thePlayer;
        this.isUsed = isUsed;
        this.round = round;
        this.pickNumber = pickNumber;
    }

    public Picks(String username, int draftId, int round, int pickNumber) {
        this.username = username;
        this.draftId = draftId;
        this.thePlayer = null;
        this.isUsed = false;
        this.round = round;
        this.pickNumber = pickNumber;
    }

    public Picks(String username, int round, int pickNumber) {
        this.username = username;
        this.thePlayer = null;
        this.isUsed = false;
        this.round = round;
        this.pickNumber = pickNumber;
    }

    public Picks() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getDraftId() {
        return draftId;
    }

    public void setDraftId(int draftId) {
        this.draftId = draftId;
    }

    public Player getThePlayer() {
        return thePlayer;
    }

    public void setThePlayer(Player thePlayer) {
        this.thePlayer = thePlayer;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getPickNumber() {
        return pickNumber;
    }

    public void setPickNumber(int pickNumber) {
        this.pickNumber = pickNumber;
    }
}
