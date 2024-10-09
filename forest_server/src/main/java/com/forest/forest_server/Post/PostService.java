package com.forest.forest_server.Post;

import com.forest.forest_server.Comment.Comment;
import com.forest.forest_server.Comment.CommentService;
import com.forest.forest_server.User.ForestUser;
import com.forest.forest_server.User.UserRepository;
import com.forest.forest_server.form.CommentDetail;
import com.forest.forest_server.form.PostDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, CommentService commentService){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentService = commentService;
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
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        ForestUser author = userRepository.findById(data.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Comment comment = new Comment();
        comment.setContent(data.getContent());
        comment.setAnonymous(data.getAnonymous());
        comment = commentService.createComment(postId, data.getAuthorId(), comment);
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
    public List<Post> getRecentPosts() {
        return postRepository.findTop10ByOrderByCreatedAtDesc();
    }
    @Transactional(readOnly = true)
    public List<Post> getRecentPosts(int from, int to){
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        return posts.subList(from-1, to);
    }

    // 인기 게시글 n개 가져오기
    @Transactional(readOnly = true)
    public List<Post> getPopularPosts() {
        return postRepository.findTop10ByOrderByLikesDesc();
    }
    @Transactional(readOnly = true)
    public List<Post> getPopularPosts(int from, int to){
        List<Post> posts = postRepository.findAllByOrderByLikesDesc();
        return posts.subList(from-1, to);
    }
    @Transactional
    public List<Post> getMostViewPosts(){ return postRepository.findTop10ByOrderByViewsDesc(); }
    @Transactional(readOnly = true)
    public List<Post> getMostViewPosts(int from, int to){
        List<Post> posts = postRepository.findAllByOrderByViewsDesc();
        return posts.subList(from-1, to);
    }
}
