package com.example.demo.domain.essay.presentation;

import com.example.demo.domain.essay.presentation.dto.request.CreateEssayCommentRequest;
import com.example.demo.domain.essay.presentation.dto.request.CreateEssayRequest;
import com.example.demo.domain.essay.presentation.dto.request.UpdateEssayRequest;
import com.example.demo.domain.essay.presentation.dto.response.EssayCommentInfoDto;
import com.example.demo.domain.essay.presentation.dto.response.EssayResponse;
import com.example.demo.domain.essay.service.EssayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/essays")
@RequiredArgsConstructor
@RestController
public class EssayController {

    private final EssayService essayService;

    @PostMapping
    public EssayResponse createEssay(@RequestBody CreateEssayRequest essayRequest) {
        return essayService.createEssay(essayRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable("id") Long essayId) {
        essayService.deleteEssay(essayId);
    }

    @PatchMapping("/{id}")
    public EssayResponse updateEssay(
            @PathVariable("id") Long reservationId,
            @RequestBody UpdateEssayRequest updateEssayRequest) {

        return essayService.updateEssay(reservationId, updateEssayRequest);
    }

    @GetMapping("/{id}")
    public EssayResponse getEssayDetail(@PathVariable("id") Long essayId) {
        return essayService.getEssayDetail(essayId);
    }

    @GetMapping
    public Slice<EssayResponse> getEssays(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        PageRequest pageRequest = PageRequest.of(page,size, Sort.Direction.DESC,"createdDate");
        return essayService.findAllEssay(pageRequest);
    }

    @GetMapping("/my")
    public Slice<EssayResponse> getMyEssays(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        PageRequest pageRequest = PageRequest.of(page,size, Sort.Direction.DESC,"createdAt");
        return essayService.findAllMyEssay(pageRequest);
    }


    @PostMapping("/{id}/like")
    public EssayResponse likeEssay(@PathVariable("id") Long essayId) {
        return essayService.likeEssay(essayId);
    }

    @DeleteMapping("/{id}/like")
    public EssayResponse unlikeEssay(@PathVariable("id") Long essayId) {
        return essayService.unlikeEssay(essayId);
    }

    @GetMapping("comment/{essayId}")
    public Slice<EssayCommentInfoDto> getEssayComment(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        PageRequest pageRequest = PageRequest.of(page,size, Sort.Direction.DESC,"createdDate");
        return essayService.findAllEssayComment(pageRequest);
    }

    @PostMapping("comment/{essayId}")
    public void createEssayComment(
            @PathVariable("essayId") Long essayId,
            @RequestBody CreateEssayCommentRequest createEssayCommentRequest) {

        essayService.createEssayComment(essayId, createEssayCommentRequest);
    }

    @DeleteMapping("comment/{commentId}")
    public void deleteEssayComment(@PathVariable("commentId") Long commentId) {
        essayService.deleteEssayComment(commentId);
    }

    @GetMapping("/draft")
    public EssayResponse getDraftEssay() {
        return essayService.getEssayDraft();
    }

    @PostMapping("/draft")
    public EssayResponse createDraftEssay(@RequestBody CreateEssayRequest createEssayRequest) {
        return essayService.createEssayDraft(createEssayRequest);
    }

}
