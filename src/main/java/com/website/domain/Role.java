package com.website.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Set;

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

    @ManyToMany(targetEntity = Role.class, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "users_role",
            joinColumns = { @JoinColumn(name = "users_name") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    @JsonProperty
    private Set<User> theUser;


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

    public Set<User> getTheUser() {
        return theUser;
    }

    public void setTheUser(Set<User> theUser) {
        this.theUser = theUser;
    }
}