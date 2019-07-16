package com.website.domains;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class GetDrafts {

    @JsonProperty
    private List<Draft> publicDrafts;

    @JsonProperty
    private List<Draft> invitedDrafts;

    @JsonProperty
    private List<Draft> yourDrafts;

    public GetDrafts() {
        publicDrafts = new ArrayList<>();
        invitedDrafts = new ArrayList<>();
        yourDrafts = new ArrayList<>();
    }

    public List<Draft> getPublicDrafts() {
        return publicDrafts;
    }

    public void setPublicDrafts(Draft publicDrafts) {
        this.publicDrafts.add(publicDrafts);
    }

    public List<Draft> getInvitedDrafts() {
        return invitedDrafts;
    }

    public void setInvitedDrafts(Draft invitedDrafts) {
        this.invitedDrafts.add(invitedDrafts);
    }

    public List<Draft> getYourDrafts() {
        return yourDrafts;
    }

    public void setYourDrafts(Draft yourDrafts) {
        this.yourDrafts.add(yourDrafts);
    }
}
