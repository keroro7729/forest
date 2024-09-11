package com.forest.forest_server.Post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 작성
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestParam String author,
                                           @RequestParam Boolean anonymous,
                                           @RequestParam String title,
                                           @RequestParam String content) {
        Post post = postService.createPost(author, anonymous, title, content);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    // 게시글 조회 (ID로)
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        Optional<Post> post = postService.getPostById(postId);
        return post.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 모든 게시글 조회
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getPostsByAuthor(null);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId,
                                           @RequestParam String title,
                                           @RequestParam String content) {
        Post post = postService.updatePost(postId, title, content);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 게시글 조회수 증가
    @PostMapping("/{postId}/views")
    public ResponseEntity<Void> incrementViews(@PathVariable Long postId) {
        postService.incrementViews(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 게시글 좋아요 증가
    @PostMapping("/{postId}/likes")
    public ResponseEntity<Void> incrementLikes(@PathVariable Long postId) {
        postService.incrementLikes(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
