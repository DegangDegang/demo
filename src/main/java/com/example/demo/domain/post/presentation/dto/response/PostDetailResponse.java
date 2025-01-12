package com.example.demo.domain.post.presentation.dto.response;

import com.example.demo.domain.post.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDetailResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifyDate;

    private Long userId;
    private String userNickname;

    public PostDetailResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate();
        this.lastModifyDate = post.getLastModifyDate();
        this.userId = post.getUser().getId();
        this.userNickname = post.getUser().getNickname();
    }
}
