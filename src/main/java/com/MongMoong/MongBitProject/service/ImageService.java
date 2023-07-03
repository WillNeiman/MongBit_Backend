package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.aspect.AdminRequired;
import com.MongMoong.MongBitProject.config.ImgbbConfig;
import com.MongMoong.MongBitProject.dto.ImageUploadResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private int index = 0;
    private final ImgbbConfig imgbbConfig;

    @AdminRequired
    public String uploadImageToImgBB(MultipartFile file) throws Exception {
        String key = getNextKey();
        InputStream in = file.getInputStream();

        HttpPost post = new HttpPost("https://api.imgbb.com/1/upload");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("key", key);

        ContentType contentType = ContentType.create(file.getContentType());
        builder.addBinaryBody("image", in, contentType, file.getOriginalFilename());

        HttpEntity multipart = builder.build();
        post.setEntity(multipart);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(post);
        HttpEntity responseEntity = response.getEntity();

        String result = EntityUtils.toString(responseEntity);

        ObjectMapper mapper = new ObjectMapper();
        ImageUploadResponse uploadResponse = mapper.readValue(result, ImageUploadResponse.class);

        if (uploadResponse.getStatus_code() == 400 && uploadResponse.getError().getCode() == 100) {
            return uploadImageToImgBB(file); // 재귀적으로 본 메소드 다시 호출
        }

        return uploadResponse.getData().getUrl();
    }

    private String getNextKey() {
        List<String> keys = imgbbConfig.getKeys(); // ImageBB api key 가져오기
        if (index >= keys.size()) {
            index = 0;
        }
        return keys.get(index++);
    }
}
