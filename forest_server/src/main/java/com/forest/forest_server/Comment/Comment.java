package com.forest.forest_server.Comment;

import com.forest.forest_server.Post.Post;
import com.forest.forest_server.User.ForestUser;
import com.forest.forest_server.form.CommentDetail;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Comment")
@Getter @Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    private ForestUser author;

    @Column(nullable = false)
    private Boolean anonymous = false;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Comment(){}
    public Comment(CommentDetail commentDetail){
        anonymous = commentDetail.getAnonymous();
        content = commentDetail.getContent();
    }
}
