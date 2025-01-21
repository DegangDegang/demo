package com.example.demo.domain.image.service;

import com.example.demo.domain.image.presentation.dto.UploadImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUtils {

    void delete(String objectName);

    UploadImageResponse uploadImage(MultipartFile file);

    
}
