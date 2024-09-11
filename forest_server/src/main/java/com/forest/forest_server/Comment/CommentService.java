package com.forest.forest_server.Comment;

import com.forest.forest_server.Post.Post;
import com.forest.forest_server.Post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    // 댓글 추가
    @Transactional
    public Comment addComment(Long postId, String author, Boolean anonymous, String content) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new IllegalArgumentException("Invalid post ID");
        }

        Comment comment = new Comment();
        comment.setPost(post.get());
        comment.setAuthor(author);
        comment.setAnonymous(anonymous);
        comment.setContent(content);

        return commentRepository.save(comment);
    }

    // 특정 포스트의 댓글 조회
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new IllegalArgumentException("Comment not found");
        }
    }
}
