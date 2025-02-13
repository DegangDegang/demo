package com.example.demo.domain.novel.service;


import com.example.demo.domain.novel.Novel;
import com.example.demo.domain.novel.NovelCategory;
import com.example.demo.domain.novel.Paragraph;
import com.example.demo.domain.novel.repo.NovelRepository;
import com.example.demo.domain.novel.repo.ParagraphRepository;
import com.example.demo.domain.user.domain.User;
import com.example.demo.domain.user.domain.repository.UserRepository;
import com.example.demo.global.utils.user.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class NovelService {

    private final UserUtils userUtils;
    private final NovelRepository novelRepository;
    private final ParagraphRepository paragraphRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createNovel() {

        User user = User.builder()
                .nickname("ttee")
                .build();

        userRepository.save(user);

        Novel novel = Novel.builder()
                .title("test")
                .novelCategory(NovelCategory.FANTASY)
                .imageUrl("url-adw")
                .build();

        novelRepository.save(novel);
        Paragraph build = Paragraph.builder()
                .novel(novel)
                .user(user)
                .build();

        paragraphRepository.save(build);

    }


}
