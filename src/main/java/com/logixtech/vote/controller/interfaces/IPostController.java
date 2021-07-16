package com.logixtech.vote.controller.interfaces;

import com.logixtech.vote.dto.PostDto;
import com.logixtech.vote.dto.request.VotePostRequest;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/post")
public interface IPostController {

    @GetMapping("")
    Page<PostDto> getAllPost(@RequestParam("date")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam("page") int pageIndex,
                             @RequestParam("size") int pageSize);
    @PostMapping("/vote")
    @CrossOrigin
    PostDto votePost(@RequestBody VotePostRequest votePostRequest);
}
