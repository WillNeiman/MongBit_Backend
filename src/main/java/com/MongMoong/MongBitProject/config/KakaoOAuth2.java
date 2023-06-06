package com.MongMoong.MongBitProject.config;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
/*
여기서는 카카오 로그인을 통해 회원 정보를 가져오기 위해 2가지 기능을 수행한다.
1. getAccessToken
    Http 오브젝트를 만들고 body에 grant_type, client_id, redirect_uri, code(인가코드)를 담아 https://kauth.kakao.com/oauth/token로
    POST요청 후 응답 본문에서 AccessToken을 가져온다.
2. getUserInfoByToken
    1에서 받아온 AccessToken을 이번에는 https://kapi.kakao.com/v2/user/me로 POST 요청한다.
    응답 본문에서 필요한 회원 정보를 가져온다.

 */
@Component
public class KakaoOAuth2 {
    public KakaoUserInfo getUserInfo(String authorizedCode) {
        // 1. 인가코드 -> 액세스 토큰
        String accessToken = getAccessToken(authorizedCode);
        // 2. 액세스 토큰 -> 카카오 사용자 정보
        KakaoUserInfo userInfo = getUserInfoByToken(accessToken);

        return userInfo;
    }

    public String getAccessToken(String authorizedCode) {
        // HttpHeader 오브젝트 생성, HTTP 요청의 헤더 정보를 저장
        HttpHeaders headers = new HttpHeaders();
        // "Content-type" 헤더를 추가하여 요청의 본문 타입을 지정
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성, MultiValueMap 객체는 HTTP 요청의 본문 정보를 저장하는 데 사용
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // 파라미터를 추가하여 Kakao API에 전달될 요청 정보를 설정
        // grant_type은 "authorization_code"로 설정되어 인증 코드 교환을 요청
        params.add("grant_type", "authorization_code");
        params.add("client_id", "3245a5f9cb8303814aadbe1eb65b2e73");
        params.add("redirect_uri", "http://localhost:8080/login/oauth2/kakao/code");
        params.add("code", authorizedCode);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        RestTemplate rt = new RestTemplate();
        // HttpHeaders와 MultiValueMap을 결합
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // RestTemplate을 사용하여 HTTP POST 요청, 응답은 ResponseEntity<String> 형태로 받아옴
        // RestTemplate의 exchange() 메소드를 사용하여 Kakao OAuth 서버에 POST 요청
        ResponseEntity<String> response = rt.exchange(
                // 요청 URL에 전달할 헤더, 본문 및 응답을 받을 클래스 유형을 지정
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // JSON -> 액세스 토큰 파싱
        // ResponseEntity의 getBody() 메소드를 사용하여 응답 본문을 문자열 형태로 받아옴
        String tokenJson = response.getBody();
        // 가져온 JSON 형식의 문자열을 JSONObject로 변환
        JSONObject rjson = new JSONObject(tokenJson);
        // JSONObject에서 "access_token" 필드 값을 추출하여 액세스 토큰을 얻음
        String accessToken = rjson.getString("access_token");

        return accessToken;
    }

    public KakaoUserInfo getUserInfoByToken(String accessToken) {
        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        JSONObject body = new JSONObject(response.getBody());
        Long id = body.getLong("id");
        System.out.println("JSONObject body 내용보기 : "+ body.toString());
        String email = body.getJSONObject("kakao_account").getString("email");
        String nickname = body.getJSONObject("properties").getString("nickname");

        return new KakaoUserInfo(id, email, nickname);
    }
}