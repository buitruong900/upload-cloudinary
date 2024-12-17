package com.example.demo.controller;

import com.example.demo.service.UploadImageFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UploadFileController {
    @Autowired
    private UploadImageFileService uploadImageFileService;
    @PostMapping("/upload/image")
    public String uploadImage(@RequestParam("file")MultipartFile file)throws IOException{
        return  uploadImageFileService.uploadImage(file);
    }

}
