package com.example.demo.domain.image.presentation;


import com.example.demo.domain.image.presentation.dto.UploadImageResponse;
import com.example.demo.domain.image.service.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
@RestController
@Slf4j
public class ImageController {

    private final ImageUtils imageUtils;

    @PostMapping("/upload")
    public UploadImageResponse uploadImage(
            @RequestPart MultipartFile file) {
        log.info("=======================Image_upload===========================");
        log.info("==============================================================");
        log.info("file = {}",file);
        return imageUtils.uploadImage(file);
    }
}
