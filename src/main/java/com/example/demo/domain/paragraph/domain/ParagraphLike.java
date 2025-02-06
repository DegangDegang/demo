package com.example.demo.domain.paragraph.domain;

import com.example.demo.domain.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParagraphLike {

	@Id
	@GeneratedValue
	@Column(name = "paragraph_like_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "paragraph_id")
	private Paragraph paragraph;

	@Builder
	public ParagraphLike(User user, Paragraph paragraph) {
		this.user = user;
		this.paragraph = paragraph;
	}

}
