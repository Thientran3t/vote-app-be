package com.logixtech.vote.controller;

import com.logixtech.vote.controller.interfaces.IPostController;
import com.logixtech.vote.dto.PostDto;
import com.logixtech.vote.dto.request.VotePostRequest;
import com.logixtech.vote.service.PostService;
import javassist.tools.web.BadHttpRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class PostController implements IPostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Override
    public Page<PostDto> getAllPost(LocalDate date, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return postService.getAllPosts(date, pageable);
    }

    @Override
    public PostDto votePost(VotePostRequest votePostRequest) {
        try {
            return postService.votePost(votePostRequest);
        } catch (BadHttpRequest badHttpRequest) {
            badHttpRequest.printStackTrace();
            return null;
        }
    }
}
