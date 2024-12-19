package com.example.demo.service;

import com.example.demo.dto.ProductAddDto;
import com.example.demo.entity.ProductEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadImageFileService {
    String uploadImage(MultipartFile file) throws IOException;
    ProductEntity add(ProductAddDto productAddDto,String imageUrl) throws IOException;
}
