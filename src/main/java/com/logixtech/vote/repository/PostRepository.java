package com.logixtech.vote.repository;

import com.logixtech.vote.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCreatedDateOrderById(LocalDate date, Pageable page);
    long countAllByCreatedDate(LocalDate date);
    Post findByPublicId(UUID id);
}
