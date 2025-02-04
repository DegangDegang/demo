package com.example.demo.domain.asset.word.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Table(name = "words")
@Entity
public class Word {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "word_id")
	private Long id;

	private String word;

}
