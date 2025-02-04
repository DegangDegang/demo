package com.example.demo.domain.essay.domain;

import com.example.demo.domain.essay.domain.vo.EssayCommentInfoVo;
import com.example.demo.domain.essay.exception.NotEssayCommentHostException;
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
public class EssayComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "essay_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "essay_id")
    private Essay essay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    @Builder
    public EssayComment(User user, Essay essay, String content) {
        this.essay = essay;
        this.user = user;
        this.content = content;
    }

    public EssayCommentInfoVo getEssayCommentInfoVo() {
        return EssayCommentInfoVo.builder()
                .essayCommentId(id)
                .content(content)
                .hostInfoVO(user.getUserInfo())
                .build();
    }

    // 연관과계 편의 메서드
    public void addMethod() {
        essay.getEssayComments().add(this);
    }

    public void validUserIsHost(Long id) {
        if (!checkUserIsHost(id)) {
            throw NotEssayCommentHostException.EXCEPTION;
        }
    }

    public boolean checkUserIsHost(Long id) {
        return user.getId().equals(id);
    }
}
