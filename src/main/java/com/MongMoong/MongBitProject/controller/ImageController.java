package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    @Operation(summary = "ImageBB 플랫폼에 사진을 업로드하고 url을 반환합니다.", description = "MultipartFile 타입의 파일 데이터가 필요합니다.")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws Exception{
        System.out.println("/upload 실행");
        String imageUrl = imageService.uploadImageToImgBB(file);
        System.out.println("imageUrl = " + imageUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(imageUrl);
    }
}
