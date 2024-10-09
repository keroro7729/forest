package com.example.forest_app.fragment;

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
    private OnItemClickListener listener;

    // 인터페이스로 아이템 클릭 리스너 처리
    public interface OnItemClickListener {
        void onItemClick(PostDetail post);
    }

    public PostAdapter(List<PostDetail> postList, OnItemClickListener listener) {
        this.postList = postList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostDetail post = postList.get(position);

        // 제목 [댓글 수] 표시
        holder.titleTextView.setText(post.getTitle() + " [" + post.getComments().size() + "]");
        // 조회수, 좋아요수 표시
        holder.viewsTextView.setText("Views: " + post.getViews());
        holder.likesTextView.setText("Likes: " + post.getLikes());
        // 작성자 및 작성일 표시
        holder.authorDateTextView.setText(post.getAuthor() + " | " + post.getCreatedAt());

        // 클릭 이벤트 처리
        holder.itemView.setOnClickListener(v -> listener.onItemClick(post));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void setPostList(List<PostDetail> postList) {
        this.postList = postList;
        notifyDataSetChanged();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView viewsTextView;
        TextView likesTextView;
        TextView authorDateTextView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.post_title);
            viewsTextView = itemView.findViewById(R.id.post_views);
            likesTextView = itemView.findViewById(R.id.post_likes);
            authorDateTextView = itemView.findViewById(R.id.post_author_date);
        }
    }
}
