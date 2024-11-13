package com.example.forest_app.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.forest_app.R;
import com.example.forest_app.activity.RegisterActivity;
import com.example.forest_app.activity.WritePostActivity;
import com.example.forest_app.api.ApiManager;
import com.example.forest_app.form.PostDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment2 extends Fragment {
    // community fragment
    private static final String TAG = "Community fragment";
    private ApiManager apiManager;
    private LinearLayout layout;

    public Fragment2() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        apiManager = new ApiManager(getResources().getString(R.string.SERVER_URL));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        layout = view.findViewById(R.id.postlist_layout);
        Button writeBtn = view.findViewById(R.id.make_post_btn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WritePostActivity.class);
                startActivity(intent);
            }
        });

        Call<List<PostDetail>> call = apiManager.getApiService().getRecentPosts(0, 10);
        call.enqueue(new Callback<List<PostDetail>>() {
            @Override
            public void onResponse(Call<List<PostDetail>> call, Response<List<PostDetail>> response) {
                if(response.isSuccessful()){
                    List<PostDetail> list = response.body();
                    if(list == null){
                        Log.e(TAG, "received post list is null");
                    }
                    else if(list.isEmpty())
                        return;
                    for(PostDetail post : list){
                        addPost(post);
                    }
                }
                else{
                    Log.e(TAG, "code: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<PostDetail>> call, Throwable t) {
                Log.e(TAG, "network error: "+t.getMessage());
            }
        });
    }

    private void addPost(PostDetail postData){
        TextView textView = new TextView(getContext());
        textView.setText(postData.getTitle());
        textView.setTextSize(20);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.rgb(226, 226, 226));
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textView.setLayoutParams(textParams);

        layout.addView(textView);
    }
}