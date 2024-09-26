package com.forest.forest_server.Post;

import com.forest.forest_server.Comment.Comment;
import com.forest.forest_server.User.ForestUser;
import com.forest.forest_server.User.UserRepository;
import com.forest.forest_server.form.CommentDetail;
import com.forest.forest_server.form.PostDetail;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 게시글 생성
    @Transactional
    public Post createPost(long userId, PostDetail data) {
        ForestUser author = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Post post = new Post();
        post.setAuthor(author);
        post.setAnonymous(data.getAnonymous());
        post.setTitle(data.getTitle());
        post.setContent(data.getContent());
        post.setCreatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }



    // 게시글 조회
    @Transactional(readOnly = true)
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
    }

    // 모든 게시글 조회
    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // 게시글 수정
    @Transactional
    public Post updatePost(PostDetail data) {
        Post post = postRepository.findById(data.getId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        post.setAnonymous(data.getAnonymous());
        post.setTitle(data.getTitle());
        post.setContent(data.getContent());
        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        postRepository.delete(post);
    }

    // 댓글 생성
    @Transactional
    public void addComment(long postId, CommentDetail data){
        Comment comment = new Comment();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        comment.setPost(post);

        ForestUser author = userRepository.findById(data.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        comment.setAuthor(author);

        comment.setAnonymous(data.getAnonymous());
        comment.setContent(data.getContent());
        comment.setCreatedAt(LocalDateTime.now());

        post.getComments().add(comment);
        postRepository.save(post);
    }

    // 조회수 증가
    @Transactional
    public void incrementViews(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        post.setViews(post.getViews() + 1);
        postRepository.save(post);
    }

    // 좋아요 증가
    @Transactional
    public void incrementLikes(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
    }

    // 최신 게시글 n개 가져오기
    @Transactional(readOnly = true)
    public List<Post> getRecentPosts(int limit) {
        return postRepository.findTopNByOrderByCreatedAtDesc(limit);
    }

    // 인기 게시글 n개 가져오기
    @Transactional(readOnly = true)
    public List<Post> getPopularPosts(int limit) {
        return postRepository.findTopNByOrderByLikesDesc(limit);
    }
}
