package com.logixtech.vote.dto;

import java.util.UUID;

public class UserDto {

    private UUID publicId;

    private String email;

    public UserDto() {
    }

    public UserDto(UUID publicId, String email) {
        this.publicId = publicId;
        this.email = email;
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
}
