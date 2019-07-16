package com.website.domains;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TheTime {

    @JsonProperty
    private long theTime;

    public TheTime() {
    }

    public TheTime(long theTime) {
        this.theTime = theTime;
    }
}
