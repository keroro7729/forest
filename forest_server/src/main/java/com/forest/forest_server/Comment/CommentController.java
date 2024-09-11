package com.forest.forest_server.Comment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 추가
    @PostMapping("/post/{postId}")
    public ResponseEntity<Comment> addComment(@PathVariable Long postId,
                                              @RequestParam String author,
                                              @RequestParam Boolean anonymous,
                                              @RequestParam String content) {
        Comment comment = commentService.addComment(postId, author, anonymous, content);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    // 특정 게시글의 댓글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
