package com.website.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import java.sql.Timestamp;

import javax.persistence.*;
import javax.swing.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "draft")
public class Draft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    @JsonProperty
    private Date created;

//    @ManyToOne(fetch = FetchType.EAGER)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonProperty
    User userCreated;

    @JsonProperty
    @Column(name="draft_started")
    boolean draftStarted;

    @JsonProperty
    @NotNull
    @Column(name="is_public")
    boolean isPublic;

    @JsonProperty
    @Column(name="was_running")
    private String wasRunning;

    @Transient
    @JsonProperty
    List<String> usersAccepted;

    @Transient
    @JsonProperty
    List<String> usersInvited;

    public Draft(List<Object> db) {
        HashSet<String> userAccpetedFound = new HashSet<>();
        HashSet<String> userInvitedFound = new HashSet<>();
        int i = 0;
        for (Object theOb : db) {
            Object[] ob = (Object[])theOb;
            if (i == 0) {
                this.id = (Integer) ob[2];
                Timestamp theDate = (Timestamp) ob[3];
                this.created = new Date(theDate.getTime());
                this.draftStarted = (Boolean) ob[4];
                this.isPublic = (Boolean) ob[5];
                this.setUserCreated(new User((String)ob[6]));
                usersAccepted = new ArrayList<>();
                usersInvited = new ArrayList<>();
                this.wasRunning = (String)ob[7];
            }
            i++;
            if (!userAccpetedFound.contains((String)ob[0])) {
                usersAccepted.add((String)ob[0]);
            }
            if (!userInvitedFound.contains((String)ob[1])) {
                usersInvited.add((String)ob[0]);
            }
        }
    }

//    List<String> draftOrder;

    public void buildDraft (Object db) {
            Object[] ob = (Object[])db;
            this.id = (Integer) ob[0];
            Timestamp theDate = (Timestamp) ob[1];
            this.created = new Date(theDate.getTime());
            this.draftStarted = (Boolean) ob[2];
            this.isPublic = (Boolean) ob[3];
            this.setUserCreated(new User((String)ob[4]));
            this.wasRunning = (String)ob[5];
    }

    public Draft() {

    }

    protected void onCreate() {
        created = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public User getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(User userCreated) {
        this.userCreated = userCreated;
    }

    public boolean isDraftStarted() {
        return draftStarted;
    }

    public void setDraftStarted(boolean draftStarted) {
        this.draftStarted = draftStarted;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public List<String> getUsersAccepted() {
        return usersAccepted;
    }

    public void setUsersAccepted(List<String> usersAccepted) {
        this.usersAccepted = usersAccepted;
    }

    public List<String> getUsersInvited() {
        return usersInvited;
    }

    public void setUsersInvited(List<String> usersInvited) {
        this.usersInvited = usersInvited;
    }

    public String getWasRunning() {
        return wasRunning;
    }

    public void setWasRunning(String wasRunning) {
        this.wasRunning = wasRunning;
    }
}
