package com.forest.forest_server.Post;

import com.forest.forest_server.User.ForestUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor(ForestUser author);
    List<Post> findTop10ByOrderByCreatedAtDesc();

    List<Post> findTop10ByOrderByLikesDesc();

    List<Post> findTop10ByOrderByViewsDesc();

    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findAllByOrderByLikesDesc();

    List<Post> findAllByOrderByViewsDesc();
}
