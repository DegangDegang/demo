package com.example.demo.domain.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.example.demo.domain.image.exception.BadFileExtensionException;
import com.example.demo.domain.image.exception.FileEmptyException;
import com.example.demo.domain.image.exception.FileOversizeException;
import com.example.demo.domain.image.exception.FileUploadFailException;
import com.example.demo.domain.image.presentation.dto.UploadImageResponse;
import com.example.demo.global.utils.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ImageUtilsImpl implements ImageUtils{

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.base-url}")
    private String baseUrl;

    private final AmazonS3 amazonS3;

    @Override
    public UploadImageResponse uploadImage(MultipartFile file) {
        String url = upload(file);
        return new UploadImageResponse(url);
    }

    private String upload(MultipartFile file) {

        if (file.isEmpty() && file.getOriginalFilename() != null){
            throw FileEmptyException.EXCEPTION;
        }

        if (file.getSize() / (1024 * 1024) > 10) {
            throw FileOversizeException.EXCEPTION;
        }

        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null) {
            throw FileEmptyException.EXCEPTION;
        }

        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        if (!(ext.equals("jpg")
                || ext.equals("HEIC")
                || ext.equals("jpeg")
                || ext.equals("png")
                || ext.equals("heic"))) {
            throw BadFileExtensionException.EXCEPTION;
        }

        String randomName = UUID.randomUUID().toString();
        String fileName = SecurityUtils.getCurrentUserId() + "|" + randomName + "." + ext;

        try {
            ObjectMetadata objMeta = new ObjectMetadata();
            byte[] bytes = IOUtils.toByteArray(file.getInputStream());
            objMeta.setContentType(file.getContentType());
            objMeta.setContentLength(bytes.length);
            amazonS3.putObject(
                    new PutObjectRequest(bucket, fileName, file.getInputStream(), objMeta)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw FileUploadFailException.EXCEPTION;
        }
        return baseUrl + "/" + fileName;
    }

    @Override
    public void delete(String profilePath) {
        String objectName = getBucketKey(profilePath);
        amazonS3.deleteObject(bucket, objectName);
    }

    public String getBucketKey(String profilePath){
        return profilePath.substring(profilePath.lastIndexOf('/') + 1);
    }
}
