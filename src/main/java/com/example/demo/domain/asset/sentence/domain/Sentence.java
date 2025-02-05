package com.example.demo.domain.asset.sentence.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "sentences")
@Entity
public class Sentence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sentence_id")
    private Long id;

    private String sentence;
}
