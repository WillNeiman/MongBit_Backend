# MongBit Project

### [프로젝트 개요]
- MBTI 검사 및 다양한 심리테스트를 할 수 있는 웹 페이지 개발
- 카카오톡 소셜 로그인 기능을 도입하여 로그인 정보를 관리하고, 카카오톡 공유하기 기능을 사용할 수 있게 한다.
- 각 테스트마다 사용자 경험을 해치지 않는 선에서 쿠팡 광고배너를 배치하여 10원짜리 한 장이라도 건져본다.

### [몽뭉이 크루] (ORDER BY birthday)
김송현 : front-end / react / git-hub
임건재 : back-end / java / database / deployment / PM
김정은 : back-end / java / api developement / QA
안혜지 : back-end / java / api developement / QA

### [기술 스택]
- 언어: Java (버전 11)
- 프로젝트 관리 도구: Gradle (Groovy 기반)
- 웹 프레임워크: Spring Boot (버전 2.7.12)
- 패키징 형식: Jar
- 의존성:
- - Spring Web: Spring의 웹 기능을 제공하는 모듈
- - Lombok: 자동화된 메서드 생성 및 코드 간소화를 위한 라이브러리
- - Spring Boot DevTools: 개발 시 자동 재시작 및 빠른 개발을 위한 도구
- - Spring Data MongoDB: MongoDB와의 상호작용을 위한 데이터 모듈
- - OAuth2 Client: 소셜로그인을 지원하는 클라이언트 라이브러리
- 데이터베이스: MongoDB Atlas
- 배포 도구 : KOYEB

### 추가 사항:
1. 클라우드 서비스: Koyeb
 - 프로젝트 배포를 위해 Koyeb의 서비스를 활용
2. 파일 저장: Amazon S3 (아마존 웹 서비스의 클라우드 스토리지 솔루션)
 - 파일 저장을 위해 클라우드 스토리지로 Amazon S3를 사용할 예정
3. 쿠팡 파트너스: 임건재가 옛날에 만들어둔 계정 그대로 활용


### [화면 설계]
- figma wire-frame
https://www.figma.com/file/C9D4w9U6uKwewR5MqdYBIA/Untitled?type=design&node-id=0%3A1&t=RmadoLPgs2ZPFFck-1

### [기능 명세]
0. 계정 등록 및 로그인(KAKAO OAuth)
1. 메인 화면
2. 사이드바
3. 테스트 목록
4. 테스트 페이지
  - 테스트 시작
  - 테스트 진행
  - 테스트 결과 :지난 결과 보기가 가능하다. 보기 쉽게 날짜, 기간별 표?가 나타나면 더 좋을 것 같다.)
5. 좋아요
  - 테스트마다 좋아요 기능
6. 댓글
  - 테스트마다 댓글 기능
7. 공유하기
  - 테스트 공유하기
  - 테스트 결과 공유하기
8. 심리테스트 만들기
  - GUI로 구현하여 웹 상에서 심리테스트 등록이 가능하도록 만든다. 시간이 남으면 한다.(안할 가능성 90%)
9. MBTI 게시판을 따로 만들거나 5, 6 번과 통합.
  - 글 작성자 표기가 (당사이트에서 테스트한 결과) MBTI로 된다. MBTI별 게시글 검색이 가능하다. 시간이 남으면 한다.(안할 가능성 95%)
