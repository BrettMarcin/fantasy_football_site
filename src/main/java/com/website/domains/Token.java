package com.website.domains;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {

    public Token(String theToken) {
        this.theToken = theToken;
    }


    public String getTheToken() {
        return theToken;
    }

    public void setTheToken(String theToken) {
        this.theToken = theToken;
    }

    @JsonProperty("access_token")
    private String theToken;

}
