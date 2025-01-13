package com.example.demo.domain.image.presentation;


import com.example.demo.domain.image.presentation.dto.UploadImageResponse;
import com.example.demo.domain.image.service.ImageService;
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

    private final ImageService imageService;

    @PostMapping("/upload")
    public UploadImageResponse uploadImage(
//            @Parameter(name = "file",
//                    description = "multipart/form-data 형식의 이미지를 input으로 받습니다. 이때 key 값은 file 입니다.",
//                    required = true)
            @RequestPart MultipartFile file) {
        log.info("=======================Image_upload===========================");
        log.info("==============================================================");
        log.info("file = {}",file);
        return imageService.uploadImage(file);
    }
}
