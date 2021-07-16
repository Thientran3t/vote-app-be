package com.logixtech.vote.dto.request;

import java.util.UUID;

public class VotePostRequest {

    private UUID postId;
    private UUID userId;

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
