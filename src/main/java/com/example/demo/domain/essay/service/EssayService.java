package com.example.demo.domain.essay.service;

import com.example.demo.domain.essay.domain.Essay;
import com.example.demo.domain.essay.domain.EssayComment;
import com.example.demo.domain.essay.domain.EssayLike;
import com.example.demo.domain.essay.domain.repository.EssayCommentRepository;
import com.example.demo.domain.essay.domain.repository.EssayLikeRepository;
import com.example.demo.domain.essay.domain.repository.EssayRepository;
import com.example.demo.domain.essay.exception.*;
import com.example.demo.domain.essay.presentation.dto.request.CreateEssayCommentRequest;
import com.example.demo.domain.essay.presentation.dto.request.CreateEssayRequest;
import com.example.demo.domain.essay.presentation.dto.request.UpdateEssayRequest;
import com.example.demo.domain.essay.presentation.dto.response.EssayCommentInfoDto;
import com.example.demo.domain.essay.presentation.dto.response.EssayResponse;
import com.example.demo.domain.user.domain.User;
import com.example.demo.global.utils.security.SecurityUtils;
import com.example.demo.global.utils.user.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class EssayService {

    private final EssayRepository essayRepository;
    private final UserUtils userUtils;
    private final EssayLikeRepository essayLikeRepository;
    private final EssayCommentRepository essayCommentRepository;
    private final SecurityUtils securityUtils;

    @Transactional
    public EssayResponse createEssay(CreateEssayRequest createEssayRequest){

        User user = userUtils.getUserFromSecurityContext();

        return essayRepository.findDraftByUser(user.getId())
                .map(essay -> {
                    essay.updateEssay(createEssayRequest.toUpdateEssayDto());
                    essay.updateIsDraft();
                    return getEssayResponse(essay, user.getId());
                })
                .orElseGet(() -> {
                    Essay newEssay = makeEssay(createEssayRequest, user);
                    essayRepository.save(newEssay);
                    return getEssayResponse(newEssay,user.getId());
                });

    }

    @Transactional
    public void deleteEssay(Long essayId){

        User user = userUtils.getUserFromSecurityContext();

        Essay essay = queryEssay(essayId);

        essay.validUserIsHost(user.getId());

        essayRepository.delete(essay);
    }

    //방 상세정보
    public EssayResponse getEssayDetail(Long essayId) {

        Long currentUserId = securityUtils.getCurrentUserId();

        Essay essay = queryEssay(essayId);

        essay.validDraft();

        return getEssayResponse(essay,currentUserId);
    }

    @Transactional
    public EssayResponse updateEssay(Long essayId, UpdateEssayRequest updateEssayRequest) {

        Long currentUserId = securityUtils.getCurrentUserId();

        Essay essay = queryEssay(essayId);

        essay.validUserIsHost(currentUserId);

        essay.updateEssay(updateEssayRequest.toUpdateEssayDto());

        return getEssayResponse(essay,currentUserId);
    }

    @Transactional
    public EssayResponse createEssayDraft(CreateEssayRequest createEssayRequest) {
        User user = userUtils.getUserFromSecurityContext();

        return essayRepository.findDraftByUser(user.getId())
                .map(essay -> {
                    essay.updateEssay(createEssayRequest.toUpdateEssayDto());
                    return getEssayResponse(essay, user.getId());
                })
                .orElseGet(() -> {
                    Essay newEssay = makeEssayDraft(createEssayRequest, user);
                    essayRepository.save(newEssay);
                    return getEssayResponse(newEssay,user.getId());
                });
    }

    //방 상세정보
    public EssayResponse getEssayDraft() {

        Long currentUserId = securityUtils.getCurrentUserId();

        Essay essay = essayRepository.findDraftByUser(currentUserId)
                .orElseThrow(() -> EssayDraftNotFoundException.EXCEPTION);

        return getEssayResponse(essay,currentUserId);
    }

    public Slice<EssayResponse> findAllEssay(PageRequest pageRequest) {

        Long currentUserId = securityUtils.getCurrentUserId();

        Slice<Essay> sliceReservation = essayRepository.findByIsDraftFalseOrderByLastModifyAtDesc(pageRequest);

        return sliceReservation.map(
                essay -> getEssayResponse(essay,currentUserId));
    }

    public Slice<EssayResponse> findAllMyEssay(PageRequest pageRequest) {

        Long currentUserId = securityUtils.getCurrentUserId();

        Slice<Essay> sliceReservation = essayRepository.findAllMyEssay(currentUserId,pageRequest);

        return sliceReservation.map(
                essay -> getEssayResponse(essay,currentUserId));
    }

    @Transactional
    public EssayResponse likeEssay(Long essayId) {
        User user = userUtils.getUserFromSecurityContext();

        Essay essay = queryEssay(essayId);

        essay.validDraft();

        if (essayLikeRepository.existsByEssayAndUser(essay, user)) {
            throw EssayLikeAlreadyExistsException.EXCEPTION;
        }

        EssayLike essayLike = makeEssayLike(essay, user);

        essayLikeRepository.save(essayLike);

        return getEssayResponse(essay, user.getId());
    }

    @Transactional
    public EssayResponse unlikeEssay(Long essayId) {
        User user = userUtils.getUserFromSecurityContext();
        Essay essay = queryEssay(essayId);

        essay.validDraft();

        if (!essayLikeRepository.existsByEssayAndUser(essay, user)) {
            throw EssayLikeNotFoundException.EXCEPTION;
        }

        essayLikeRepository.deleteByEssayAndUser(essay, user);

        return getEssayResponse(essay, user.getId());
    }


    @Transactional
    public Slice<EssayCommentInfoDto> findAllEssayComment(PageRequest pageRequest) {

        Long currentUserId = securityUtils.getCurrentUserId();

        Slice<EssayComment> sliceEssayComments = essayCommentRepository.findSliceByOrderByCreatedAtDesc(pageRequest);

        return sliceEssayComments.map(essayComment ->
                new EssayCommentInfoDto(essayComment.getEssayCommentInfoVo(),essayComment.checkUserIsHost(currentUserId)));
    }

    @Transactional
    public void createEssayComment(Long essayId, CreateEssayCommentRequest createEssayCommentRequest) {

        User user = userUtils.getUserFromSecurityContext();

        Essay essay = queryEssay(essayId);

        essay.validDraft();

        EssayComment essayComment = makeEssayComment(user, essay, createEssayCommentRequest.getContent());

        essayCommentRepository.save(essayComment);
    }

    @Transactional
    public void deleteEssayComment(Long commentId) {

        Long currentUserId = securityUtils.getCurrentUserId();

        EssayComment essayComment = queryEssayComment(commentId);

        essayComment.validUserIsHost(currentUserId);

        essayCommentRepository.delete(essayComment);
    }

    public Slice<EssayResponse> search(String word, Pageable pageable){

        Long currentUserId = securityUtils.getCurrentUserId();

        Slice<Essay> essays = essayRepository.searchEssays(word, pageable);

        return essays.map(essay -> getEssayResponse(essay,currentUserId));
    }



    private Essay makeEssay(CreateEssayRequest createEssayRequest,User user){

        return Essay.builder()
                .user(user)
                .sentence(createEssayRequest.getSentence())
                .title(createEssayRequest.getTitle())
                .content(createEssayRequest.getContent())
                .isDraft(false)
                .build();
    }

    private Essay makeEssayDraft(CreateEssayRequest createEssayRequest,User user){

        return Essay.builder()
                .user(user)
                .sentence(createEssayRequest.getSentence())
                .title(createEssayRequest.getTitle())
                .content(createEssayRequest.getContent())
                .isDraft(true)
                .build();
    }

    private EssayComment makeEssayComment(User user,Essay essay, String content) {
        EssayComment essayComment = EssayComment.builder()
                .essay(essay)
                .user(user)
                .content(content)
                .build();

        essayComment.addMethod();

        return essayComment;
    }

    private EssayLike makeEssayLike(Essay essay, User user){

        EssayLike essayLike = EssayLike.builder()
                .essay(essay)
                .user(user)
                .build();

        essayLike.addMethod();

        return essayLike;
    }

    private EssayResponse getEssayResponse(Essay essay, Long currentUserId) {
        return new EssayResponse(
                essay.getEssayInfoVo(),
                essay.checkUserIsHost(currentUserId),
                essay.getLikeNum(),
                essay.getCommentNum(),
                checkLike(essay,currentUserId));
    }

    private boolean checkLike(Essay essay, Long currentUserId) {
        User user = userUtils.getUserById(currentUserId);
        return essayLikeRepository.existsByEssayAndUser(essay,user);
    }

    public Essay queryEssay(Long id) {
        return essayRepository
                .findById(id)
                .orElseThrow(()-> EssayNotFoundException.EXCEPTION);
    }

    private EssayComment queryEssayComment(Long id) {
        return essayCommentRepository
                .findById(id)
                .orElseThrow(()-> EssayCommentNotFoundException.EXCEPTION);
    }

}
