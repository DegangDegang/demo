package com.example.demo.domain.novel;

import com.example.demo.domain.essay.domain.Essay;
import com.example.demo.domain.novel.service.dto.ChatMessageSaveDto;
import com.example.demo.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "paragraph_comment")
public class ParagraphComment implements Serializable {

    private static final long serialVersionUID = 5090380600159441769L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "novel_id")
    private Novel novel;

    private String message;

    private String createdAt;

    @Builder
    public ParagraphComment(Long id, User user, Novel novel, String message, String createdAt) {
        this.id = id;
        this.user = user;
        this.novel = novel;
        this.message = message;
        this.createdAt = createdAt;
    }


}