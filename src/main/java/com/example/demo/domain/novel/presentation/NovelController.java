package com.example.demo.domain.novel.presentation;


import com.example.demo.domain.novel.service.NovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/novel")
@RequiredArgsConstructor
@RestController
public class NovelController {

    private final NovelService novelService;


    @PostMapping("/create")
    public void createNovel() {
        novelService.createNovel();
    }





}
