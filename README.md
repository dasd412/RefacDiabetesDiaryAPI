# __Diabetes Diary API Remake__
## 포트폴리오 리메이크 

### 진행 상황 ###

+ 데이터 재설계 및 JPA 적용
  + DB 스키마 재설계
  + JPA 연관관계 재맵핑
  + JPA Repository 테스트 코드 작성 및 수행
  + 서비스 레이어 생성
  + JPQL @Query 코드 제거 후, QueryDSL 적용. 기존 테스트를 수행하여 정상 작동하는 지 확인하며 진행
  + 컨트롤러 레이어 생성 
  + MockMvc 를 활용하여 컨트롤러 레이어 테스트 수행 
  + n+1 문제 최적화 수행
  + 삭제 연산 최적화 수행 [벌크 연산 처리]
  + MySql 연동 완료
  + 삽입 연산 최적화 수행
  

+ 스프링 시큐리티
  + 일반 회원가입과 로그인 구현
  + OAuth 로그인 및 회원 가입 구현
  + 기존 도메인 테스트에 스프링 시큐리티 적용 [관리자만 접근 가능하도록 변경]
  + 도메인 컨트롤러 매핑 url 에서 작성자 정보 제거하고 세션으로 판단하기
  + 아이디 찾기 , 비밀번호 찾기 구현(예정)


+ 테스트 커버리지 
  + (2021-12-24 refactoringAPI.controller 패키지 기준)
    + 클래스 커버리지 93% (29/31)
    + 메소드 커버리지 83% (178/214)
    + 라인 커버리지 70% (396/562)

  + (2021-12-24 refactoringAPI.domain 패키지 기준)
    + 클래스 커버리지 100% (18/18)
    + 메소드 커버리지 84% (110/130)
    + 라인 커버리지 82% (274/332)