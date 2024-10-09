package com.example.forest_app.api;

import com.example.forest_app.form.AuthForm;
import com.example.forest_app.form.CommentDetailRequest;
import com.example.forest_app.form.PostDetail;
import com.example.forest_app.form.PostDetailRequest;
import com.example.forest_app.form.RegisterForm;
import com.example.forest_app.form.ResponseForm;
import com.example.forest_app.form.Text_1_Img_4_Form;
import com.example.forest_app.form.Text_4_Form;
import com.example.forest_app.form.Text_4_Img_1_Form;


import org.w3c.dom.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/hello")
    Call<ResponseForm> hello();

    @GET("api/get-test-image")
    Call<ResponseForm> getImage();

    @POST("api/users/register-user")
    Call<AuthForm> registerUser(@Body RegisterForm registerForm);

    @POST("api/find-img-by-word")
    Call<Text_1_Img_4_Form> findImgByWord(@Body AuthForm auth);

    @POST("api/find-word-by-img")
    Call<Text_4_Img_1_Form> findWordByImg(@Body AuthForm auth);

    @POST("api/find-word-by-listening")
    Call<Text_4_Form> findWordByListening(@Body AuthForm auth);

    @POST("api/find-img-by-listening")
    Call<Text_1_Img_4_Form> findImgByListening(@Body AuthForm auth);

    @POST("api/try-speech")
    Call<ResponseForm> trySpeech(@Body AuthForm auth);

    @POST("api/find-statement-by-img")
    Call<Text_4_Img_1_Form> findStatementByImg(@Body AuthForm auth);



    // 게시글 생성
    @POST("/api/posts")
    Call<Void> createPost(@Body PostDetailRequest postDetailRequest);

    // 게시글 수정
    @PUT("/api/posts/{postId}")
    Call<Void> updatePost(@Path("postId") Long postId, @Body PostDetailRequest postDetailRequest);

    // 게시글 삭제
    @DELETE("/api/posts/{postId}")
    Call<Void> deletePost(@Path("postId") Long postId, @Body AuthForm authForm);

    // 게시글 열기
    @GET("/api/posts/{postId}")
    Call<PostDetail> getPost(@Path("postId") Long postId);

    // 댓글 달기
    @POST("/api/posts/{postId}/comments")
    Call<Void> addComment(@Path("postId") Long postId, @Body CommentDetailRequest commentDetailRequest);

    // 댓글 수정
    @PUT("/api/posts/comments/{commentId}")
    Call<Comment> updateComment(@Path("commentId") Long commentId, @Body CommentDetailRequest commentDetailRequest);

    // 댓글 삭제
    @DELETE("/api/posts/comments/{commentId}")
    Call<Void> deleteComment(@Path("commentId") Long commentId, @Body AuthForm authForm);

    // 좋아요 누르기
    @POST("/api/posts/{postId}/likes")
    Call<Void> likePost(@Path("postId") Long postId);

    // 게시글 목록 (최근 n개)
    @GET("/api/posts/recent")
    Call<List<PostDetail>> getRecentPosts(@Query("from") Integer from, @Query("to") Integer to);

    // 게시글 목록 (인기 게시물)
    @GET("/api/posts/popular")
    Call<List<PostDetail>> getPopularPosts(@Query("from") Integer from, @Query("to") Integer to);

    // 게시글 목록 (조회수 많은 게시물)
    @GET("/api/posts/most-view")
    Call<List<PostDetail>> getMostViewPosts(@Query("from") Integer from, @Query("to") Integer to);

}
