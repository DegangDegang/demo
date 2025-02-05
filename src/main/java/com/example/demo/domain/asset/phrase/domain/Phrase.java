package com.example.demo.domain.asset.phrase.domain;

import com.example.demo.domain.asset.phrase.domain.vo.PhraseInfoVo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Table(name = "phrases")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Phrase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "phrase_id")
	private Long id;

	private String phrase;

	public Phrase(String phrase) {
		this.phrase = phrase;
	}

	public PhraseInfoVo getPhraseInfo() {
		return new PhraseInfoVo(id, phrase);
	}

}
