package com.example.demo.domain.novel.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.paragraph.domain.Paragraph;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "novels")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Novel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "novel_id")
	private Long id;

	private String title;

	@Lob
	private String content;

	@OneToMany(fetch = FetchType.LAZY)
	private final List<Paragraph> paragraphs = new ArrayList<>();

	private String category;

	private Boolean isEnd;

	@Builder
	public Novel(String title, String content, String category) {
		this.title = title;
		this.content = content;
		this.category = category;
		this.isEnd = false;
	}
}
