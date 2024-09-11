package com.forest.forest_server.Attachment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    // 첨부파일 추가
    @PostMapping("/post/{postId}")
    public ResponseEntity<Attachment> addAttachment(@PathVariable Long postId,
                                                    @RequestParam String fileUrl) {
        Attachment attachment = attachmentService.addAttachment(postId, fileUrl);
        return new ResponseEntity<>(attachment, HttpStatus.CREATED);
    }

    // 특정 게시글의 첨부파일 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Attachment>> getAttachmentsByPostId(@PathVariable Long postId) {
        List<Attachment> attachments = attachmentService.getAttachmentsByPostId(postId);
        return new ResponseEntity<>(attachments, HttpStatus.OK);
    }

    // 첨부파일 삭제
    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long attachmentId) {
        attachmentService.deleteAttachment(attachmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
