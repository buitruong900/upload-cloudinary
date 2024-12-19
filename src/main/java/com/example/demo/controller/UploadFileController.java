package com.example.demo.controller;

import com.example.demo.dto.ProductAddDto;
import com.example.demo.entity.ProductEntity;
import com.example.demo.service.UploadImageFileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import java.io.IOException;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")

public class UploadFileController {
    private final Logger logger = LoggerFactory.getLogger(UploadFileController.class);
    @Autowired
    private UploadImageFileService uploadImageFileService;


    @PostMapping("/add")
    public ProductEntity uploadImage(
            @RequestPart("data") ProductAddDto productAddDto,
            @RequestPart("file") MultipartFile file
    ) throws IOException {
        logger.info("Received Product Data: {}", productAddDto);
        logger.info("Received File: {}", file.getOriginalFilename());
        String uploadedImageUrl = uploadImageFileService.uploadImage(file);
        return uploadImageFileService.add(productAddDto, uploadedImageUrl);
    }

}
