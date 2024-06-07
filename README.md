### Architecture

요청을 받는 컨트롤러 레이어    
Controller에서는 Service 호출과 Exception 처리만을 담당한다  
Controller에서의 비즈니스 로직 구현은 최대한 피한다.  
|  
|  
요청에 대한 응답 중간과정인 서비스 레이어  
|  
|  
서비스레이어 에서 필요할 때 데이터 베이스 접근 레이어  

- 중간마다 주고받는 데이터는 DTO 로 모든 데이터를 주고받음
DTO 설계할 때 테이블에 알맞도록 설계해야함

컨트롤러 -> 
서비스 ->
DAO -> 



참고  
https://cloud.google.com/files/apigee/apigee-web-api-design-the-missing-link-ebook.pdf
<br>

메소드와 클래스는 하나의 목적만을 위해 생성한다.  
도메인명의 Service 생성은 피한다.  
Order 라는 도메인이 있을 때 OrderService 로 만드는 것은 피한다.  
도메인 관련 기능을 세분화하여 Service를 만든다(ex. OrderRegisretService, OrderStatusService .....)  

### To do

- [ ] 기능단위 개발
  - [ ] 회원가입
  - [ ] 로그인
  - [ ] 회원정보 수정
  - [ ] 회원 패스워드 수정
  - [ ] 회원탈퇴
- [ ] 기능단위 테스트
- [ ] 깃 브랜치 전략 적용
- [ ] jwt 적용 
- [ ] 유저 관련 기능은 JDBCTemplate
- [ ] 게시판 기능은 JPA + QueryDSL
- [ ] 모든 기능 구현 후 postDTO man 으로 API 테스트
- [ ] 스프링 전용 프론트 레포 복제해서 리팩토링 ㄱㄱ (리액트도?)