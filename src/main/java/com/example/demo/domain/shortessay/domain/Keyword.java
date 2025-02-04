package com.example.demo.domain.shortessay.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword {
	private String keyword1;
	private String keyword2;
	private String keyword3;

	public Keyword(String keyword1, String keyword2, String keyword3) {
		this.keyword1 = keyword1;
		this.keyword2 = keyword2;
		this.keyword3 = keyword3;
	}
}
