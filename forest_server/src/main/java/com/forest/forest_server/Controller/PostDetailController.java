package com.forest.forest_server.Controller;

import com.forest.forest_server.Comment.Comment;
import com.forest.forest_server.Comment.CommentService;
import com.forest.forest_server.Post.Post;
import com.forest.forest_server.Post.PostService;
import com.forest.forest_server.User.ForestUser;
import com.forest.forest_server.User.UserService;
import com.forest.forest_server.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostDetailController {
    private final PostService postService;
    private final CommentService commentService;
    private final UserController userController;
    private final UserService userService;

    @Autowired
    public PostDetailController(PostService postService, CommentService commentService, UserController userController, UserService userService){
        this.postService = postService;
        this.commentService = commentService;
        this.userController = userController;
        this.userService = userService;
    }

    // 게시글 생성
    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostDetailRequest form) {
        AuthForm auth = form.getAuthForm();
        if(!userController.authenticateUser(auth))
            return ResponseEntity.status(401).build();

        postService.createPost(auth.getId(), form.getPostDetail());
        return ResponseEntity.ok().build();
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, @RequestBody PostDetailRequest form) {
        AuthForm auth = form.getAuthForm();
        if(!userController.authenticateUser(auth))
            return ResponseEntity.status(401).build();

        Post post = postService.getPostById(postId);
        ForestUser author = post.getAuthor();
        if(!author.getId().equals(auth.getId()))
            return ResponseEntity.status(403).build();

        postService.updatePost(form.getPostDetail());
        return ResponseEntity.ok().build();
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, @RequestBody AuthForm auth) {
        if(!userController.authenticateUser(auth))
            return ResponseEntity.status(401).build();

        Post post = postService.getPostById(postId);

        ForestUser author = post.getAuthor();
        if(!author.getId().equals(auth.getId()))
            return ResponseEntity.status(403).build();

        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    // 게시글 열기
    @GetMapping("/{postId}")
    public ResponseEntity<PostDetail> getPost(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        postService.incrementViews(postId);
        PostDetail postDetail = new PostDetail(post);
        return ResponseEntity.ok().body(postDetail);
    }

    // 댓글 달기
    @PostMapping("/{postId}/comments")
    public ResponseEntity<Void> addComment(@PathVariable Long postId, @RequestBody CommentDetailRequest form) {
        AuthForm auth = form.getAuthForm();
        if(!userController.authenticateUser(auth))
            return ResponseEntity.status(401).build();
        postService.addComment(postId, form.getCommentDetail());
        return ResponseEntity.ok().build();
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody CommentDetailRequest form) {
        AuthForm auth = form.getAuthForm();
        if(!userController.authenticateUser(auth))
            return ResponseEntity.status(401).build();
        Comment comment = new Comment(form.getCommentDetail());
        commentService.updateComment(commentId, comment);
        return ResponseEntity.ok().build(); // 나중에 댓글 수정 서비스 추가
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @RequestBody AuthForm auth) {
        if(!userController.authenticateUser(auth))
            return ResponseEntity.status(401).build();
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    // 좋아요 누르기
    @PostMapping("/{postId}/likes")
    public ResponseEntity<Void> likePost(@PathVariable Long postId) {
        postService.incrementLikes(postId);
        return ResponseEntity.ok().build();
    }

    // 게시글 목록 (최근 n개)
    @GetMapping("/recent")
    public ResponseEntity<List<PostDetail>> getRecentPosts(@RequestParam(required = false) Integer from,
                                                     @RequestParam(required = false) Integer to) {
        List<Post> posts;
        if(from == -1 || to == -1)
            posts = postService.getRecentPosts();
        else posts = postService.getRecentPosts(from, to);
        List<PostDetail> result = new ArrayList<>();
        for(Post p : posts)
            result.add(new PostDetail(p));
        return ResponseEntity.ok(result);
    }

    // 게시글 목록 (인기 게시물)
    @GetMapping("/popular")
    public ResponseEntity<List<PostDetail>> getPopularPosts(@RequestParam(required = false) Integer from,
                                                      @RequestParam(required = false) Integer to) {
        List<Post> posts;
        if(from == -1 || to == -1)
            posts = postService.getPopularPosts();
        else posts = postService.getPopularPosts(from, to);
        List<PostDetail> result = new ArrayList<>();
        for(Post p : posts)
            result.add(new PostDetail(p));
        return ResponseEntity.ok(result);
    }

    @GetMapping("most-view")
    public ResponseEntity<List<PostDetail>> getMostViewPosts(@RequestParam(required = false) Integer from,
                                                       @RequestParam(required = false) Integer to){
        List<Post> posts;
        if(from == -1 || to == -1)
            posts = postService.getMostViewPosts();
        else posts = postService.getMostViewPosts(from, to);
        List<PostDetail> result = new ArrayList<>();
        for(Post p : posts)
            result.add(new PostDetail(p));
        return ResponseEntity.ok(result);
    }
}