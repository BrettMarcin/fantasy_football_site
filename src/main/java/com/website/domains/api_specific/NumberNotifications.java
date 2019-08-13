package com.website.domains.api_specific;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NumberNotifications {

    @JsonProperty
    private long number;


    public NumberNotifications() {

    }

    public NumberNotifications(long number) {
        this.number = number;
    }
}
