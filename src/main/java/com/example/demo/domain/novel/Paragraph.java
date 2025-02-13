package com.example.demo.domain.novel;

import com.example.demo.domain.user.domain.User;
import com.example.demo.global.database.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Paragraph extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paragraph_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "novel_id")
    private Novel novel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    private Boolean isAdopted;

    @Builder
    public Paragraph(Novel novel, User user, String content, Boolean isAdopted) {
        this.novel = novel;
        this.user = user;
        this.content = content;
        this.isAdopted = isAdopted;
    }

}
