# MongBit Project

### [프로젝트 개요]
- jwt 인증을 기반으로 카카오 로그인, 공유하기를 지원하는 심리테스트 플랫폼
- 카카오톡 소셜 로그인 기능을 도입하여 로그인 정보를 관리하고, 카카오톡 공유하기 기능을 사용할 수 있게 한다.
- 각 테스트마다 사용자 경험을 해치지 않는 선에서 쿠팡 광고배너를 배치하여 10원짜리 한 장이라도 건져본다.

### [몽뭉이 크루] (ORDER BY birthday)
- 김송현 : front-end / react / git-hub
- 임건재 : back-end / java / database / deployment / PM
- 김정은 : back-end / java / api developement / QA
- 안혜지 : back-end / java / api developement / QA

### [기술 스택]
- 언어: Java (버전 11)
- 프로젝트 관리 도구: Gradle (Groovy 기반)
- 웹 프레임워크: Spring Boot (버전 2.7.12)
- 패키징 형식: Jar
- 의존성:
  - Spring Web: Spring의 웹 기능을 제공하는 모듈
  - Lombok: 자동화된 메서드 생성 및 코드 간소화를 위한 라이브러리
  - Spring Boot DevTools: 개발 시 자동 재시작 및 빠른 개발을 위한 도구
  - Spring Data MongoDB: MongoDB와의 상호작용을 위한 데이터 모듈
  - OAuth2 Client: 소셜로그인을 지원하는 클라이언트 라이브러리
  - Spring Security : 안을 처리하기 위한 스타터 의존성
  - Spring AOP : 횡단 관심사 분리, 주로 예외처리에 사용
  - Spring Test : 테스트 코드 작성
  - Spring DevTools : 개발 도구로서 개발 시 자동 재시작 등의 기능을 제공
  - Openapi : Spring Doc의 Swagger UI를 사용하여 API 문서를 생성하고 표시하기 위한 의존성
  - Jwt : JWT(JSON Web Token)를 다루기 위한 라이브러리
  - Json : JSON 데이터 처리를 위한 라이브러리
  - HttpClient : Apache HttpClient를 사용하여 HTTP 요청을 보내기 위한 라이브러리
  - HttpMime : Apache HttpClient에서 멀티파트(Multipart) 요청을 처리하기 위한 라이브러리
- 데이터베이스: MongoDB Atlas
- 배포 도구 : Koyeb, EC2
- 아키텍쳐 : RESTful API

### 추가 사항:
1. 클라우드 서비스: Koyeb, AWS EC2
 - 프로젝트 배포를 위해 Koyeb의 서비스를 활용
 - Koyeb의 느린 latency 극복을 위해 프로덕션 서버는 EC2를 도입하여 개선
2. 파일 저장: ImageBB
 - 이미지 저장을 위한 클라우드 스토리지
3. 쿠팡 파트너스: 임건재가 옛날에 만들어둔 계정 그대로 활용


### 화면 설계
- figma wire-frame
https://www.figma.com/file/C9D4w9U6uKwewR5MqdYBIA/Untitled?type=design&node-id=0%3A1&t=RmadoLPgs2ZPFFck-1
---
### 나의 작업
- 기획 (Figma)
    - 프로젝트 설계
    - 프로젝트 스펙 선정
    - 화면설계. Design, Wireframe, Prototype 제작
- Backend (Java, Spring Boot)
    - OAuth (Kakao), 로그인 관련 API
    - 좋아요, 댓글 API 구현
    - JWT
    - Security Config
    - Web Config 설정, CORS설정
    - Swagger (API 명세)
    - Interceptor, Exception Handler, Aspect로 예외 핸들링
    - Interceptor (인증, 인가)
- Database (MongoDB Atlas)
    - Database JPA-Oracle 기반 설계 → MongoDB Atlas로 재설계
    - Document 구조 설계 및 배포
- Backend Test Server Deploy (Koyeb)
    - Koyeb 플랫폼을 사용하여 백엔드 애플리케이션 실시간 통합 및 배포
- Backend Production Server Deploy (EC2)
    - AWS EC2 인스턴스를 사용하여 프로덕션 API 서버 배포
    - 가비아 도메인 구매 및 EC2 연동
    - HTTPS 통신을 위한 SSL/TLS 인증서 발급 및 EC2 인스턴스에 연동
---
### 트러블슈팅

**OAuth2 로그인**

사용자의 진입장벽은 낮을 수록 좋음

회원관리는 하고 싶은데, 가입 절차는 없애고 싶었음

- 카카오 디벨로퍼에 게시된 공식문서를 읽고 OAuth2 인증의 개념도를 보며 작동 원리 분석
- Spring OAuth2 의존성을 이용하면 인가 절차를 매우 간단히 수행할 수 있지만 직접 코드로 작성
- 인가 코드를 받고, redirect uri에 인가 코드를 담아 엑세스 토큰을 받고, 엑세스 토큰으로 사용자 정보를 불러오는 일련의 과정을 이해함

**JWT**

서버의 세션 관리 과정의 번거로움을 없애고 싶었고, 보안은 유지하고 싶었음

- JWT를 구현하기 위해 10여 개의 블로그 포스팅 학습
- OAuth2 로그인 시 시큐리티 컨텍스트에 저장된 사용자 정보를 불러와 HMAC256 알고리즘으로 서명하는 과정을 작은 단위의 메소드로 분할하여 작동 구조를 파악하고 통합하여 완성
- HTTP 통신에 대한 이해가 많이 부족해 생성한 토큰을 어떻게 전달할지 오래 고민. 프론트엔드 담당자에게 HTTP 응답 방식에 대한 가이드라인을 배우고 응답 헤더에 담아 보내는 것이 가장 이상적임을 배움

**RESTful API**

프로젝트 초반에 개발한 API는 가독성이 안좋고 응답은 일관됐으며 GET과 POST 뿐

프론트는 명세를 일일이 물어봐야 했고, 커뮤니케이션 자체가 비용이 됨

- RESTful한 것이 무엇인지 집요하게 고찰하며 기존의 API를 재검토
- HTTP 메소드를 CRUD 목적에 맞게 재설계
- 어떤 처리가 이루어졌는지 상황에 따른 응답코드를 전송할 수 있도록 컨벤션을 정의하고, 팀에 REST API 개발 규칙 공유
- URI 설계를 리소스 중심, 계층 구조로 다시 표현

**Swagger**

API 담당자가 각자 깃허브에 명세를 작성하다보니 양식은 제멋대로에, 파라미터 설명을 누락하는 일이 발생.

- **“이 정도 불편은 나만 겪은 게 아니다. 분명 API 명세 자동화 프레임워크가 있을 것이다.”** 라는 생각으로 정보 탐색, OpenAPI와 Swagger를 채택
- localhost 에서만 오픈되는 2차 문제 발생, 개발 서버 url을 통해 접속하여 모든 팀원이 간단히 명세서를 작성하고 정보를 공유할 수 있도록 개선
- 모든 팀원이 명세를 작성할 수 있도록 @Operation 어노테이션 작성 가이드 전파

**CORS**

프론트와 백엔드 서버를 이원화한 직 발생한 문제

이 프로젝트가 아니었다면 입사하는 순간까지 그 존재를 몰랐을지도 모름

- Cross Origin Resources Sharing(교차 출처 자원 공유) 개념 학습
- 서로 다른 도메인 간의 리소스 공유는 보안원칙 상 금지되는 점 파악
- WebMvcConfigurer 인터페이스를 구현하여 addCorsMappings 메소드를 재정의하고, 로컬 서버 및 배포 서버에 대한 CORS 제한을 해제

**예외처리**

Interceptor에서 JWT유효성을 검증하는데 이 또한 Exception Handler가 예외를 가로챌 것으로 기대하고 개발했다가 문제 발생

- 스프링 MVC의 Interceptor는 컨트롤러 메소드가 실행되기 전에 요청을 가로채기 떄문에 컨트롤러 메소드 실행 중의 예외를 처리하는 핸들러는 인터셉터의 예외를 포착하지 못하는 원리 파악
- 인증 및 인가에 관한 동작에 한에 Interceptor가 직접 예외를 관리하도록 수정
- 인터셉터는 WebMvcConfigurer보다 먼저 실행되어 CORS 문제 재발생!
- CorsFilter를 구현, 인터셉터보다 앞에 배치하여 문제 해결

**Database**

데이터베이스 설계가 기획과 변경되는 일이 잦음

실제로 서비스 배포가 최종 목표였던 만큼, 배포 상태를 유지하며 간단히 접근할 수 있는 Database가 필요했음

- JPA는 학습했으나 MongoDB는 사용해본 적이 없었음
- 그러나 AWS RDS 서비스에 대한 이해가 낮았으며, RDBMS는 배포 절차가 번거로웠음
- MongoDB Atlas를 배우고 테스트할 수 있는 간단한 미니 프로젝트를 만들어 작동원리를 이해하는 시간을 가짐
- 데드라인, 팀 생산성, JPA와 다른 스펙(Dirty Checking, Cascading 등), RDS 학습 비용 등 장단점을 고려했을 때 우리의 프로젝트는 MongoDB Atlas 서비스를 이용할 가치가 충분하다고 판단하고 팀원 의견 취합 → 만장일치
- 유연하게 엔티티를 수정할 수 있는 MongoDB 특성을 최대한 이용해 비즈니스 로직 개발에 있어 다양한 테스트와 적용이 가능했고, 결과적으로 팀 생산성 증가
