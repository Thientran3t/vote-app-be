package com.logixtech.vote.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "test_user")
public class User {

    @JsonIgnore
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(updatable = false, nullable = false, unique = true)
    private UUID publicId;

    @Column(length = 255, unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @NotNull
    @Column(length = 60, nullable = false)
    private String password;

    @Column(name = "created_date", updatable = false)
    @JsonIgnore
    private Instant createdDate = Instant.now();

    private Instant lastModifiedDate = Instant.now();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "test_user_authority",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "test_user_vote_post",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "post_id", referencedColumnName = "id") }
    )
    private List<Post> postAlreadyVoted = new ArrayList<>();

    @PrePersist
    public void init() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public List<Post> getPostAlreadyVoted() {
        return postAlreadyVoted;
    }

    public void setPostAlreadyVoted(List<Post> postAlreadyVoted) {
        this.postAlreadyVoted = postAlreadyVoted;
    }
}
