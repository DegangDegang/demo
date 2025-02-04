package com.example.demo.domain.essay.domain;

import com.example.demo.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EssayLike {

    @Id
    @GeneratedValue
    @Column(name = "essay_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "essay_id")
    private Essay essay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public EssayLike(Essay essay, User user) {
        this.essay = essay;
        this.user = user;
    }

    //연관 관계
    public void addMethod() {
        essay.getEssayLikes().add(this);
    }



}
