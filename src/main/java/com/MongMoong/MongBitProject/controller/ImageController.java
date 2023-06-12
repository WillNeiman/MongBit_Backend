package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception{
        return "asdasdasd";
//        System.out.println("/upload 실행");
//        return imageService.uploadImageToImgBB(file);
    }
}
