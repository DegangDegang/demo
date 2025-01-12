package com.example.demo.domain.post.presentation.dto.response;

import com.example.demo.domain.post.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostSummaryResponse {

    private String title;
    private String content;
    private LocalDateTime createdDate;

    private String userNickname;

    public PostSummaryResponse(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate();
        this.userNickname = post.getUser().getNickname();
    }
}
