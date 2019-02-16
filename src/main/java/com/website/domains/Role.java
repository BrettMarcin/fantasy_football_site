package com.website.domains;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name="role")
public class Role {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "role_name")
    @JsonProperty
    private String roleName;

    @Column(name = "description")
    @JsonProperty
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "the_user_name")
    @JsonProperty
    private User theUser;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTheUser(User theUser) {
        this.theUser = theUser;
    }

    public User getTheUser() {
        return theUser;
    }
}