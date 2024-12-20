package com.example.demo.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.dto.ProductAddDto;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.UploadImageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadImageFileServiceImpl implements UploadImageFileService {

    @Autowired
    private ProductRepository productRepository;

    private final Cloudinary cloudinary;

    @Override
    public ProductEntity add(ProductAddDto productAddDto,String imageUrl) throws IOException {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setTenSanPham(productAddDto.getTenSanPham());
        productEntity.setMoTa(productAddDto.getMoTa());
        productEntity.setImage(imageUrl);
        return productRepository.save(productEntity);
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        assert file.getOriginalFilename() != null;
        String publicValue = generatePublicValue(file.getOriginalFilename());
        log.info(publicValue);
        String extension = getFileName(file.getOriginalFilename())[1];
        log.info(extension);
        File fileUpload = convert(file);
        log.info(String.valueOf(fileUpload));
        cloudinary.uploader().upload(fileUpload, ObjectUtils.asMap("public_id",publicValue));
        cleanDisk(fileUpload);
        return  cloudinary.url().generate(StringUtils.join(publicValue, ".", extension));
    }

    private File convert(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File convFile = new File(System.getProperty("java.io.tmpdir"), fileName);
        try (InputStream is = file.getInputStream()) {
            Files.copy(is, convFile.toPath());
        }
        return convFile;
    }
    private void cleanDisk(File file) {
        try {
            log.info("file.toPath(): {}", file.toPath());
            Path filePath = file.toPath();
            Files.delete(filePath);
        } catch (IOException e) {
            log.error("Error");
        }
    }

    public String generatePublicValue(String originalName){
        String fileName = getFileName(originalName)[0];
        return StringUtils.join(UUID.randomUUID().toString(), "_", fileName);
    }

    public String[] getFileName(String originalName) {
        int dotIndex = originalName.lastIndexOf('.');
        if (dotIndex > 0) {
            return new String[]{
                    originalName.substring(0, dotIndex),
                    originalName.substring(dotIndex + 1)
            };
        }
        return new String[]{originalName, ""};
    }
}
