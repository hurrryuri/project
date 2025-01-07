1. 필요한 라이브러리를 설정
2. application.properties
   서버, 데이터베이스, jpa, thymeleaf, 파일업로드, s3보안키
   소셜로그인(google, naver, kakao, github 등) 보안키,
   개발환경
3. 주제에 맞는 템플릿을 선정(bootstrap5 template 검색)
   themewagon.com, bootstrapmade.com 템플릿(반드시 bootstrap5인지 확인)
   bootstrap.kr 공용 아이콘

4. java에 공용 패키지, templates에 공용 폴더 생성
   Config : 사용자 메소드 및 변수를 빈에 등록
            보안설정(맵핑권한, 로그인, 로그아웃, 로그인 후 처리, 로그아웃 후 처리)
   Constant : 고정적인 데이터값을 클래스(변수명), 값으로 구성
               회원등급, 호텔분류(본사, 지점), 룸서비스분류(중식, 한식, 양식)
               public enum member {
                  user, master, admin("관리자")
               }
   Controller : 맵핑(service 연결, html 연결)
                  페이지처리, 생성형AI호출
   DTO : HTML에서 처리할 변수 목록, 하나의 테이블은 여러개의 DTO로 구성할 수 있다.
         사용목적에 따라서 DTO 세부적으로 구성하면 작업이 편하다.
         변수뿐만 아니고 필요에 따라 메소드도 추가해서 사용이 가능하다.
            public BoardRegisterDTO(){
                String subject;
                String content;
                String userId;
             }   
             public BoardListDTO(){
                 String subject;
                 String userId;
                 LocalDateTime modDate;
                 public int total(){
                     return ea*amount;
                    }
             }
   Entity : ERD를 참고해서 테이블을 생성
             @OneToMany, @ManyToOne을 이용해서 조인(***)
             member테이블과 order테이블을 조인시
             member호출 시 자동으로 order 읽어온다. 
             ===>DTO에 member있으면 member사용
             ===>DTO에 member, order가 존재하면 함께 사용
             직원<->호텔, 회원<->예약<->룸서비스
   Repository : SQL문을 구현, 
                 기본(id(기본키)를 대상) 삽입, 수정, 삭제, 조회 기능은 제공
                 기본키가 아닌 다른 필드를 사용할 때는 사용자가 메소드를 생성
                 findBy필드명()==>검색 기능
   Service : 로그인 처리, 일반 처리
             로그인 처리 - 보안처리를 오버라이딩으로 재편집해서 사용
             일반 처리 - 대상 테이블에 맞게 삽입, 수정, 삭제, 조회를 기본 기능으로 구현
             modelmapper : 하나의 DTO, Entity 변환
                             여러개의 DTO, Entity 변환
                             하나의 DTO안에 다른 DTO존재 (어려움)
                             여러개의 DTO안에 다른 DTO 존재 (어려움)
             파일업로드 처리 : 데이터 저장 전 파일 업로드
                             데이터 삭제 전 파일 삭제
                             데이터 수정 전 기존파일 삭제, 신규파일 업로드
   Util : 보조적인 작업, 데이터베이스처리를 제외한 기능적인 작업
           페이지 처리
           날짜 처리(5분전, 1일전, 1개월전, 1년전)
           파일업로드/삭제처리
           S3업로드
           생성형AI 구현
           이메일 전송
      
   테이블별로 패키지를 추가해서 작업을 진행한다.
5. templates
    Layouts : 템플릿을 여러개로 구성할 수 있으므로 폴더명뒤에 s(여러개 구성)
               관리자용(하나의 틀을 반복)-단순하게 삽입, 수정, 삭제, 조회를 구성 
                사용자용(프론트) - 프로젝트 주제, 팀색깔, 초보사용자가 쉽게 접하게 구성
    fragments : 조각이 여러개로 구성될 수 있으므로 폴더명 뒤에 s
                타이틀부분, 메인메뉴부분, 서브메뉴부분, 저작권(하단)부분, 회원정보부분
    테이블별로 폴더를 생성해서 작업을 진행한다.(단, 뒤에 s는 붙이지 않는다.)
    ※ templates에서 폴더 및 파일명은 가능하면 모두 소문자 구성
   (윈도우는 대소문자 구분없이 처리, 리눅스 대소문자를 구분해서 처리)
   6. 템플릿 분리작업
      1) 다운로드 받은 압축파일 해제
      2) resources(assets, css, js, img, images)와 html부분으로 구성
      3) assets, css, js, img, images 등을 static에 그대로 복사
      4) CSS 한글 입력시 글꼴이 다른 글꼴로 표현되어서 어울리지 않을 때가 존재
           ->CSS 수정(FONT을 찾아서 한글 글꼴로 변경)
           ->CSS 로고 이미지(이미지 이름으로 검색), 
           ->다른 기존 이미지 파일을 다른 이미지 이름으로 변경해서 사용
      5) index.html 파일을 layouts에 복사후 이름을 변경(보안, 타임리프, 레이아웃)
           ->중복되는 명령 오류방지를 위해 xmlns 선언
           ->head부분에서 link ="./css", "/css"로 시작시 "/.", "/" 제거
           ->fragments로 사용할 조각은 th:fragment로
           ->분리된 부분들을 파일로 저장
           ->기존 태그명 변경X, 가능하면 새로운 태그 추가X
        
         태그명, 이름등을 변경하면 안됨
         <th:block layout:fragment="script"></th:block> 선언시
         <th:block layout:fragment="css"></th:block> 지정해야 적용된다.
        
        ※ 주석을 확인하면서 작업영역을 구분
              
   