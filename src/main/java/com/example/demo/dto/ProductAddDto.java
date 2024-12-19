package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductAddDto {
    private String tenSanPham;
    private String moTa;
}
