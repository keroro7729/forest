package com.forest.forest_server.Post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor(String author);
    List<Post> findTopNByOrderByCreatedAtDesc(int limit);
    List<Post> findTopNByOrderByLikesDesc(int limit);
}
