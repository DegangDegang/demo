package com.example.demo.domain.essay.presentation.dto.request;

import com.example.demo.domain.essay.service.dto.UpdateEssayDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateEssayRequest {

    private String title;

    private String content;

    private String imageUrl;

    public UpdateEssayDto toUpdateEssayDto() {
        return new UpdateEssayDto(
                title,
                content,
                imageUrl);
    }
}
