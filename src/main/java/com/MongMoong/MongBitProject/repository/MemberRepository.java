package com.MongMoong.MongBitProject.repository;

import com.MongMoong.MongBitProject.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/*
MongoRepository 인터페이스를 상속하는 사용자 정의 Repository는 @Repository 어노테이션을 따로 붙이지 않아도 Spring에서 자동으로 Bean으로 등록된다.
이는 Spring Data MongoDB가 제공하는 기능 중 하나로, Spring Data MongoDB는 내부적으로 인터페이스를 구현하여 프록시 객체를 만들고 이를 Bean으로 등록한다.
 */
public interface MemberRepository extends MongoRepository<Member, String> {
    // Spring Data MongoDB는 메서드 이름을 분석하여 적절한 쿼리를 자동으로 생성하므로 메서드 이름만 정확하게 지정하면 된다.
    Optional<Member> findByKakaoId(Long kakaoId);
    Optional<Member> findByUsername(String username);
}
