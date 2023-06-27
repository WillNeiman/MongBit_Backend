package com.MongMoong.MongBitProject.config;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public static void main(String[] args) {
        System.out.println(generateNewSecretKey());
    }

    public static String generateNewSecretKey() {
        // 24바이트 길이의 랜덤 바이트 배열을 생성
        byte[] randomBytes = new byte[24];
        //Base64Url 인코딩하여 문자열로 변환
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
