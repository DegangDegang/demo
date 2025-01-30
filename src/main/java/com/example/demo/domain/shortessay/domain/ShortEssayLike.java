package com.example.demo.domain.shortessay.domain;

import com.example.demo.domain.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ShortEssayLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
