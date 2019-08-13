package com.website.domains.api_specific;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class UserInvitedAndAccepted {

    @JsonProperty
    private List<String> usersInvited;
    @JsonProperty
    private List<String> usersAccepted;

    public UserInvitedAndAccepted() {
        usersInvited = new ArrayList<>();
        usersAccepted = new ArrayList<>();
    }

    public List<String> getUsersInvited() {
        return usersInvited;
    }

    public void setUsersInvited(List<String> usersInvited) {
        this.usersInvited = usersInvited;
    }

    public List<String> getUsersAccepted() {
        return usersAccepted;
    }

    public void setUsersAccepted(List<String> usersAccepted) {
        this.usersAccepted = usersAccepted;
    }
}
