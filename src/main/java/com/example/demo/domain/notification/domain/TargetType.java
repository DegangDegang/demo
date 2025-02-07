package com.example.demo.domain.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TargetType {

	SHORT_ESSAY("단필", "short-essays"),
	ESSAY("장필", "essays"),
	NOVEL("연필", "novels"),
	PARAGRAPH("문맥", "paragraphs");

	private final String value;
	private final String url;

}
