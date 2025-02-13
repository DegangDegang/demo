package com.example.demo.domain.novel;

import com.example.demo.global.database.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Novel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "novel_id")
    private Long id;

    @OneToMany(mappedBy = "novel",cascade = CascadeType.ALL)
    private List<Paragraph> paragraphs = new ArrayList<>();

    private String title;

    @Enumerated(EnumType.STRING)
    private NovelCategory novelCategory;

    private String imageUrl;

    @Builder
    public Novel(final String title, final NovelCategory novelCategory, final String imageUrl) {
        this.title = title;
        this.novelCategory = novelCategory;
        this.imageUrl = imageUrl;
    }


//    public int getLikeNum() {
//        return essayLikes.size();
//    }

}
