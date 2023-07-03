# MongBit Project

### [프로젝트 개요]
- jwt 인증을 기반으로 카카오 로그인, 공유하기를 지원하는 심리테스트 플랫폼
- 카카오톡 소셜 로그인 기능을 도입하여 로그인 정보를 관리하고, 카카오톡 공유하기 기능을 사용할 수 있게 한다.
- 각 테스트마다 사용자 경험을 해치지 않는 선에서 쿠팡 광고배너를 배치하여 10원짜리 한 장이라도 건져본다.
###프로젝트 기간
- 2023/06/06 ~ 2023/06/27 (3주)
### [몽뭉이 크루] (ORDER BY birthday)
- 김송현 : front-end / react / git-hub
- 임건재 : back-end / java / database / deployment / PM
- 김정은 : back-end / java / api developement / QA
- 안혜지 : back-end / java / api developement / QA
## **기술스택**
**Frontend** : html5, css, JavaScript, React
**Backend** :
- 언어: Java (버전 11)
- 프로젝트 관리 도구 : Gradle-Groovy
- 웹 프레임워크 : **Spring Boot** (버전 2.7.12)
- 패키징 형식 : Jar
- 데이터베이스 : **MongoDB Atlas**
- 클라우드 서비스 : **AWS EC2**, Koyeb
- 클라우드 스토리지 : ImageBB
- 의존성 : Spring Web, Lombok, Spring Boot DevTools, Spring Data MongoDB, Spring Security, **OAuth2 Client**, Json, Spring AOP, Spring Test, Swagger, **Jwt**, HttpClient, Httpmime
- **아키텍쳐 : RESTful API**
- VCS : Git, Github, Git Kraken
## 작업 영역
- 기획 (Figma)
    - 프로젝트 설계
    - 프로젝트 스펙 선정
    - 화면설계. Design, Wireframe, Prototype 제작
- Backend (Java, Spring Boot)
    - 핵심 비즈니스 로직 구현
    - OAuth (Kakao), 로그인 관련 API
    - 좋아요, 댓글 API 구현
    - JWT 인가/인증
    - Security Config
    - Web Config 설정, CORS설정
    - Swagger (API 명세)
    - Interceptor, Exception Handler, Aspect로 예외 핸들링
- Database (MongoDB Atlas)
    - Database JPA-Oracle 기반 설계 → MongoDB Atlas로 재설계
    - Document 구조 설계 및 배포
- Backend Test Server Deploy (Koyeb)
    - Koyeb 플랫폼을 사용하여 백엔드 애플리케이션 실시간 통합 및 배포
- Backend Production Server Deploy (AWS)
    - AWS EC2 인스턴스 → 프로덕션 API 서버 배포
    - 가비아 → [mongbit.site](http://mongbit.site) 도메인 구매
    - AWS Route 53 → DNS 호스팅
    - AWS ACM → SSL/TLS 인증서 발급
    - 로드밸런서 → HTTPS 통신
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
## 기획, 팀 빌딩

**초안은 SSR이었다.**

프론트엔드와의 협업 기회가 전혀 없는 학원 환경 상 템플릿 엔진을 사용해야 했다.

- 실제 서비스 개발 현장과 유사한 환경(프론트/백 이원화) 속에서 개발하고 싶었음. 개발자 모임에서 프론트엔드 경력자를 설득해 프로젝트 편입
- **기존의 컨트롤러, Thymeleaf 템플릿 엔진을 기술 스택에서 제외하고 RESTful API와 CSR로 피봇**

**디자인 요소가 많다**

- 퍼블리싱 오버헤드를 줄이고자 로고는 그림판으로 직접 제작
- UI 디자인 생산성을 높이기 위해 Material Design Icons 플러그인 도입
- Export an SVG 기능으로 디자인 요소를 빠르게 퍼블리싱에 적용

**깃허브 협업 경험 X**

구성원 중 누구도 깃허브를 제대로 다룰 줄 몰랐다.

- 프론트엔드 경력자에게 팀의 상황을 설명하고 헬프요청, 깃 허브 협업 플로우 및 GUI 기반 깃 관리 툴인 GitKraken 사용법을 모든 팀원이 교육받게 함. ~~(만세)~~
- **master, dev, feature의 계층구조를 갖는 워크 플로우를 프로젝트 컨벤션으로 채택**
## 목표와 과제, 트러블슈팅

**OAuth2 로그인**

사용자의 진입장벽은 낮을 수록 좋음

회원관리는 하고 싶은데, 가입 절차는 없애고 싶었음

- 카카오 디벨로퍼에 게시된 공식문서를 읽고 OAuth2 인증의 개념도를 보며 작동 원리 분석
- Spring OAuth2 의존성을 이용하면 인가 절차를 매우 간단히 수행할 수 있지만 직접 코드로 작성
- 인가 코드를 받고, redirect uri에 인가 코드를 담아 엑세스 토큰을 받고, 엑세스 토큰으로 사용자 정보를 불러오는구나! 네이버는 한 스텝 짧지만, 우리 목적은 카카오니까!

**JWT**

서버의 세션 관리 과정의 번거로움을 없애고 싶었고, 보안은 유지하고 싶었음

- JWT를 구현하기 위해 10여 개의 블로그 포스팅 학습
- OAuth2 로그인 시 시큐리티 컨텍스트에 저장된 사용자 정보를 불러와 HMAC256 알고리즘으로 서명하는 과정을 작은 단위의 메소드로 분할하여 작동 구조를 파악하고 통합하여 완성
- HTTP 통신에 대한 이해가 많이 부족해 생성한 토큰을 어떻게 전달할지 오래 고민. 프론트엔드 담당자에게 HTTP 응답 방식에 대한 가이드라인을 배우고 응답 헤더에 담아 보내는 것이 가장 이상적임을 배움

**RESTful API**

프로젝트 초반에 개발한 API는 가독성이 안좋고 응답은 일관됐으며 GET과 POST 뿐

프론트는 명세를 일일이 물어봐야 했고, 커뮤니케이션 자체가 비용이 됨

- RESTful한 것이 무엇인지 고찰하며 기존의 API를 재검토
- 클라이언트는 사용자 인터페이스와 관련된 기능을 담당
- 서버는 비즈니스 로직과 데이터 관리를 담당
- HTTP 메소드를 CRUD 목적에 맞게 재설계
- 어떤 처리가 이루어졌는지 상황에 따른 응답코드를 전송할 수 있도록 컨벤션을 정의하고, 팀에 REST API 개발 규칙 공유
- URI 설계를 리소스 중심, 계층 구조로 다시 표현
- **자주 호출되며 변동이 적은 서비스 로직에 Caching을 적용하여 불필요한 쿼리 억제**

**Swagger**

API 담당자가 각자 깃허브에 명세를 작성하다보니 양식은 제멋대로에, 파라미터 설명을 누락하는 일이 발생.

- **“명세서를 일일이 손으로 쓰는 게 정말 맞나? 이 정도 불편은 나만 겪은 게 아니다. 누군가는 자동화시켰을 것이다.”** 라는 생각으로 정보 탐색, Swagger를 채택
- localhost 에서만 오픈되는 2차 문제 발생, 개발 서버 url을 통해 접속하여 모든 팀원이 간단히 명세서를 작성하고 정보를 공유할 수 있도록 개선
- 모든 팀원이 명세를 작성할 수 있도록 @Operation 어노테이션 작성 가이드 전파

**프론트/백 서버 이원화**

로컬에서만 돌아가는 프로젝트 그만! 실제로 PC웹, 모바일에서 접속 가능한 서비스를 만들고 싶음.

- Vercel과 달리 동적 페이지를 배포할 수 있는 무료 서비스로, 예전에 지뢰밟기 게임을 배포할 때 사용한 Koyeb을 활용하기로 함
- 처음에는 React앱과 스프링 프로젝트를 하나로 묶어 배포하려고 했으나 Koyeb에서 빌드를 못하는 문제 발생!
- 프론트엔드 서버와 백엔드 서버를 나눠서 배포하면 어떨까?
- **서버 이원화 후 프론트와 백 모두 24시간 배포상태 유지함**
- **그러나 평균 응답 속도가 2500ms ~ 3500ms로 굉장히 느렸음**

**CORS**

프론트와 백엔드 서버를 이원화한 직후 발생한 문제

이 프로젝트가 아니었다면 입사하는 순간까지 그 존재를 몰랐을지도 모름

- Cross Origin Resources Sharing(교차 출처 자원 공유) 개념 학습
- 서로 다른 도메인 간의 리소스 공유는 보안원칙 상 금지되는 점 파악
- WebMvcConfigurer 인터페이스를 구현하여 addCorsMappings 메소드를 재정의하고, 로컬 서버 및 배포 서버에 대한 CORS 제한을 해제

**예외처리**

Interceptor에서 JWT유효성을 검증하는데 이 또한 Exception Handler가 예외를 가로챌 것으로 기대하고 개발했다가 문제 발생

- 스프링 MVC의 Interceptor는 컨트롤러 메소드가 실행되기 전에 요청을 가로채기 떄문에 컨트롤러 메소드 실행 중의 예외를 처리하는 핸들러는 인터셉터의 예외를 포착하지 못하는 원리 파악
- 인증 및 인가에 관한 동작에 한에 Interceptor가 직접 예외를 관리하도록 수정했으나 인터셉터는 WebMvcConfigurer보다 먼저 실행되어 CORS 문제 재발생!
- CorsFilter를 구현, 인터셉터보다 앞에 배치하여 문제 해결
- 이후, 인터셉터 대신 커스텀 Annotation 기반으로 컨트롤러에서 JWT를 받아 API별로 처리하는 방식으로 개선

**Database**

실제로 서비스 배포가 최종 목표였던 만큼, 배포 상태를 유지하며 간단히 접근할 수 있는 Database가 필요했음

그러나 AWS RDS 서비스에 대한 이해가 낮았으며, RDBMS는 배포 절차가 번거로웠음

- JPA는 학습했으나 MongoDB는 사용해본 적이 없었음
- MongoDB Atlas를 배우고 테스트할 수 있는 간단한 미니 프로젝트를 만들어 작동원리를 이해하는 시간을 가짐
- 데드라인, 팀 생산성, JPA와 다른 스펙(Dirty Checking, Cascading 등), RDS 학습 비용 등 장단점을 고려했을 때 우리의 프로젝트는 MongoDB Atlas 서비스를 이용할 가치가 충분하다고 판단하고 팀원 의견 취합 → 전환하기로 결정
- 그러나 데이터 누락이라는 개념이 없는 MongoDB와 Java의 강타입 특성 사이에서 빈번하게 NPE가 발생하는 문제도 발견. 참조가 발생되는 Document에 한해 JPA의 캐스케이딩을 수작업으로 구현하여 해결.

**AWS EC2**

백엔드 api 호출하면 응답속도가 1.5초 ~ 3초 가까이 걸리는 문제가 지속됨

처음에는 koyeb의 설정 문제를 의심하고 프리티어 → 유료 요금제까지 올려봤으나 해결되지 않음

- 클라우드 서비스를 제공하는 플랫폼의 region이 레이턴시에 영향을 미칠 것이라 추론함. Koyeb이 지`원하는 region이 독일, 미국 뿐이었다!
- 대한민국 리전을 지원하는 여러 클라우드 서비스 탐색
- Vultr와 EC2 중 가장 대중적이고 정보 접근성이 높은 EC2 선택
- 스프링 부트 프로젝트 배포를 성공한 블로그 사례들을 검색해보며 배포 순서 학습
- FileZilla, OpenSSH 등 인스턴스와 상호작용 할 수 있는 프로그램 사용법 학습 후 배포 성공
- **그러나 로컬에서는 통신이 되지만 클라이언트 서버와 통신이 안되는 문제가 여전히 남아있었다!**

**HTTPS**

클라이언트 서버는 HTTPS를 쓰는 Vercel을 사용했으나 AWS가 제공하는 EC2 인스턴스는 HTTP를 사용, 프로토콜이 맞지 않아 통신이 불가함

- 가비아에서 저렴한 도메인 구매
- AWS Certificate Manager(ACM)를 사용하여 구매한 도메인에 대한 SSL/TLS 인증서를 발급. 이 인증서는 HTTPS 통신을 위한 암호화 정보를 제공.
- AWS의 Route 53 DNS 서비스를 사용하여 가비아에서 구매한 도메인을 AWS EC2 인스턴스의 IP 주소로 매핑함. (DNS 호스팅)
- AWS 로드밸런서를 사용하여 ACM에서 발급받은 SSL/TLS 인증서를 적용하고, 인바운드 443(HTTPS) 요청을 EC2 인스턴스로 전달. 이렇게 하여 프론트엔드와의 HTTPS 통신을 가능하게 함.
- **응답속도 2500~3500ms → 18~22ms로 개선**
