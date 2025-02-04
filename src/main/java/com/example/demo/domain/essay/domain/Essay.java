package com.example.demo.domain.essay.domain;

import com.example.demo.domain.essay.domain.vo.EssayInfoVO;
import com.example.demo.domain.essay.exception.NotEssayHostException;
import com.example.demo.domain.essay.service.dto.UpdateEssayDto;
import com.example.demo.domain.user.domain.User;
import com.example.demo.global.database.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Essay extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "essay_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "essay",cascade = CascadeType.ALL)
    private List<EssayComment> essayComments = new ArrayList<>();

    @OneToMany(mappedBy = "essay",cascade = CascadeType.ALL)
    private List<EssayLike> essayLikes = new ArrayList<>();

    private String title;

    @Lob
    private String content;

    private String imageUrl;

    @Builder
    public Essay(User user, String title, String content, String imageUrl) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public EssayInfoVO getEssayInfoVo() {
        return EssayInfoVO.builder()
                .essayId(id)
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .createAt(getCreatedAt())
                .lastModifyAt(getLastModifyAt())
                .hostInfoVO(user.getUserInfo())
                .build();
    }

    public void updateEssay(UpdateEssayDto updateEssayDto) {
        this.title = updateEssayDto.getTitle();
        this.content = updateEssayDto.getContent();
        this.imageUrl = updateEssayDto.getImageURL();
    }

    public Boolean checkUserIsHost(Long id) {
        return user.getId().equals(id);
    }

    public void validUserIsHost(Long id) {
        if (!checkUserIsHost(id)) {
            throw NotEssayHostException.EXCEPTION;
        }

    }

    public int getLikeNum() {
        return essayLikes.size();
    }

    public int getCommentNum() {
        return essayComments.size();
    }


}
