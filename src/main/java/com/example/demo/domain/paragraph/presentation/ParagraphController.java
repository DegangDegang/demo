package com.example.demo.domain.paragraph.presentation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.paragraph.presentation.dto.request.ParagraphUpdateRequest;
import com.example.demo.domain.paragraph.presentation.dto.response.ParagraphDetailResponse;
import com.example.demo.domain.paragraph.service.ParagraphService;
import com.example.demo.domain.user.domain.User;
import com.example.demo.global.utils.user.UserUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/novels/{novelId}/paragraphs")
@RequiredArgsConstructor
public class ParagraphController {

	private final ParagraphService paragraphService;
	private final UserUtils userUtils;

	@PostMapping
	public ParagraphDetailResponse startWriteParagraph(@PathVariable Long novelId) {
		User user = userUtils.getUserFromSecurityContext();
		return paragraphService.startWriteParagraph(novelId, user);
	}

	@PatchMapping("/{paragraphId}")
	public ParagraphDetailResponse updateParagraphContent(@PathVariable Long paragraphId,
		@RequestBody ParagraphUpdateRequest paragraphUpdateRequest) {
		User user = userUtils.getUserFromSecurityContext();
		return paragraphService.updateParagraphContent(paragraphId, paragraphUpdateRequest, user);
	}

	@PostMapping("/{paragraphId}")
	public ParagraphDetailResponse endWriteParagraph(@PathVariable Long paragraphId) {
		User user = userUtils.getUserFromSecurityContext();
		return paragraphService.endWriteParagraph(paragraphId, user);
	}

	@GetMapping("/{paragraphId}")
	public ParagraphDetailResponse readParagraph(@PathVariable Long paragraphId) {
		User user = userUtils.getUserFromSecurityContext();
		return paragraphService.readParagraph(paragraphId, user);
	}

	@GetMapping
	public Slice<ParagraphDetailResponse> getParagraphs(@PathVariable Long novelId, @PageableDefault Pageable pageable) {
		User user = userUtils.getUserFromSecurityContext();
		return paragraphService.getParagraphs(novelId, pageable, user);
	}

	@GetMapping("/{writing}")
	public Slice<ParagraphDetailResponse> getParagraphsWriting(@PathVariable Long novelId, @PageableDefault Pageable pageable) {
		User user = userUtils.getUserFromSecurityContext();
		return paragraphService.getParagraphsWriting(novelId, pageable, user);
	}

	@GetMapping("/user/{userId}")
	public Slice<ParagraphDetailResponse> getParagraphsUser(@PathVariable Long userId, @PageableDefault Pageable pageable) {
		User user = userUtils.getUserFromSecurityContext();
		return paragraphService.getParagraphsUserNotAdopted(userId, pageable, user);
	}

	@PostMapping("{paragraphId}/like")
	public ParagraphDetailResponse likeParagraph(@PathVariable Long paragraphId) {
		User user = userUtils.getUserFromSecurityContext();
		return paragraphService.like(paragraphId, user);
	}

	@DeleteMapping("{paragraphId}/like")
	public ParagraphDetailResponse unlikeParagraph(@PathVariable Long paragraphId) {
		User user = userUtils.getUserFromSecurityContext();
		return paragraphService.unlike(paragraphId, user);
	}

}
