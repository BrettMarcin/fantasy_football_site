package com.website.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private Integer id;

    @JsonProperty
    @Column(nullable = false, name="name")
    private String name;

    @JsonProperty
    @Column(nullable = false, name="user_name")
    private String userName;

    @JsonProperty
    @Column(nullable = false)
    private String password;

    @JsonProperty
    @Column(nullable = false)
    private String email;

    @ManyToMany(targetEntity = Role.class, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "users_role",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "users_name") }
    )
    @JsonProperty
    private List<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreated")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonProperty
    private List<Draft> draft;

    @ManyToMany(targetEntity = Draft.class, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "draft_users_accepted",
            joinColumns = { @JoinColumn(name = "draft_id") },
            inverseJoinColumns = { @JoinColumn(name = "users_name") }
    )
    @JsonProperty
    private Set<Draft> acceptedDrafts;

    @ManyToMany(mappedBy = "usersInvited", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JsonProperty
    private Set<Draft> draftsInvitedTo;

    public User (String name, String userName, String email, String password) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getUsername() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public boolean isEnabled() {
        return true;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}