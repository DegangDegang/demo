package com.example.demo.domain.essay.presentation.dto.response;

import com.example.demo.domain.essay.domain.vo.EssayInfoVO;
import lombok.Getter;

@Getter
public class EssayBriefInfoDto {

    private Long essayId;
    private String title;
    private String content;
    private HostInfoDto hostInfo;

    public EssayBriefInfoDto(EssayInfoVO essayInfoVO) {
        essayId = essayInfoVO.getEssayId();
        title = essayInfoVO.getTitle();
        content = essayInfoVO.getContent();
        hostInfo = new HostInfoDto(essayInfoVO.getHostInfoVO());
    }
}
