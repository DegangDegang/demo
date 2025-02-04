package com.example.demo.domain.essay.presentation.dto.response;

import com.example.demo.domain.essay.domain.vo.EssayCommentInfoVo;
import lombok.Getter;

@Getter
public class EssayCommentInfoDto {
    private Long essayCommentId;
    private String content;
    private HostInfoDto hostInfo;
    private Boolean iHost;

    public EssayCommentInfoDto(EssayCommentInfoVo essayCommentInfoVo, Boolean iHost) {
        this.essayCommentId = essayCommentInfoVo.getEssayCommentId();
        this.content = essayCommentInfoVo.getContent();
        this.hostInfo = new HostInfoDto(essayCommentInfoVo.getHostInfoVO());
        this.iHost = iHost;
    }
}
