package com.example.demo.domain.shortessay.domain;

import com.example.demo.domain.user.domain.User;
import com.example.demo.global.database.BaseEntity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ShortEssayLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "short_essay_like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "short_essay_id")
    private ShortEssay shortEssay;

    @Builder
    public ShortEssayLike(User user, ShortEssay shortEssay) {
        this.user = user;
        this.shortEssay = shortEssay;
    }
}
