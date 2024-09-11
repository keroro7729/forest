package com.example.forest_app.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forest_app.R;
import com.example.forest_app.form.Attachment;

import java.util.List;

public class AttachmentAdapter extends RecyclerView.Adapter<AttachmentAdapter.AttachmentViewHolder> {

    private List<Attachment> attachmentList;

    public AttachmentAdapter(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    @NonNull
    @Override
    public AttachmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attachment, parent, false);
        return new AttachmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttachmentViewHolder holder, int position) {
        Attachment attachment = attachmentList.get(position);
        holder.attachmentNameTextView.setText(attachment.getFileUrl());
        // 이미지나 아이콘을 설정할 수도 있습니다.
        // 예를 들어 Glide 라이브러리를 사용할 수 있습니다.
    }

    @Override
    public int getItemCount() {
        return attachmentList.size();
    }

    public static class AttachmentViewHolder extends RecyclerView.ViewHolder {
        TextView attachmentNameTextView;
        ImageView attachmentImageView;

        public AttachmentViewHolder(@NonNull View itemView) {
            super(itemView);
            attachmentNameTextView = itemView.findViewById(R.id.textViewAttachmentName);
            attachmentImageView = itemView.findViewById(R.id.imageViewAttachment);
        }
    }
}

