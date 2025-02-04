package com.example.demo.domain.essay.presentation.dto.response;

import com.example.demo.domain.essay.domain.vo.EssayInfoVO;
import lombok.Getter;

@Getter
public class EssayBriefInfoDto {

    private Long essayId;
    private String title;
    private String content;
    private String imageURL;
    private HostInfoDto hostInfo;

    public EssayBriefInfoDto(EssayInfoVO essayInfoVO) {
        essayId = essayInfoVO.getEssayId();
        title = essayInfoVO.getTitle();
        content = essayInfoVO.getContent();
        imageURL = essayInfoVO.getImageUrl();
        hostInfo = new HostInfoDto(essayInfoVO.getHostInfoVO());
    }
}
