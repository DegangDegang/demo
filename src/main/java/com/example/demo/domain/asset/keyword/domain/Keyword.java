package com.example.demo.domain.asset.keyword.domain;

import com.example.demo.domain.asset.keyword.domain.vo.KeywordInfoVo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Table(name = "keywords")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "keyword_id")
	private Long id;

	private String word;

	public Keyword(String word) {
		this.word = word;
	}

	public KeywordInfoVo getKeywordInfo() {
		return new KeywordInfoVo(id, word);
	}


}
