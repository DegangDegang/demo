package com.example.demo.domain.essay.presentation.dto.request;

import com.example.demo.domain.essay.service.dto.UpdateEssayDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateEssayRequest {

    private String title;

    private String content;

    public UpdateEssayDto toUpdateEssayDto() {
        return new UpdateEssayDto(
                title,
                content);
    }
}
