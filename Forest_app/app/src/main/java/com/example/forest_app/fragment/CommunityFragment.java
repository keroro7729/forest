package com.example.forest_app.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.forest_app.R;
import com.example.forest_app.api.ApiManager;
import com.example.forest_app.form.Attachment;
import com.example.forest_app.form.Comment;
import com.example.forest_app.form.Post;
import com.example.forest_app.utils.AttachmentAdapter;
import com.example.forest_app.utils.CommentAdapter;
import com.example.forest_app.utils.PostAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;


public class CommunityFragment extends Fragment {

    private RecyclerView recyclerViewPosts;
    private RecyclerView recyclerViewComments;
    private RecyclerView recyclerViewAttachments;
    private EditText editTextComment;

    private PostAdapter postAdapter;
    private CommentAdapter commentAdapter;
    private AttachmentAdapter attachmentAdapter;

    private List<Post> postList;
    private List<Comment> commentList;
    private List<Attachment> attachmentList;

    private ApiManager apiManager = new ApiManager();

    public CommunityFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        // RecyclerView 초기화
        recyclerViewPosts = view.findViewById(R.id.recyclerViewPosts);
        recyclerViewComments = view.findViewById(R.id.recyclerViewComments);
        recyclerViewAttachments = view.findViewById(R.id.recyclerViewAttachments);
        editTextComment = view.findViewById(R.id.editTextComment);

        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAttachments.setLayoutManager(new LinearLayoutManager(getContext()));

        // Adapter 설정
        // postList is null fuck you gpt
        postAdapter = new PostAdapter(postList);
        commentAdapter = new CommentAdapter(commentList);
        attachmentAdapter = new AttachmentAdapter(attachmentList);

        recyclerViewPosts.setAdapter(postAdapter);
        recyclerViewComments.setAdapter(commentAdapter);
        recyclerViewAttachments.setAdapter(attachmentAdapter);

        // 게시글 클릭 리스너 설정 (예시로 전체 게시글 클릭)
        postAdapter.setOnItemClickListener(post -> {
            // 선택한 게시글의 ID로 댓글 및 첨부파일을 로드
            loadPostDetails(post.getId());
        });

        // 댓글 추가 버튼 클릭 리스너
        view.findViewById(R.id.buttonAddComment).setOnClickListener(v -> {
            String commentContent = editTextComment.getText().toString();
            if (!commentContent.isEmpty()) {
                // 댓글 추가 API 호출
                // addComment(postId, commentContent);
            } else {
                Toast.makeText(getContext(), "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        // 게시글 목록 로드
        loadPosts();

        return view;
    }

    private void loadPosts() {
         //API 요청: 게시글 목록 가져오기
         apiManager.getApiService().getAllPosts().enqueue(new Callback<List<Post>>() {
             @Override
             public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                 if (response.isSuccessful() && response.body() != null) {
                     postList = response.body();
                     postAdapter.notifyDataSetChanged();
                 }
             }

             @Override
             public void onFailure(Call<List<Post>> call, Throwable t) {
                 Toast.makeText(getContext(), "게시글을 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
             }
         });
    }

    private void loadPostDetails(Long postId) {
        // API 요청: 선택한 게시글의 댓글 및 첨부파일 가져오기
         apiManager.getApiService().getPostById(postId).enqueue(new Callback<Post>() {
             @Override
             public void onResponse(Call<Post> call, Response<Post> response) {
                 if (response.isSuccessful() && response.body() != null) {
                     Post postDetails = response.body();
                     //commentList = postDetails.getComments();
                     //attachmentList = postDetails.getAttachments();
                     commentAdapter.notifyDataSetChanged();
                     attachmentAdapter.notifyDataSetChanged();
                 }
             }

             @Override
             public void onFailure(Call<Post> call, Throwable t) {
                 Toast.makeText(getContext(), "게시글 상세 정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
             }
         });
    }

     private void addComment(Long postId, String author, Boolean anonymous, String content) {
         // API 요청: 댓글 추가
          apiManager.getApiService().addComment(postId, author, anonymous, content).enqueue(new Callback() {
              @Override
              public void onResponse(Call call, Response response) {
                  if (response.isSuccessful()) {
                      Toast.makeText(getContext(), "댓글이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                      editTextComment.setText("");
                      loadPostDetails(postId);
                  }
              }

              @Override
              public void onFailure(Call call, Throwable t) {
                  Toast.makeText(getContext(), "댓글 추가에 실패했습니다.", Toast.LENGTH_SHORT).show();
              }
          });
     }
}