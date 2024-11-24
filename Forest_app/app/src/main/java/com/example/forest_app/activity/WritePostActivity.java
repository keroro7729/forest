package com.example.forest_app.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.forest_app.R;
import com.example.forest_app.api.ApiManager;
import com.example.forest_app.form.PostDetail;
import com.example.forest_app.form.PostDetailRequest;
import com.example.forest_app.utils.LocalDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class WritePostActivity extends AppCompatActivity {

    private static final String TAG = "WritePostActivity";
    private TextView title, body;
    private Button submit;
    private ApiManager apiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_write_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        apiManager = new ApiManager(getResources().getString(R.string.SERVER_URL));
        submit = findViewById(R.id.submit_post_btn);
        title = findViewById(R.id.title_text_input);
        body = findViewById(R.id.post_body_text_input);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = title.getText().toString();
                String b = title.getText().toString();

                PostDetail post = new PostDetail();
                post.setAnonymous(false);
                post.setContent(b);
                post.setTitle(t);
                PostDetailRequest request = new PostDetailRequest();
                request.setAuthForm(LocalDatabase.getInstance(getApplicationContext()).getAuthForm("token"));
                request.setPostDetail(post);

                Call<Void> call = apiManager.getApiService().createPost(request);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(!response.isSuccessful()){
                            Log.e(TAG, "code: "+response.code());
                            Toast.makeText(getApplicationContext(), "작성 실패.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e(TAG, "network error: "+t.getMessage());
                        Toast.makeText(getApplicationContext(), "작성 실패.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}