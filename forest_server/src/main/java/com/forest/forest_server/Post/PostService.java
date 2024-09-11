package com.forest.forest_server.Post;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시물 생성
    @Transactional
    public Post createPost(String author, Boolean anonymous, String title, String content) {
        Post post = new Post();
        post.setAuthor(author);
        post.setAnonymous(anonymous);
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    // 게시물 조회
    @Transactional(readOnly = true)
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    // 게시물 목록 조회 (전체 또는 작성자 기준)
    @Transactional(readOnly = true)
    public List<Post> getPostsByAuthor(String author) {
        return postRepository.findByAuthor(author);
    }

    // 게시물 수정
    @Transactional
    public Post updatePost(Long id, String title, String content) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setTitle(title);
            post.setContent(content);
            post.setUpdatedAt(LocalDateTime.now());
            return postRepository.save(post);
        } else {
            throw new IllegalArgumentException("Post not found");
        }
    }

    // 게시물 삭제
    @Transactional
    public void deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Post not found");
        }
    }

    // 게시물 조회수 증가
    @Transactional
    public void incrementViews(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setViews(post.getViews() + 1);
            postRepository.save(post);
        } else {
            throw new IllegalArgumentException("Post not found");
        }
    }

    // 게시물 좋아요 증가
    @Transactional
    public void incrementLikes(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setLikes(post.getLikes() + 1);
            postRepository.save(post);
        } else {
            throw new IllegalArgumentException("Post not found");
        }
    }
}
