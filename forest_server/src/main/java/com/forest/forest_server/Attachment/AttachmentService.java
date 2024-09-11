package com.forest.forest_server.Attachment;

import com.forest.forest_server.Post.Post;
import com.forest.forest_server.Post.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final PostRepository postRepository;

    public AttachmentService(AttachmentRepository attachmentRepository, PostRepository postRepository) {
        this.attachmentRepository = attachmentRepository;
        this.postRepository = postRepository;
    }

    // 첨부파일 추가
    @Transactional
    public Attachment addAttachment(Long postId, String fileUrl) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid post ID");
        }

        Attachment attachment = new Attachment();
        attachment.setPost(postOptional.get());
        attachment.setFileUrl(fileUrl);
        return attachmentRepository.save(attachment);
    }

    // 특정 포스트의 첨부파일 목록 조회
    @Transactional(readOnly = true)
    public List<Attachment> getAttachmentsByPostId(Long postId) {
        return attachmentRepository.findByPostId(postId);
    }

    // 첨부파일 삭제
    @Transactional
    public void deleteAttachment(Long attachmentId) {
        if (attachmentRepository.existsById(attachmentId)) {
            attachmentRepository.deleteById(attachmentId);
        } else {
            throw new IllegalArgumentException("Attachment not found");
        }
    }
}