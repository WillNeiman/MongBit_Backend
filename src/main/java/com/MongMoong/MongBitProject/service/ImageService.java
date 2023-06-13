package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.dto.ImageUploadResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class ImageService {

    @Value("${imgbb.key}")
    private String API_KEY;

    public String uploadImageToImgBB(MultipartFile file) throws Exception {
        System.out.println("API_KEY = " + API_KEY);
        InputStream in = file.getInputStream();

        HttpPost post = new HttpPost("https://api.imgbb.com/1/upload");
        System.out.println("post = " + post);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("key", API_KEY);

        ContentType contentType = ContentType.create(file.getContentType());
        builder.addBinaryBody("image", in, contentType, file.getOriginalFilename());

        HttpEntity multipart = builder.build();
        post.setEntity(multipart);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(post);
        HttpEntity responseEntity = response.getEntity();

        System.out.println("responseEntity = " + responseEntity);
        String result = EntityUtils.toString(responseEntity);

        ObjectMapper mapper = new ObjectMapper();
        ImageUploadResponse uploadResponse = mapper.readValue(result, ImageUploadResponse.class);

        return uploadResponse.getData().getUrl();
    }
}
