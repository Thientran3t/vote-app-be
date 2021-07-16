package com.logixtech.vote.service;

import com.logixtech.vote.dto.PostDto;
import com.logixtech.vote.dto.request.VotePostRequest;
import com.logixtech.vote.exception.LimitVotePostException;
import com.logixtech.vote.exception.PostNotFoundException;
import com.logixtech.vote.exception.UserNotFoundException;
import com.logixtech.vote.model.Post;
import com.logixtech.vote.model.User;
import com.logixtech.vote.repository.PostRepository;
import com.logixtech.vote.repository.UserRepository;
import javassist.tools.web.BadHttpRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Page<PostDto> getAllPosts(LocalDate date, Pageable pageable) {
        List<PostDto> postDtoList = postRepository.findAllByCreatedDateOrderById(date, pageable)
                .stream()
                .map(post -> {
                    PostDto postDto = new PostDto();
                    postDto.setPublicId(post.getPublicId());
                    postDto.setDescription(post.getDescription());
                    postDto.setName(post.getName());
                    postDto.setVoteCount(post.getVoteCount());
                    return postDto;
                }).collect(Collectors.toList());
        long count = postRepository.countAllByCreatedDate(date);
        return new PageImpl<>(postDtoList, pageable, count);
    }

    public PostDto votePost(VotePostRequest votePostRequest) throws BadHttpRequest {
        if (votePostRequest.getPostId() == null || votePostRequest.getUserId() == null) {
            throw new BadHttpRequest();
        }
        Post post = postRepository.findByPublicId(votePostRequest.getPostId());
        if (post == null) {
            throw new PostNotFoundException();
        }
        User user = userRepository.findByPublicId(votePostRequest.getUserId());
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (!user.getPostAlreadyVoted().isEmpty()) {
            long count = user.getPostAlreadyVoted().stream()
                    .filter(p -> post.getPublicId().equals(p.getPublicId())).count();
            if (count >= 3) {
                throw new LimitVotePostException("Post is already vote 3 time");
            }
        }
        long voteCount = post.getVoteCount();
        voteCount = voteCount + 1;
        post.setVoteCount(voteCount);
        postRepository.save(post);
        List<Post> posts = new ArrayList<>();
        posts.add(post);

        user.setPostAlreadyVoted(posts);
        userRepository.save(user);
        return null;
    }
}
