package com.website.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "draft")
public class Draft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
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

    @ManyToMany(mappedBy = "acceptedDrafts")
    Set<User> usersAccepted;


    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "draft_users_invited",
            joinColumns = { @JoinColumn(name = "users_name") },
            inverseJoinColumns = { @JoinColumn(name = "draft_id") }
    )
    Set<User> usersInvited;

//    Set<String> draftOrder;

    @PrePersist
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
}
