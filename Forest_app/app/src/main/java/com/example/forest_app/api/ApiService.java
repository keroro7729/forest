package com.example.forest_app.api;

import com.example.forest_app.form.Attachment;
import com.example.forest_app.form.AuthForm;
import com.example.forest_app.form.Comment;
import com.example.forest_app.form.Post;
import com.example.forest_app.form.RegisterForm;
import com.example.forest_app.form.ResponseForm;
import com.example.forest_app.form.Text_1_Img_4_Form;
import com.example.forest_app.form.Text_4_Form;
import com.example.forest_app.form.Text_4_Img_1_Form;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/hello")
    Call<ResponseForm> hello();

    @GET("api/get-test-image")
    Call<ResponseForm> getImage();

    @POST("api/user/register-user")
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

    // 게시글 관련 API

    // POST /api/posts: 게시글 작성
    @FormUrlEncoded
    @POST("/api/posts")
    Call<Post> createPost(@Field("author") String author,
                          @Field("anonymous") Boolean anonymous,
                          @Field("title") String title,
                          @Field("content") String content);

    // GET /api/posts/{postId}: 특정 게시글 조회
    @GET("/api/posts/{postId}")
    Call<Post> getPostById(@Path("postId") Long postId);

    // GET /api/posts: 모든 게시글 조회
    @GET("/api/posts")
    Call<List<Post>> getAllPosts();

    // PUT /api/posts/{postId}: 게시글 수정
    @FormUrlEncoded
    @PUT("/api/posts/{postId}")
    Call<Post> updatePost(@Path("postId") Long postId,
                          @Field("title") String title,
                          @Field("content") String content);

    // DELETE /api/posts/{postId}: 게시글 삭제
    @DELETE("/api/posts/{postId}")
    Call<Void> deletePost(@Path("postId") Long postId);

    // POST /api/posts/{postId}/views: 게시글 조회수 증가
    @POST("/api/posts/{postId}/views")
    Call<Void> incrementViews(@Path("postId") Long postId);

    // POST /api/posts/{postId}/likes: 게시글 좋아요 증가
    @POST("/api/posts/{postId}/likes")
    Call<Void> incrementLikes(@Path("postId") Long postId);

    // 댓글 관련 API

    // POST /api/comments/post/{postId}: 게시글에 댓글 추가
    @FormUrlEncoded
    @POST("/api/comments/post/{postId}")
    Call<Comment> addComment(@Path("postId") Long postId,
                             @Field("author") String author,
                             @Field("anonymous") Boolean anonymous,
                             @Field("content") String content);

    // GET /api/comments/post/{postId}: 특정 게시글의 댓글 조회
    @GET("/api/comments/post/{postId}")
    Call<List<Comment>> getCommentsByPostId(@Path("postId") Long postId);

    // DELETE /api/comments/{commentId}: 댓글 삭제
    @DELETE("/api/comments/{commentId}")
    Call<Void> deleteComment(@Path("commentId") Long commentId);

    // 첨부파일 관련 API

    // POST /api/attachments/post/{postId}: 게시글에 첨부파일 추가
    @FormUrlEncoded
    @POST("/api/attachments/post/{postId}")
    Call<Attachment> addAttachment(@Path("postId") Long postId,
                                   @Field("fileUrl") String fileUrl);

    // GET /api/attachments/post/{postId}: 게시글의 첨부파일 조회
    @GET("/api/attachments/post/{postId}")
    Call<List<Attachment>> getAttachmentsByPostId(@Path("postId") Long postId);

    // DELETE /api/attachments/{attachmentId}: 첨부파일 삭제
    @DELETE("/api/attachments/{attachmentId}")
    Call<Void> deleteAttachment(@Path("attachmentId") Long attachmentId);
}
