package com.forest.forest_server.Post;

import com.forest.forest_server.Comment.Comment;
import com.forest.forest_server.User.ForestUser;
import com.forest.forest_server.form.PostDetail;
import com.forest.forest_server.form.PostDetailRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Post")
@Getter @Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "forest_user_id", nullable = false)
    private ForestUser author;

    @Column(nullable = false)
    private Boolean anonymous = false;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "views", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int views;

    @Column(name = "likes", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public Post(){}
    public Post(PostDetail postDetail){
        anonymous = postDetail.getAnonymous();
        title = postDetail.getTitle();
        content = postDetail.getContent();
    }
}