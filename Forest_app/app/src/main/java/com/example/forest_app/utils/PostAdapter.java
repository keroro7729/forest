package com.example.forest_app.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forest_app.R;
import com.example.forest_app.form.PostDetail;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<PostDetail> postList;
    private OnItemClickListener onItemClickListener;

    public PostAdapter(List<PostDetail> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostDetail post = postList.get(position);
        holder.titleTextView.setText(post.getTitle());
        holder.contentTextView.setText(post.getContent());
        holder.authorAndDateTextView.setText(post.getAuthor() + " | " + post.getCreatedAt());

        // 클릭 이벤트 설정
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(post);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView contentTextView;
        TextView authorAndDateTextView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            contentTextView = itemView.findViewById(R.id.textViewContent);
            authorAndDateTextView = itemView.findViewById(R.id.textViewAuthorAndDate);
        }
    }

    // OnItemClickListener 인터페이스
    public interface OnItemClickListener {
        void onItemClick(PostDetail post);
    }

    // 리스너 설정 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
