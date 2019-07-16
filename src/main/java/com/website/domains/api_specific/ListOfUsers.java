package com.website.domains.api_specific;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ListOfUsers {

    @JsonProperty
    private List<String> users;

    public ListOfUsers(List<String> users) {
        this.users = users;
    }
}
