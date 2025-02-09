package com.example.demo.domain.essay.presentation.dto.response;

import com.example.demo.domain.essay.domain.vo.EssayInfoVO;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class EssayResponse {

    private Long essayId;

    private String sentence;

    private String title;

    private String content;

    private LocalDateTime createDate;

    private LocalDateTime lastModifyDate;

    private HostInfoDto hostInfo;

    private Boolean isOwner;

    private Boolean isLiked;

    private int likeCount;

    private int commentCount;

    public EssayResponse(EssayInfoVO essayInfoVO, boolean iHost, int likeCnt, int commentCnt, boolean checkLike) {

        essayId = essayInfoVO.getEssayId();
        sentence = essayInfoVO.getSentence();
        title = essayInfoVO.getTitle();
        content = essayInfoVO.getContent();
        likeCount = likeCnt;
        commentCount = commentCnt;
        createDate = essayInfoVO.getCreateAt();
        lastModifyDate = essayInfoVO.getLastModifyAt();
        hostInfo = new HostInfoDto(essayInfoVO.getHostInfoVO());
        isOwner = iHost;
        isLiked = checkLike;
    }
}
