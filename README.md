# database-Theater-JavaSwing-MySQL-GUI
JAVA Swing과 MySQL 데이터베이스를 이용해서 영화관DB를 관리하는 GUI 프로그램




# **기술 스택**

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">  <img src="https://img.shields.io/badge/eclipse-2C2255?style=for-the-badge&logo=eclipseide&logoColor=white">  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">




# **프로젝트 팀**
20003201  김성민  /20011683  변성은


**김성민**
1. DataBase 스키마 작성
2. 사용자 요구사항 구현을 위한 SQL문 작성
3. jdbc를 이용해서 MySQL과 쿼리 송수신
4. Java GUI 설계/구현




# **요구사항 분석**


|항목|설명|
| - | - |
|사용자  요구사항|<p>>관리자는  초기화  버튼을  누르면  모든  정보가  초기화된다.</p><p>>관리자는  전체  테이블  보기  버튼을  누르면  모든  테이블을  조회할 수  있다.</p><p>>관리자는  변경하고  싶은  테이블을  고른  후  데이터를  입력하고  입력  실행  버튼을  누르면  새로운  데이터가  추가된다. </p><p>>관리자는  조건식을  입력하고  삭제  버튼을  누르면  해당  데이터가 삭제된다.</p><p>>관리자는  조건식을  입력하고  변경  버튼을  누르면  해당  데이터가 변경된다.</p><p>>회원은  영화명  또는  감독명  또는  배우명  또는  장르를  입력하고 조회  버튼을  누르면  해당  데이터가  들어있는  영화를  검색할  수  있다.</p><p>>회원은  검색을  통해  조회한  영화중  하나를  선택하여  예매  버튼을 누르면  상영일,  좌석,  결제수단,  id를  입력한  뒤  예매할  수  있다.</p><p>>회원은  id를  입력하고  예매조회  버튼을  클릭하면  해당  회원이  예매한 영화에 대해서 영화명 ,  상영일 ,  상영관번호 ,  좌석번호 및 판매가격 정보를 조회할 수 있다 .</p><p>>회원은 예매된 정보 중 하나를 클릭하면 해당 예매에 대해 모든 상영관일정 상영관 티켓에 대한 정보를 알 수 있다 .</p><p>>회원은 본인이 예매한 영화에 대해 조회하고 원하는 영화를 고른 뒤 삭제를 누르면 예매정보를 삭제할 수 있다 .</p><p>>회원은 본인이 예매한 영화에 대해 조회하고 변경 버튼을 누른 뒤 영화명 ,  상영일 ,  좌석을 선택하면 다른 영화로 예매를 변경할 수 있다 .</p><p>>회원은 본인이 예매한 영화에 대해 조회하고 변경 버튼을 누른 뒤 영화명 ,  상영일 ,  좌석을 선택하면 다른 일정으로 예매를 변경할 수 있다 .</p>|
|기능  요구사항|<p>>초기화  버튼이  눌리면  모든  정보를  초기화한다.</p><p>>전체  테이블  보기  버튼이  눌리면  모든  테이블의  데이터를  제공한다.</p><p>>변경하고  싶은  테이블과  각  필드의  데이터  값을  입력받고  입력 실행  버튼이  눌리면  그  값을  입력한다.</p><p>>조건식을  입력받고  삭제  버튼이  클릭되면  해당  데이터를  삭제한다.</p><p>>조건식을  입력받고  변경  버튼이  눌리면  해당  데이터가  변경된다.</p><p>>영화명  또는  감독명  또는  배우명  또는  장르가  입력되고  조회  버튼이  눌리면  해당  데이터가  들어있는  영화정보를  보여준다.</p><p>>검색을  통해  조회한  영화중  하나를  선택하여  예매  버튼이  클릭되면  상영일,  좌석,  결제수단,  id정보를  입력받고  예매한다.</p><p>>예매조회  버튼을  클릭하면  입력된  아이디로  해당  회원이  예매한 영화에 대해서 영화명 ,  상영일 ,  상영관번호 ,  좌석번호 및 판매가격 정보를 제공한다 .</p><p>>회원은 예매된 정보 중 하나를 클릭하면 해당 예매에 대해 모든 상영일정 상영관 티켓에 대한 정보를 알 수 있다 .   영화를 선택 후 삭제를 누르면 해당 회원의 예매정보를 삭제한다 .</p><p>>영화를 선택 후 변경 버튼이 클릭되고 영화명 ,  상영일 ,  좌석이 입력되면 그 값에 따라 예매 정보 (영화)를 변경한다 .</p><p>>영화를 선택 후 변경 버튼이 클릭되고 영화명 ,  상영일 ,  좌석이 입력되면 그 값에 따라 예매 정보 (상영 일정)를 변경 한다 .</p>|
|외부  인터페이스  요구 사항|<p>>입력  기능의  경우  반드시  하나의  윈도우  안에서  모든  데이터  기입,  버튼  클릭  한  번으로  구현한다.</p><p>>입력해야  할  속성이  4개라면,  속성1/속성2/속성3/속성4에  대한 입력값을  하나의  GUI  윈도우에서  입력받아야  하며,  저장/취소  버튼이  있어서  저장  (즉,  입력  실행)  혹은  취소  (입력  취소)  기능이 구현되어야  함.</p><p>>사용자  인터페이스는  반드시  GUI  (Graphical  User  Interface)를 사용한다.  단,  GUI  구동에  필요한  라이브러리는  기본  JDK에  포함 되어  있는  것으로  한정한다.</p><p>>GUI 화면에서  관리자/회원을  택하여  다음  화면으로  넘어가는  형태로  구현한다.</p>|
|논리적  데이터베이스 요구사항|<p>영화  :  영화  번호(int),  영화명  (varchar(100)),  상영시간(TIME), 상영등급(varchar(15)),  감독명(varchar(45)),  배우명 (varchar(45)),  장르  (varchar(45)),  영화  소개  (varchar(2000)) 및  개봉일(DATE)</p><p>상영일정:  상영일정번호(int),  영화번호(int),  상영관번호(int),  상영 시작일(date),  상영요일(varchar(45)),  상영회차(int)  및  상영시작시간(TIME)</p><p>상영관:  상영관번호(int),  좌석수(int)  및  상영관사용여부 (varchar(4))</p><p>티켓:  티켓번호(int),  상영일정번호(int),  상영관번호(int),  좌석번호 (int),  예매번호(int),  발권여부(varchar(4)),  표준가격(int)  및  판매 가격(int)</p><p>좌석:  좌석번호(int),  상영관번호(int)  및  좌석사용여부(varchar(4)) 회원고객:  회원아이디(varchar(45)),  고객명(varchar(45)),  휴대폰 번호(varchar(45))  및  전자메일주소(varchar(45))</p><p>예매정보:  예매번호(int),  결제방법(varchar(45)),  결제상태 (varchar(4)),  결제금액(int),  회원아이디(varchar(45))  및   결제일자(DATETIME)</p>|



# **DB스키마  정의**

<img width="1137" alt="스크린샷 2022-11-23 오후 3 38 12" src="https://user-images.githubusercontent.com/44383895/203486118-73242748-5f08-4458-b07f-b1cc183e8e06.png">


# **프로그램  테스팅**

**- 사용자가  관리자인지  회원인지  선택**

<img width="714" alt="스크린샷 2022-11-23 오후 3 38 22" src="https://user-images.githubusercontent.com/44383895/203486302-600d26e6-6220-43fd-b270-349b28775aab.png">

**- 관리자**

> 데이터베이스 초기화 기능

<img width="1133" alt="스크린샷 2022-11-23 오후 3 38 35" src="https://user-images.githubusercontent.com/44383895/203486318-53616c8b-cd34-4d98-b22c-58a14c39bf99.png">

> 전체 테이블 보기 :  모든 테이블의 내용을 보여주는 기능

<img width="1131" alt="스크린샷 2022-11-23 오후 3 38 48" src="https://user-images.githubusercontent.com/44383895/203486340-eaae9f87-d740-4925-b9a8-abe28d45e8c9.png">

> 데이터베이스에 포함된 모든 테이블에 대한 입력 /삭제 /변경 기능
> 단 ,  삭제 /변경은 “1개 ”의 고정된 특정 조건이 아닌 “조건식 ”을 입력 받아서 삭제 /변경하는 방식으로 구현해야 함

1. 데이터 입력 ->  입력 실행 후, 전체 테이블을 보면 새로운 데이터가 들어온 것이 확인 된다.

<img width="209" alt="스크린샷 2022-11-23 오후 3 38 58" src="https://user-images.githubusercontent.com/44383895/203487171-562c3d50-18cf-44cd-bc8f-f361bcbc264d.png"><img width="899" alt="스크린샷 2022-11-23 오후 3 39 07" src="https://user-images.githubusercontent.com/44383895/203486367-042ef14b-c27e-4c1a-836a-895e920760da.png">

2. 삭제 ->  조건식을 movie\_number  =  10011 으로 넣고 삭제 버튼을 누른 후, 전체 데이터를 조회하면 추가했던 영화 정보가 사라진다.

[삭제전]

<img width="1138" alt="스크린샷 2022-11-23 오후 3 39 16" src="https://user-images.githubusercontent.com/44383895/203486386-cd45b8a8-7e1c-4695-b8d8-d6d47b3207e3.png">

<img width="1131" alt="스크린샷 2022-11-23 오후 3 39 23" src="https://user-images.githubusercontent.com/44383895/203486393-c25d1bca-f7a7-4a3b-9dad-d997444eff76.png">

[삭제후]

<img width="1132" alt="스크린샷 2022-11-23 오후 3 39 32" src="https://user-images.githubusercontent.com/44383895/203486411-e7d2f86f-7ae9-4ac9-af3e-220ba977f5ee.png">

3. 변경
->  영화명 범죄도시 2를 범죄도시 1으로 변경하고 전체 테이블을 조회하면 영화명이 변경된 것이 확인된다 .

<img width="1132" alt="스크린샷 2022-11-23 오후 3 39 40" src="https://user-images.githubusercontent.com/44383895/203486429-46506b6a-6118-4f75-b14e-9732320230c4.png">

->  범죄도시 2를 범죄도시 1으로 변경

<img width="395" alt="스크린샷 2022-11-23 오후 3 39 47" src="https://user-images.githubusercontent.com/44383895/203486440-9a03458e-15ff-4d1b-ae86-39d52f820e1e.png">

<img width="1136" alt="스크린샷 2022-11-23 오후 3 39 57" src="https://user-images.githubusercontent.com/44383895/203486446-9f3dd2b2-ef68-4932-b1fc-7492165058c9.png">

**- 회원**

> 모든 영화에 대한 조회 기능 :  영화명, 감독명, 배우명, 장르를 이용한 조회 (입력 안된 정보는 무시하고 조회함)

->  영화명 필드에 '안녕' 입력시 영화명에 '안녕'이 들어간 데이터 검색됨 .

<img width="1132" alt="스크린샷 2022-11-23 오후 3 40 04" src="https://user-images.githubusercontent.com/44383895/203486464-dae36108-b68c-45db-934a-4306bd69a276.png">

> 위에서 조회한 영화에 대한 예매 기능

<img width="1134" alt="스크린샷 2022-11-23 오후 3 40 12" src="https://user-images.githubusercontent.com/44383895/203486469-cf4dbb1d-999c-4a01-af70-d535a2f87f18.png">

<img width="311" alt="스크린샷 2022-11-23 오후 3 40 17" src="https://user-images.githubusercontent.com/44383895/203486479-8006136f-ace3-4da5-8080-2e3000ab409a.png">

<img width="782" alt="스크린샷 2022-11-23 오후 3 40 21" src="https://user-images.githubusercontent.com/44383895/203486490-e98c22aa-eea2-45b8-829c-8ddd044f240e.png">

-> 검색된 영화 중 하나를 선택후 예매 정보를 입력하면

<img width="1132" alt="스크린샷 2022-11-23 오후 3 40 27" src="https://user-images.githubusercontent.com/44383895/203486504-7b8559f3-f378-4d5d-b8b9-bf139113c4c4.png">

-> 예매 조회를 하면 예매된 것이 확인됨.

> 본인이 예매한 영화에 대해서 영화명, 상영일, 상영관번호, 좌석번호 및 판매가격 정보를 보여주는 기능

<img width="1133" alt="스크린샷 2022-11-23 오후 3 40 34" src="https://user-images.githubusercontent.com/44383895/203486513-4776df8a-e26c-45d3-acb7-cbc8fef28f0c.png">

-> 아이디를 입력하고 예매 조회 버튼을 누르면 예매한 영화 정보가 조회됨 .

> 본인이 예매한 영화에 대하여 조회하고 한 개 이상의 예매 정보를 "삭제"하는 기능

[삭제전]

<img width="1133" alt="스크린샷 2022-11-23 오후 3 40 43" src="https://user-images.githubusercontent.com/44383895/203486551-7e4c6716-faa9-41a6-bb65-dbe0368bfd17.png">

<img width="320" alt="스크린샷 2022-11-23 오후 3 40 51" src="https://user-images.githubusercontent.com/44383895/203486555-575a9518-81e7-4e9e-ad32-aad8158e464c.png">

[삭제후]

<img width="1128" alt="스크린샷 2022-11-23 오후 3 40 56" src="https://user-images.githubusercontent.com/44383895/203486568-3f6af0e6-f755-4bd2-a353-473d3a041041.png">

> 본인이 예매한 영화에 대하여 조회하여 다른 영화로 예매를 변경하는 기능

<img width="565" alt="스크린샷 2022-11-23 오후 3 41 04" src="https://user-images.githubusercontent.com/44383895/203486582-81b82a03-999f-4ac9-9fa4-daf15f06939d.png">

-> 범죄 도시 2에서 닥터스트레인지로 변경

<img width="1130" alt="스크린샷 2022-11-23 오후 3 41 11" src="https://user-images.githubusercontent.com/44383895/203486729-d53e52f6-b1b2-4f09-9ba0-ef547cf8ddf7.png">

> 본인이 예매한 영화에 대하여 조회하여 다른 상영 일정으로 변경하는 기능

<img width="1133" alt="스크린샷 2022-11-23 오후 3 41 18" src="https://user-images.githubusercontent.com/44383895/203486741-7602d27e-9db5-4a7b-aa1b-10d7972bf7fa.png">

<img width="712" alt="스크린샷 2022-11-23 오후 3 41 24" src="https://user-images.githubusercontent.com/44383895/203486769-4306fc2a-fa0f-4b29-9f6c-c0feb9b1384b.png">

-> 예매 일정 변경

<img width="1133" alt="스크린샷 2022-11-23 오후 3 41 31" src="https://user-images.githubusercontent.com/44383895/203486785-6242dc71-99e4-4340-8ec3-a74ab99c199a.png">
