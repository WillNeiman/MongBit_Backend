package com.MongMoong.MongBitProject.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Document("Member")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    /*
    MongoDB의 @Id 어노테이션의 타입은 ObjectId
    그러나 ObjectId는 MongoDB에서 자동으로 생성되는 12바이트의 고유 식별자이다.
    이를 문자열 형태로 표현하기 위해 String으로 타입을 지정한다.
    ObjectId는 MongoDB에서 생성된 도큐먼트를 고유하게 식별하기 위한 값으로,
    일반적으로 고유성과 인덱싱 측면에서 좋은 성능을 제공한다.
     */
    @Id
    private String id;
    private Long kakaoId;
    private String username;
    // password = kakaoId + ADMIN_TOKEN
    private String password;
    private String email;
    private MemberRole role;
    private LocalDateTime registDate;
    private String thumbnailImage;

    public Member(Long kakaoId, String username, String password, String email, MemberRole role, String thumbnailImage){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.kakaoId = kakaoId;
        this.registDate = LocalDateTime.now();
        this.thumbnailImage = thumbnailImage;
    }

    public List<GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

}
