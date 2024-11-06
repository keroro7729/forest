package com.example.forest_app.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.forest_app.R;
import com.example.forest_app.api.ApiManager;
import com.example.forest_app.form.PostDetail;
import com.example.forest_app.form.PostDetailRequest;
import com.example.forest_app.utils.PostAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommunityFragment extends Fragment {

    private RecyclerView recyclerView;
    //private PostAdapter postAdapter;
    private ApiManager apiManager;

    private Button recent, views, likes;
    private String sortType;
    private int from, to;

    public CommunityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        recyclerView = view.findViewById(R.id.post_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        /*postAdapter = new PostAdapter(new ArrayList<>(), post -> {
            openPostDetailFragment(post);
        });
        recyclerView.setAdapter(postAdapter);*/

        // xml 페이지 컨트롤, 글쓰기 버튼 추가 후 로직까지 작성
        sortType = "recent";
        setFromTo(1);
        loadPosts(sortType, from, to);
        recent = view.findViewById(R.id.btn_sort_recent);
        views = view.findViewById(R.id.btn_sort_views);
        likes = view.findViewById(R.id.btn_sort_likes);
        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortType = "recent";
                setColor(sortType);
                setFromTo(1);
                loadPosts(sortType, from, to);
            }
        });
        views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortType = "views";
                setColor(sortType);
                setFromTo(1);
                loadPosts(sortType, from, to);
            }
        });
        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortType = "likes";
                setColor(sortType);
                setFromTo(1);
                loadPosts(sortType, from, to);
            }
        });
        return view;
    }

    private void loadPosts(String sortType, int from, int to) {
        Call<List<PostDetail>> call;
        switch(sortType){
            case "recent":
                call = apiManager.getApiService().getRecentPosts(from, to);
                call.enqueue(new Callback<List<PostDetail>>() {
                    @Override
                    public void onResponse(Call<List<PostDetail>> call, Response<List<PostDetail>> response) {
                        if(response.isSuccessful())
                            //postAdapter.setPostList(response.body());
                        else Log.e("getRecentPosts", "request: "+call.request()+"\n"+
                                    "http code: "+response.code());
                    }

                    @Override
                    public void onFailure(Call<List<PostDetail>> call, Throwable t) {
                        Log.e("getRecentPosts", "request: "+call.request()+"\n"+
                                "network error: "+t.getMessage());
                    }
                }); break;
            case "views":
                call = apiManager.getApiService().getMostViewPosts(from, to);
                call.enqueue(new Callback<List<PostDetail>>() {
                    @Override
                    public void onResponse(Call<List<PostDetail>> call, Response<List<PostDetail>> response) {
                        if(response.isSuccessful())
                            postAdapter.setPostList(response.body());
                        else Log.e("getMostViewPosts", "request: "+call.request()+"\n"+
                                "http code: "+response.code());
                    }

                    @Override
                    public void onFailure(Call<List<PostDetail>> call, Throwable t) {
                        Log.e("getMostViewPosts", "request: "+call.request()+"\n"+
                                "network error: "+t.getMessage());
                    }
                }); break;
            case "likes":
                call = apiManager.getApiService().getPopularPosts(from, to);
                call.enqueue(new Callback<List<PostDetail>>() {
                    @Override
                    public void onResponse(Call<List<PostDetail>> call, Response<List<PostDetail>> response) {
                        if(response.isSuccessful())
                            postAdapter.setPostList(response.body());
                        else Log.e("getPopularPosts", "request: "+call.request()+"\n"+
                                "http code: "+response.code());
                    }

                    @Override
                    public void onFailure(Call<List<PostDetail>> call, Throwable t) {
                        Log.e("getPopularPosts", "request: "+call.request()+"\n"+
                                "network error: "+t.getMessage());
                    }
                }); break;
        }
    }

    private void setFromTo(int n){
        int size = 10;
        from = size * (n-1) +1;
        to = size * n;
    }

    private void setColor(String type){
        recent.setBackgroundColor(Color.GRAY);
        views.setBackgroundColor(Color.GRAY);
        likes.setBackgroundColor(Color.GRAY);
        switch(type){
            case "recent": recent.setBackgroundColor(Color.GREEN); break;
            case "views": views.setBackgroundColor(Color.GREEN); break;
            case "likes": likes.setBackgroundColor(Color.GREEN); break;
        }
    }

    private void openPostDetailFragment(PostDetail post) {
        // 게시글 상세 프래그먼트로 전환
    }
}