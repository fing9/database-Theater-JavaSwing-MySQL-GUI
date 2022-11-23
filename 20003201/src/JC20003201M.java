import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class JC20003201M extends JFrame {
	// Container 객체
	Container c = getContentPane();

	String tableList[] = { "Movies", "Theater", "Seat", "Members", "Schedules", "Reservation", "Ticket" };
	// String tableList2[] = { "영화명", "감독명", "배우명", "장르" }; // Movies
	/*
	 * String colList1[] = { "movie_number", "movie_title", "running_time",
	 * "movie_rating", "director", "actor", "genre", "movie_introduction",
	 * "release_date"}; String colList2[] = { "Theater_number", "Theater_capacity",
	 * "Theater_status" }; String colList3[] = { "seat_number", "Theater_number",
	 * "seat_status" }; String colList4[] = { "member_id", "member_name",
	 * "phone_number", "email" }; String colList5[] = { "schedule_number",
	 * "movie_number", "Theater_number", "start_date", "screen_day", "screen_round",
	 * "show_time", "show_date"}; String colList6[] = { "reservation_number",
	 * "payment_method", "payment_status", "payment_amount", "member_id",
	 * "payment_date" }; String colList7[] = { "ticket_number", "schedule_number",
	 * "Theater_number", "seat_number", "reservation_number", "ticket_status",
	 * "standard_price", "sale_price"};
	 */

	// TextField, TextArea, ComboBox, JLabel 객체
	JComboBox<String> Ctable = new JComboBox<String>(tableList);
	// JComboBox<String> Ccol = new JComboBox<String>(colList1);
	JLabel la1 = new JLabel("DELETE ");
	JTextField tInput1 = new JTextField(8);
	JTextField tInput2 = new JTextField(8);
	JTextField tInput3 = new JTextField(8);
	JTextField tInput4 = new JTextField(8);
	JTextField tInput5 = new JTextField(8);
	JTextField tInput6 = new JTextField(8);
	JTextField tInput7 = new JTextField(8);
	JTextField tInput8 = new JTextField(8);
	JTextField tInput9 = new JTextField(8);
	JTextField DInput = new JTextField(8);
	JTextArea ta = new JTextArea();
	JScrollPane sp = new JScrollPane(ta);

	// 회원 컴포넌트
	JComboBox<String> Ctable2 = new JComboBox<String>();
	JComboBox<String> Ctable3 = new JComboBox<String>();
	JComboBox<String> Ctable4 = new JComboBox<String>();
	JTextField tf1 = new JTextField(8);
	JTextField tf2 = new JTextField(8);
	JTextField tf3 = new JTextField(8);
	JTextField tf4 = new JTextField(8);
	JTextField tf5 = new JTextField(8);
	// JScrollPane sp2 = new JScrollPane(ta);

	// MYSQL 객체
	Connection conn = null;
	Connection conn2 = null;
	Connection conn3 = null;
	Connection conn4 = null;
	Connection conn5 = null;
	Statement stmt = null;
	Statement stmt2 = null;
	Statement stmt3 = null;
	Statement stmt4 = null;
	Statement stmt5 = null;
	ResultSet rs = null;
	ResultSet rs2 = null;

	class BtListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();

			// 여기다가 버튼을 눌렀을 때 할 일 지정
			if (b.getText().equals("초기화")) { // 초기화 버튼
				// MySQL과 프로젝트 연결
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");

					String url = "jdbc:mysql://localhost/madang";

					conn = DriverManager.getConnection(url, "madang", "madang");

					stmt = conn.createStatement();

					// drop database
					stmt.executeUpdate("DROP DATABASE IF EXISTS madang;");
					stmt.executeUpdate("create database madang;");
					stmt.executeUpdate("grant all privileges on madang.* to madang@localhost with grant option;");
					stmt.executeUpdate("commit;");
					stmt.executeUpdate("USE madang;");
					stmt.executeUpdate("SET SQL_SAFE_UPDATES = 0;");

					stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `madang`.`Movies` (\r\n"
							+ "  `movie_number` INT NOT NULL AUTO_INCREMENT,\r\n"
							+ "  `movie_title` VARCHAR(100) NOT NULL,\r\n" + "  `running_time` TIME NOT NULL,\r\n"
							+ "  `movie_rating` VARCHAR(15) NOT NULL,\r\n" + "  `director` VARCHAR(45) NOT NULL,\r\n"
							+ "  `actor` VARCHAR(200) NOT NULL,\r\n" + "  `genre` VARCHAR(45) NOT NULL,\r\n"
							+ "  `movie_introduction` VARCHAR(2000) NOT NULL,\r\n"
							+ "  `release_date` DATE NOT NULL,\r\n" + "  PRIMARY KEY (`movie_number`)\r\n" + ");");

					stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `madang`.`Theater` (\r\n"
							+ "  `Theater_number` INT NOT NULL AUTO_INCREMENT,\r\n"
							+ "  `Theater_capacity` INT NOT NULL,\r\n" + "  `Theater_status` VARCHAR(4) NOT NULL,\r\n"
							+ "  PRIMARY KEY (`Theater_number`)\r\n" + ");");

					stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `madang`.`Seat` (\r\n"
							+ "  `seat_number` INT NOT NULL AUTO_INCREMENT,\r\n"
							+ "  `Theater_number` INT NOT NULL,\r\n" + "  `seat_status` VARCHAR(4) NOT NULL,\r\n"
							+ "  PRIMARY KEY (`seat_number`, `Theater_number`),\r\n"
							+ "  INDEX `fk_seat_Theater1_idx` (`Theater_number` ASC) VISIBLE,\r\n"
							+ "  CONSTRAINT `fk_seat_Theater1`\r\n" + "    FOREIGN KEY (`Theater_number`)\r\n"
							+ "    REFERENCES `madang`.`Theater` (`Theater_number`)\r\n" + "    ON DELETE CASCADE\r\n"
							+ "    ON UPDATE CASCADE\r\n" + ");");

					stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `madang`.`Members` (\r\n"
							+ "  `member_id` VARCHAR(45) NOT NULL,\r\n" + "  `member_name` VARCHAR(45) NOT NULL,\r\n"
							+ "  `phone_number` VARCHAR(45) NOT NULL,\r\n" + "  `email` VARCHAR(45) NOT NULL,\r\n"
							+ "  PRIMARY KEY (`member_id`)\r\n" + ");");

					stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `madang`.`Schedules` (\r\n"
							+ "  `schedule_number` INT NOT NULL AUTO_INCREMENT,\r\n"
							+ "  `movie_number` INT NOT NULL,\r\n" + "  `Theater_number` INT NOT NULL,\r\n"
							+ "  `start_date` DATE NOT NULL,\r\n" + "  `screen_day` VARCHAR(45) NOT NULL,\r\n"
							+ "  `screen_round` INT NOT NULL,\r\n" + "  `show_time` TIME NOT NULL,\r\n"
							+ "  `show_date` DATE NOT NULL,\r\n" + "  PRIMARY KEY (`schedule_number`),\r\n"
							+ "  INDEX `fk_schedule_movie_idx` (`movie_number` ASC) VISIBLE,\r\n"
							+ "  INDEX `fk_schedule_Theater1_idx` (`Theater_number` ASC) VISIBLE,\r\n"
							+ "  CONSTRAINT `fk_schedule_movie`\r\n" + "    FOREIGN KEY (`movie_number`)\r\n"
							+ "    REFERENCES `madang`.`Movies` (`movie_number`)\r\n" + "    ON DELETE CASCADE\r\n"
							+ "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `fk_schedule_Theater1`\r\n"
							+ "    FOREIGN KEY (`Theater_number`)\r\n"
							+ "    REFERENCES `madang`.`Theater` (`Theater_number`)\r\n" + "    ON DELETE CASCADE\r\n"
							+ "    ON UPDATE CASCADE\r\n" + ");");

					stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `madang`.`Reservation` (\r\n"
							+ "  `reservation_number` INT NOT NULL AUTO_INCREMENT,\r\n"
							+ "  `payment_method` VARCHAR(45) NOT NULL,\r\n"
							+ "  `payment_status` VARCHAR(45) NOT NULL,\r\n" + "  `payment_amount` INT NOT NULL,\r\n"
							+ "  `member_id` VARCHAR(45) NOT NULL,\r\n" + "  `payment_date` DATETIME NOT NULL,\r\n"
							+ "  PRIMARY KEY (`reservation_number`),\r\n"
							+ "  INDEX `fk_reservation_member1_idx` (`member_id` ASC) VISIBLE,\r\n"
							+ "  CONSTRAINT `fk_reservation_member1`\r\n" + "    FOREIGN KEY (`member_id`)\r\n"
							+ "    REFERENCES `madang`.`Members` (`member_id`)\r\n" + "    ON DELETE CASCADE\r\n"
							+ "    ON UPDATE CASCADE\r\n" + ");");

					stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `madang`.`Ticket` (\r\n"
							+ "  `ticket_number` INT NOT NULL AUTO_INCREMENT,\r\n"
							+ "  `schedule_number` INT NOT NULL,\r\n" + "  `Theater_number` INT NOT NULL,\r\n"
							+ "  `seat_number` INT NOT NULL,\r\n" + "  `reservation_number` INT NOT NULL,\r\n"
							+ "  `ticket_status` VARCHAR(4) NOT NULL,\r\n" + "  `standard_price` INT NOT NULL,\r\n"
							+ "  `sale_price` INT NOT NULL,\r\n" + "  PRIMARY KEY (`ticket_number`),\r\n"
							+ "  INDEX `fk_ticket_seat1_idx` (`seat_number` ASC, `Theater_number` ASC) VISIBLE,\r\n"
							+ "  INDEX `fk_ticket_schedule1_idx` (`schedule_number` ASC) VISIBLE,\r\n"
							+ "  INDEX `fk_ticket_reservation1_idx` (`reservation_number` ASC) VISIBLE,\r\n"
							+ "  CONSTRAINT `fk_ticket_seat1`\r\n"
							+ "    FOREIGN KEY (`seat_number` , `Theater_number`)\r\n"
							+ "    REFERENCES `madang`.`Seat` (`seat_number` , `Theater_number`)\r\n"
							+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE,\r\n"
							+ "  CONSTRAINT `fk_ticket_schedule1`\r\n" + "    FOREIGN KEY (`schedule_number`)\r\n"
							+ "    REFERENCES `madang`.`Schedules` (`schedule_number`)\r\n"
							+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE,\r\n"
							+ "  CONSTRAINT `fk_ticket_reservation1`\r\n" + "    FOREIGN KEY (`reservation_number`)\r\n"
							+ "    REFERENCES `madang`.`Reservation` (`reservation_number`)\r\n"
							+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE\r\n" + ");");

					stmt.executeUpdate("ALTER TABLE Movies AUTO_INCREMENT=10001;");
					stmt.executeUpdate("ALTER TABLE Theater AUTO_INCREMENT=20001;");
					stmt.executeUpdate("ALTER TABLE Seat AUTO_INCREMENT=30001;");
					stmt.executeUpdate("ALTER TABLE Schedules AUTO_INCREMENT=50001;");
					stmt.executeUpdate("ALTER TABLE Reservation AUTO_INCREMENT=60001;");
					stmt.executeUpdate("ALTER TABLE Ticket AUTO_INCREMENT=70001;");

					// 여기서부터 입력할 데이터 쿼리문으로 쭉 쏴주면 됨.
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '범죄도시2', '01:46:00', '15세', '이상용','마동석, 손석구, 최귀화','범죄, 액션','가리봉동 소탕작전 후 4년 뒤, 금천서 강력반은 베트남으로 도주한 용의자를 인도받아 오라는 미션을 받는다. 괴물형사 ‘마석도’(마동석)와 ‘전일만’(최귀화) 반장은 현지 용의자에게서 수상함을 느끼고, 그의 뒤에 무자비한 악행을 벌이는 ‘강해상’(손석구)이 있음을 알게 된다. ‘마석도’와 금천서 강력반은 한국과 베트남을 오가며 역대급 범죄를 저지르는 ‘강해상’을 본격적으로 쫓기 시작하는데... 나쁜 놈들 잡는 데 국경 없다! 통쾌하고 화끈한 범죄 소탕 작전이 다시 펼쳐진다!','2021-01-01');");
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '닥터스트레인지', '02:06:00', '12세', '샘 레이미','베네딕트 컴버배치, 엘리자베스 올슨','액션','끝없이 균열되는 차원과 뒤엉킨 시공간의 멀티버스가 열리며 오랜 동료들, 그리고 차원을 넘어 들어온 새로운 존재들을 맞닥뜨리게 된 ‘닥터 스트레인지’. 대혼돈 속, 그는 예상치 못한 극한의 적과 맞서 싸워야만 하는데….','2021-02-02');\r\n");
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '피는 물보다 진하다', '01:30:00', '15세', '김희성','조동혁, 이완, 임정은','액션','최대 청부폭력조직 백정파는 무자비함으로 악명 높은 해결사, 일명 도깨비를 앞세워 일대를 장악한다. 그러나, 베일에 싸인 ‘도깨비’ 두현은 친형제 같았던 영민의 죄를 뒤집어쓰고 10년의 수감생활을 하게 된다. 한편, 두현이 사라진 사이, ‘도깨비＇행세를 하며 조직을 차지한 영민은 두현의 출소 소식을 듣고 불안에 휩싸인다. 영민은 두현을 먼저 치기로 하고 새 삶을 시작하려던 두현은 결국, 진짜 ‘도깨비’의 부활을 선언하며 영민과 조직을 상대로 싸움을 시작하는데...','2021-03-03');\r\n");
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '안녕하세요', '01:58:00', '전체관람가', '차봉주','김환희, 유선, 이순재','드라마','외로운 세상 속에서 죽음을 결심한 열아홉 수미(김환희). ‘죽는 법’을 알려주겠다는 수간호사 서진(유선)의 제안에 따라 늘봄 호스피스 병동을 찾아간다. 이 사람들이 곧 죽을 사람들이라고?! 예상치 못한 유쾌함과 따뜻함이 수미를 반기고, 하루하루를 소중히 살아가는 그들에게 점차 스며들며 세상의 온기를 배워가기 시작하는데… ‘힐링 메이트’들이 전하는 눈부시게 찬란한 위로! “안녕하세요”','2021-04-04');\r\n");
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '아치의 노래, 정태춘', '01:53:00', '15세', '고영재','정태춘, 박은옥','다큐멘터리','10대 가수상, 가요 사전심의 철폐운동 그리고 음악시장을 홀연히 떠나기까지 어디서도 들어보지 못한 노랫말과 서정적인 음율로 시대와 함께한 뮤지션 데뷔 40주년, 우리가 몰랐던 정태춘의 음악과 삶을 만나다!','2021-05-05');\r\n");

					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '오마주', '01:48:00', '12세', '신수원','이정은, 권해효, 탕준상','드라마','엄마 영화는 재미없다는 아들과 늘상 밥타령인 남편, 잇따른 흥행 실패로 슬럼프에 빠진 중년의 영화감독 지완. 아르바이트 삼아 60년대에 활동한 한국 두 번째 여성 영화감독 홍은원 감독의 작품 <여판사>의 필름을 복원하게 된다. 사라진 필름을 찾아 홍감독의 마지막 행적을 따라가던 지완은 정체를 알 수 없는 모자 쓴 여성의 그림자와 함께 그 시간 속을 여행하게 되는데... 어쩐지, 희미해진 꿈과 영화에 대한 열정이 되살아나는 것만 같다. ','2021-06-04');\r\n");
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '쥬라기 월드: 도미니언', '02:27:00', '12세', '콜린 트레보로우','크리스 프랫, 브라이스 달라스 하워드','액션','공룡들의 터전이었던 이슬라 누블라 섬이 파괴된 후, 마침내 공룡들은 섬을 벗어나 세상 밖으로 출몰한다. 지상에 함께 존재해선 안 될 위협적 생명체인 공룡의 등장으로 인류 역사상 겪어보지 못한 사상 최악의 위기를 맞이한 인간들. 지구의 최상위 포식자 자리를 걸고 인간과 공룡의 최후의 사투가 펼쳐진다.','2021-07-01');\r\n");
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '베르네 부인의 장미정원', '01:35:00', '12세', '피에르 피노드','카트린 프로, 팟사 부야메드, 올리비아 코트','드라마','프랑스 최고의 원예사 에브 베르네는 대를 이어 장미정원을 운영해오고 있다. 하지만 장미를 공산품 취급하는 사업가 라마르젤에 밀려 명성과 고객은 물론, 자신의 정원까지 모두 빼앗길 위기에 처한다. 신입 원예사를 뽑아 정원을 지키려 하지만 경력도 지식도 없는 초짜 직원들은 문제를 일으키기만 하는데... 과연 에브와 원예 초보 신입들은 정원을 구해낼 수 있을까?','2021-08-01');\r\n");
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '노트북', '02:03:00', '15세', '닉 카사베츠','라이언 고슬링, 레이첼 맥아담스','멜로/로맨스','17살, ‘노아’는 밝고 순수한 ‘앨리’를 보고 첫눈에 반한다. 빠른 속도로 서로에게 빠져드는 둘. 그러나 이들 앞에 놓인 장벽에 막혀 이별하게 된다. 24살, ‘앨리’는 우연히 신문에서 ‘노아’의 소식을 접하고 잊을 수 없는 첫사랑 앞에서 다시 한 번 선택의 기로에 서게 되는데… 열일곱의 설렘, 스물넷의 아픈 기억, 그리고 마지막까지… 한 사람을 지극히 사랑했으니 내 인생은 성공한 인생입니다','2021-09-10');\r\n");
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '이터널 선샤인', '01:47:00', '15세', '미셸 공드리','짐 캐리, 케이트 윈슬렛, 커스틴 던스트','멜로/로맨스','조엘은 아픈 기억만을 지워준다는 라쿠나사를 찾아가 헤어진 연인 클레멘타인의 기억을 지우기로 결심한다. 기억이 사라져 갈수록 조엘은 사랑이 시작되던 순간, 행복한 기억들, 가슴 속에 각인된 추억들을 지우기 싫어지기만 하는데... 당신을 지우면 이 아픔도 사라질까요? 사랑은 그렇게 다시 기억된다.','2021-10-10');\r\n");

					stmt.executeUpdate("INSERT INTO Theater VALUES(0, 50 ,'Y');");
					stmt.executeUpdate("INSERT INTO Theater VALUES(0, 80 ,'Y');");
					stmt.executeUpdate("INSERT INTO Theater VALUES(0, 50 ,'Y');");
					stmt.executeUpdate("INSERT INTO Theater VALUES(0, 50 ,'Y');");
					stmt.executeUpdate("INSERT INTO Theater VALUES(0, 30 ,'Y');");

					stmt.executeUpdate("INSERT INTO Theater VALUES(0, 50 ,'Y');");
					stmt.executeUpdate("INSERT INTO Theater VALUES(0, 40 ,'Y');");
					stmt.executeUpdate("INSERT INTO Theater VALUES(0, 30 ,'Y');");
					stmt.executeUpdate("INSERT INTO Theater VALUES(0, 30 ,'Y');");
					stmt.executeUpdate("INSERT INTO Theater VALUES(0, 50 ,'Y');");

					for (int i = 0; i < 50; i++)
						stmt.executeUpdate("INSERT INTO Seat VALUES(0,20001,'N');");
					for (int i = 0; i < 80; i++)
						stmt.executeUpdate("INSERT INTO Seat VALUES(0,20002,'N');");
					for (int i = 0; i < 50; i++)
						stmt.executeUpdate("INSERT INTO Seat VALUES(0,20003,'N');");
					for (int i = 0; i < 50; i++)
						stmt.executeUpdate("INSERT INTO Seat VALUES(0,20004,'N');");
					for (int i = 0; i < 30; i++)
						stmt.executeUpdate("INSERT INTO Seat VALUES(0,20005,'N');");

					for (int i = 0; i < 50; i++)
						stmt.executeUpdate("INSERT INTO Seat VALUES(0,20006,'N');");
					for (int i = 0; i < 40; i++)
						stmt.executeUpdate("INSERT INTO Seat VALUES(0,20007,'N');");
					for (int i = 0; i < 30; i++)
						stmt.executeUpdate("INSERT INTO Seat VALUES(0,20008,'N');");
					for (int i = 0; i < 30; i++)
						stmt.executeUpdate("INSERT INTO Seat VALUES(0,20009,'N');");
					for (int i = 0; i < 50; i++)
						stmt.executeUpdate("INSERT INTO Seat VALUES(0,20010,'N');");

					stmt.executeUpdate(
							"INSERT INTO Members VALUES('bsu0404','변성은','010-3854-7009','bsu0404@naver.com');");
					stmt.executeUpdate(
							"INSERT INTO Members VALUES('ksm0101','김성민','010-1234-5678','ksm0101@naver.com');");
					stmt.executeUpdate(
							"INSERT INTO Members VALUES('kmk0412','김민경','010-1523-8956','kmk2364@naver.com');");
					stmt.executeUpdate(
							"INSERT INTO Members VALUES('jyj0328','정유진','010-7852-2145','jyj0328@naver.com');");
					stmt.executeUpdate(
							"INSERT INTO Members VALUES('jye0428','조예은','010-5266-7851','jye0428@naver.com');");

					stmt.executeUpdate(
							"INSERT INTO Members VALUES('lwb1003','이수빈','010-2569-9685','lwb1003@naver.com');");
					stmt.executeUpdate(
							"INSERT INTO Members VALUES('lhy0909','이하연','010-5682-4538','lhy0909@naver.com');");
					stmt.executeUpdate(
							"INSERT INTO Members VALUES('lhj0911','이현정','010-9089-1932','lhj0911@naver.com');");
					stmt.executeUpdate(
							"INSERT INTO Members VALUES('ksy0626','권수연','010-8910-9365','ksy0626@naver.com');");
					stmt.executeUpdate(
							"INSERT INTO Members VALUES('lmk0211','이민경','010-2548-8542','lmk0211@naver.com');");

					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10001,20001,'2021-01-19','일요일','2','17:25:00','2021-01-24');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10002,20002,'2021-02-08','수요일','3','10:40:00','2021-02-05');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10003,20003,'2021-03-28','화요일','1','11:20:00','2021-03-28');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10004,20004,'2021-04-19','일요일','5','09:25:00','2021-04-20');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10005,20005,'2021-05-20','월요일','6','13:50:00','2021-05-23');");

					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10006,20006,'2021-06-13','월요일','2','14:30:00','2021-06-30');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10007,20007,'2021-07-19','일요일','4','19:45:00','2021-07-25');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10008,20008,'2021-08-12','일요일','7','22:20:00','2021-08-26');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10009,20009,'2021-09-15','수요일','3','12:15:00','2021-09-30');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10010,20010,'2021-10-04','토요일','1','15:05:00','2021-10-12');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10001,20002,'2021-11-19','토요일','1','15:05:00','2021-11-18');");

					
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'카드','결제완료',10000,'bsu0404','2021-01-19 10:30:00');");
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'현금','결제완료',10000,'ksm0101','2021-02-05 11:35:05');");
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'카드','결제완료',10000,'kmk0412','2021-03-26 10:20:10');");
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'네이버페이','결제대기',10000,'jyj0328','2021-04-19 09:10:07');");
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'카카오페이','결제완료',10000,'jye0428','2021-05-19 11:30:08');");

					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'카드','결제완료',10000,'lwb1003','2021-06-27 10:30:06');");
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'카드','결제대기',10000,'lhy0909','2021-07-02 13:30:48');");
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'현금','결제완료',10000,'lhj0911','2021-08-02 15:30:45');");
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'카드','결제완료',10000,'ksy0626','2021-09-27 16:30:26');");
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'현금','결제대기',10000,'lmk0211','2021-10-11 08:30:18');");

					stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50001,20001,30001,60001,'Y',13000,10000);");
					stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50002,20002,30051,60002,'Y',13000,10000);");
					stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50003,20003,30131,60003,'Y',13000,10000);");
					stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50004,20004,30181,60004,'Y',13000,10000);");
					stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50005,20005,30231,60005,'Y',13000,10000);");

					stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50006,20006,30261,60006,'Y',13000,10000);");
					stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50007,20007,30311,60007,'Y',13000,10000);");
					stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50008,20008,30351,60008,'Y',13000,10000);");
					stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50009,20009,30381,60009,'Y',13000,10000);");
					stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50010,20010,30411,60010,'Y',13000,10000);");

					stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30001");
					stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30051");
					stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30131");
					stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30181");
					stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30231");

					stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30261");
					stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30311");
					stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30351");
					stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30381");
					stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30411");
					ta.setText("데이터 베이스가 초기화 됐습니다.");
				} catch (ClassNotFoundException e1) {
					// 드라이버 로딩 실패
					System.out.println("드라이버 로딩 실패");
				} catch (SQLException e1) {
					// 에러
					System.out.println("에러: " + e1);
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} else if (b.getText().equals("전체 테이블 보기")) { // 전체 테이블 보기 버튼

				// MySQL과 프로젝트 연결
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");

					String url = "jdbc:mysql://localhost/madang";

					conn = DriverManager.getConnection(url, "madang", "madang");

					stmt = conn.createStatement();

					ta.setText(""); // TextArea 초기화

					rs = stmt.executeQuery("select * from Movies");
					ta.append("Table:Movies\n");
					ta.append(
							"movie_number movie_title running_time movie_rating director actor genre movie_introduction release_date\n");
					while (rs.next()) {
						ta.append(rs.getString("movie_number") + "  " + rs.getString("movie_title") + "  "
								+ rs.getString("running_time") + "  " + rs.getString("movie_rating") + "  "
								+ rs.getString("director") + "  " + rs.getString("actor") + "  " + rs.getString("genre")
								+ "  " + rs.getString("movie_introduction") + "  " + rs.getString("release_date")
								+ "\n\n");
					}

					rs = stmt.executeQuery("select * from Theater");
					ta.append("\nTable:Theater\n");
					ta.append("Theater_number Theater_capacity Theater_status\n");
					while (rs.next()) {
						ta.append(rs.getString("Theater_number") + "  " + rs.getString("Theater_capacity") + "  "
								+ rs.getString("Theater_status") + "\n");
					}

					rs = stmt.executeQuery("select * from Seat");
					ta.append("\nTable:Seat\n");
					ta.append("seat_number Theater_number seat_status\n");
					while (rs.next()) {
						ta.append(rs.getString("seat_number") + "  " + rs.getString("Theater_number") + "  "
								+ rs.getString("seat_status") + "\n");
					}

					rs = stmt.executeQuery("select * from Members");
					ta.append("\nTable:Members\n");
					ta.append("member_id member_name phone_number email\n");
					while (rs.next()) {
						ta.append(rs.getString("member_id") + "  " + rs.getString("member_name") + "  "
								+ rs.getString("phone_number") + "  " + rs.getString("email") + "\n");
					}

					rs = stmt.executeQuery("select * from Schedules");
					ta.append("\nTable:Schedules\n");
					ta.append(
							"schedule_number movie_number Theater_number start_date screen_day screen_round show_time show_date\n");
					while (rs.next()) {
						ta.append(rs.getString("schedule_number") + "  " + rs.getString("movie_number") + "  "
								+ rs.getString("Theater_number") + "  " + rs.getString("start_date") + "  "
								+ rs.getString("screen_day") + "  " + rs.getString("screen_round") + "  "
								+ rs.getString("show_time") + "  " + rs.getString("show_date") + "  " + "\n");
					}

					rs = stmt.executeQuery("select * from Reservation");
					ta.append("\nTable:Reservation\n");
					ta.append(
							"reservation_number payment_method payment_status payment_amount member_id payment_date\n");
					while (rs.next()) {
						ta.append(rs.getString("reservation_number") + "  " + rs.getString("payment_method") + "  "
								+ rs.getString("payment_status") + "  " + rs.getString("payment_amount") + "  "
								+ rs.getString("member_id") + "  " + rs.getString("payment_date") + "  " + "\n");
					}

					rs = stmt.executeQuery("select * from Ticket");
					ta.append("\nTable:Ticket\n");
					ta.append(
							"ticket_number schedule_number Theater_number seat_number reservation_number ticket_status standard_price sale_price\n");
					while (rs.next()) {
						ta.append(rs.getString("ticket_number") + "  " + rs.getString("schedule_number") + "  "
								+ rs.getString("Theater_number") + "  " + rs.getString("seat_number") + "  "
								+ rs.getString("reservation_number") + "  " + rs.getString("ticket_status") + "  "
								+ rs.getString("standard_price") + "  " + rs.getString("sale_price") + "  " + "\n");
					}

				} catch (ClassNotFoundException e1) {
					// 드라이버 로딩 실패 System.out.println("드라이버 로딩 실패");
				} catch (SQLException e1) {
					// 에러 System.out.println("에러: " + e1);
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} else if (b.getText().equals("입력 실행")) { // 입력 버튼
				String table = (String) Ctable.getSelectedItem();
				String fd1, fd2, fd3, fd4, fd5, fd6, fd7, fd8, fd9;
				fd1 = tInput1.getText();
				fd2 = tInput2.getText();
				fd3 = tInput3.getText();
				fd4 = tInput4.getText();
				fd5 = tInput5.getText();
				fd6 = tInput6.getText();
				fd7 = tInput7.getText();
				fd8 = tInput8.getText();
				fd9 = tInput9.getText();

				// MySQL과 프로젝트 연결
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");

					String url = "jdbc:mysql://localhost/madang";

					conn = DriverManager.getConnection(url, "madang", "madang");

					stmt = conn.createStatement();

					ta.setText(""); // TextArea 초기화

					if (table.compareTo("Movies") == 0)
						stmt.executeUpdate("INSERT INTO " + table + " VALUES(" + fd1 + "," + "\'" + fd2 + "\'" + ","
								+ "\'" + fd3 + "\'" + "," + "\'" + fd4 + "\'" + "," + "\'" + fd5 + "\'" + "," + "\'"
								+ fd6 + "\'" + "," + "\'" + fd7 + "\'" + "," + "\'" + fd8 + "\'" + "," + "\'" + fd9
								+ "\'" + ")");
					else if (table.compareTo("Theater") == 0)
						stmt.executeUpdate(
								"INSERT INTO " + table + " VALUES(" + fd1 + "," + fd2 + "," + "\'" + fd3 + "\'" + ")");
					else if (table.compareTo("Seat") == 0)
						stmt.executeUpdate(
								"INSERT INTO " + table + " VALUES(" + fd1 + "," + fd2 + "," + "\'" + fd3 + "\'" + ")");
					else if (table.compareTo("Members") == 0)
						stmt.executeUpdate("INSERT INTO " + table + " VALUES(" + "\'" + fd1 + "\'" + "," + "\'" + fd2
								+ "\'" + "," + "\'" + fd3 + "\'" + "," + "\'" + fd4 + "\'" + ");");
					else if (table.compareTo("Schedules") == 0)
						stmt.executeUpdate("INSERT INTO " + table + " VALUES(" + fd1 + "," + fd2 + "," + fd3 + ","
								+ "\'" + fd4 + "\'" + "," + "\'" + fd5 + "\'" + "," + fd6 + "," + "\'" + fd7 + "\'"
								+ "," + "\'" + fd8 + "\'" + ")");
					else if (table.compareTo("Reservation") == 0)
						stmt.executeUpdate(
								"INSERT INTO " + table + " VALUES(" + fd1 + "," + "\'" + fd2 + "\'" + "," + "\'" + fd3
										+ "\'" + "," + fd4 + "," + "\'" + fd5 + "\'" + "," + "\'" + fd6 + "\'" + ")");
					else if (table.compareTo("Ticket") == 0)
						stmt.executeUpdate("INSERT INTO " + table + " VALUES(" + fd1 + "," + fd2 + "," + fd3 + "," + fd4
								+ "," + fd5 + "," + "\'" + fd6 + "\'" + "," + fd7 + "," + fd8 + ")");

					ta.setText("데이터가 정상적으로 입력되었습니다.");
				} catch (ClassNotFoundException e1) {
					// 드라이버 로딩 실패 System.out.println("드라이버 로딩 실패");
				} catch (SQLException e1) {
					// 에러 System.out.println("에러: " + e1);
					ta.setText("올바른 데이터 입력이 아닙니다. 다시 입력해주세요");
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			} else if (b.getText().equals("삭제")) { // 삭제 버튼
				String table = (String) Ctable.getSelectedItem();
				String di = DInput.getText();
				// MySQL과 프로젝트 연결
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");

					String url = "jdbc:mysql://localhost/madang";

					conn = DriverManager.getConnection(url, "madang", "madang");

					stmt = conn.createStatement();

					ta.setText(""); // TextArea 초기화

					stmt.execute("DELETE FROM " + table + " WHERE " + di + ";");

					DInput.setText("삭제를 위한 조건식을 입력해주세요.");
					ta.setText("데이터를 성공적으로 삭제했습니다."); // 삭제 성공
				} catch (ClassNotFoundException e1) {
					// 드라이버 로딩 실패 System.out.println("드라이버 로딩 실패");
				} catch (SQLException e1) {
					// 에러 System.out.println("에러: " + e1);
					ta.setText("데이터 삭제에 실패했습니다."); // 삭제 실패
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} else if (b.getText().equals("변경")) { // 변경 버튼
				new Change();
			} else if (b.getText().equals("입력 취소")) { // 입력 취소 버튼
				String table = (String) Ctable.getSelectedItem();
				if (table.equals("Movies")) {
					DInput.setText("삭제를 위한 조건식을 입력해주세요.");
					tInput1.setText("0");
					tInput2.setText("movie_title");
					tInput3.setText("running_time");
					tInput4.setText("movie_rating");
					tInput5.setText("director");
					tInput6.setText("actor");
					tInput7.setText("genre");
					tInput8.setText("movie_introduction");
					tInput9.setText("release_date");
				} else if (table.equals("Theater")) {
					DInput.setText("삭제를 위한 조건식을 입력해주세요.");
					tInput1.setText("0");
					tInput2.setText("Theater_capacity");
					tInput3.setText("Theater_status");
				} else if (table.equals("Seat")) {
					DInput.setText("삭제를 위한 조건식을 입력해주세요.");
					tInput1.setText("0");
					tInput2.setText("Theater_number");
					tInput3.setText("seat_status");
				} else if (table.equals("Members")) {
					DInput.setText("삭제를 위한 조건식을 입력해주세요.");
					tInput1.setText("member_id");
					tInput2.setText("member_name");
					tInput3.setText("phone_number");
					tInput4.setText("email");
				} else if (table.equals("Schedules")) {
					DInput.setText("삭제를 위한 조건식을 입력해주세요.");
					tInput1.setText("0");
					tInput2.setText("movie_number");
					tInput3.setText("Theater_number");
					tInput4.setText("start_date");
					tInput5.setText("screen_day");
					tInput6.setText("screen_round");
					tInput7.setText("show_time");
					tInput8.setText("show_date");
				} else if (table.equals("Reservation")) {
					DInput.setText("삭제를 위한 조건식을 입력해주세요.");
					tInput1.setText("0");
					tInput2.setText("payment_method");
					tInput3.setText("payment_status");
					tInput4.setText("payment_amount");
					tInput5.setText("member_id");
					tInput6.setText("payment_date");
				} else if (table.equals("Ticket")) {
					DInput.setText("삭제를 위한 조건식을 입력해주세요.");
					tInput1.setText("0");
					tInput2.setText("schedule_number");
					tInput3.setText("Theater_number");
					tInput4.setText("seat_number");
					tInput5.setText("reservation_number");
					tInput6.setText("ticket_status");
					tInput7.setText("standard_price");
					tInput8.setText("sale_price");
				}
			}
		}
	}

	class BtListener3 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			// String ques = (String) Ctable2.getSelectedItem();
			String str1 = tf1.getText();
			String str2 = tf2.getText();
			String str3 = tf3.getText();
			String str4 = tf4.getText();
			String ques = "SELECT * FROM Movies WHERE ";
			int i = 0;

			// 여기다가 버튼을 눌렀을 때 할 일 지정
			if (b.getText().equals("조회")) { // 조회 버튼
				// MySQL과 프로젝트 연결
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");

					String url = "jdbc:mysql://localhost/madang";

					conn = DriverManager.getConnection(url, "madang", "madang");

					stmt = conn.createStatement();

					ta.setText(""); // TextArea 초기화

					if (!str1.equals("영화명") && !str1.equals("")) {
						ques += "movie_title LIKE \'%" + str1 + "%\'";
						i++;
					}
					if (!str2.equals("감독명") && !str2.equals("")) {
						if (i != 0)
							ques += " AND ";
						ques += "director LIKE \'%" + str2 + "%\'";
						i++;
					}
					if (!str3.equals("배우명") && !str3.equals("")) {
						if (i != 0)
							ques += " AND ";
						ques += "actor LIKE \'%" + str3 + "%\'";
						i++;
					}
					if (!str4.equals("장르") && !str4.equals("")) {
						if (i != 0)
							ques += " AND ";
						ques += "genre LIKE \'%" + str4 + "%\'";
						i++;
					}

					rs = stmt.executeQuery(ques);

					// 선택에 따른 분기
					/*
					 * if (ques.equals("영화명")) { rs =
					 * stmt.executeQuery("SELECT * FROM Movies WHERE movie_title LIKE \'%" + str1 +
					 * "%\'"); } else if (ques.equals("감독명")) { rs =
					 * stmt.executeQuery("SELECT * FROM Movies WHERE director LIKE \'%" + str1 +
					 * "%\'"); } else if (ques.equals("배우명")) { rs =
					 * stmt.executeQuery("SELECT * FROM Movies WHERE actor LIKE \'%" + str1 +
					 * "%\'"); } else if (ques.equals("장르")) { rs =
					 * stmt.executeQuery("SELECT * FROM Movies WHERE genre LIKE \'%" + str1 +
					 * "%\'"); }
					 */

					Ctable2.removeAllItems();
					ta.append(
							"movie_number movie_title running_time movie_rating director actor genre movie_introduction release_date\n");
					while (rs.next()) {
						ta.append(rs.getString("movie_number") + "  " + rs.getString("movie_title") + "  "
								+ rs.getString("running_time") + "  " + rs.getString("movie_rating") + "  "
								+ rs.getString("director") + "  " + rs.getString("actor") + "  " + rs.getString("genre")
								+ "  " + rs.getString("movie_introduction") + "  " + rs.getString("release_date")
								+ "\n\n");
						Ctable2.addItem(rs.getString("movie_title"));
					}
				} catch (ClassNotFoundException e1) {
					// 드라이버 로딩 실패 System.out.println("드라이버 로딩 실패");
				} catch (SQLException e1) {
					// 에러 System.out.println("에러: " + e1);
					ta.setText("검색 결과가 없습니다.");
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} else if (b.getText().equals("예매")) { // 예매 버튼
				if (Ctable2.getItemCount() != 0) {
					new Ticketing(Ctable2.getSelectedItem().toString());
				}
			} else if (b.getText().equals("예매 조회")) { // 예매 버튼
				
				// MySQL과 프로젝트 연결
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");

					String url = "jdbc:mysql://localhost/madang";

					conn = DriverManager.getConnection(url, "madang", "madang");
					conn2 = DriverManager.getConnection(url, "madang", "madang");
					conn3 = DriverManager.getConnection(url, "madang", "madang");
					conn4 = DriverManager.getConnection(url, "madang", "madang");
					conn5 = DriverManager.getConnection(url, "madang", "madang");

					stmt = conn.createStatement();
					stmt2 = conn2.createStatement();
					stmt3 = conn3.createStatement();
					stmt4 = conn4.createStatement();
					stmt5 = conn5.createStatement();

					ta.setText("");

					rs = stmt.executeQuery("select * from Members where member_id=\'" + tf5.getText() + "\';");

					// 아이디가 데이터베이스에 존재하면 다음 페이지로 넘어감.
					if (rs.next()) {
						// 성공
						
						String mt="", sd="", tn="", sn="", sp="", rid="";
						Ctable3.removeAllItems();
						Ctable4.removeAllItems();
						
						rs2 = stmt2.executeQuery("select * from Reservation where member_id=\'" + tf5.getText() + "\';");
						
						
						// movie_title  show_date  theater_number seat_number sale_price
						ta.setText("고유번호 영화명  상영일  상영관번호  좌석번호  판매가격\n");
						while (rs2.next()) {
							ResultSet rs3 = null;
							ResultSet rs4 = null;
							ResultSet rs5 = null;
							rid = rs2.getString("reservation_number");
							rs3 = stmt3.executeQuery("select * from Ticket where reservation_number=" + rs2.getString("reservation_number") + ";");
							if(rs3.next()) { // 티켓
								tn = rs3.getString("theater_number");
								sn = rs3.getString("seat_number");
								sp = rs3.getString("sale_price");
								rs4 = stmt4.executeQuery("select * from Schedules where schedule_number=" + rs3.getString("schedule_number") +";");
								if(rs4.next()) {
									sd = rs4.getString("show_date");
									rs5 = stmt5.executeQuery("select * from Movies where movie_number=" + rs4.getString("movie_number") + ";");
									if(rs5.next()) {
										mt = rs5.getString("movie_title");
									}
								}
							}
							
							ta.append(rid + " | " +mt + "  " + sd + "  "
									+ tn + "  " + sn + "  "
									+ sp + "  "  + "\n");
							Ctable3.addItem(rid);
							Ctable4.addItem(rid);
						}
						tf5.setText("ID");
					}

				} catch (ClassNotFoundException e1) {
					// 드라이버 로딩 실패 System.out.println("드라이버 로딩 실패");
					// 실패
				} catch (SQLException e1) {
					// 에러 System.out.println("에러: " + e1);
					// 실패
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} else if (b.getText().equals("삭제")) { // 예매 버튼
				if (Ctable4.getItemCount() != 0) {
					String rid = (String) Ctable3.getSelectedItem();
					// MySQL과 프로젝트 연결
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
	
						String url = "jdbc:mysql://localhost/madang";
	
						conn = DriverManager.getConnection(url, "madang", "madang");
	
						stmt = conn.createStatement();
	
						ta.setText(""); // TextArea 초기화
	
						stmt.execute("DELETE FROM Reservation WHERE reservation_number=" + rid + ";");
	
						ta.setText("예매기록을 성공적으로 삭제했습니다."); // 삭제 성공
						Ctable3.removeAllItems();
						Ctable4.removeAllItems();
					} catch (ClassNotFoundException e1) {
						// 드라이버 로딩 실패 System.out.println("드라이버 로딩 실패");
					} catch (SQLException e1) {
						// 에러 System.out.println("에러: " + e1);
						ta.setText("예매기록 삭제에 실패했습니다."); // 삭제 실패
					} finally {
						try {
							if (conn != null && !conn.isClosed()) {
								conn.close();
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			} else if (b.getText().equals("변경")) { // 예매 버튼
				if (Ctable4.getItemCount() != 0) {
					new Change2((String) Ctable4.getSelectedItem());
				}
			}
		}
	}


	public JC20003201M(int mode) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// mode 1:유저 2:관리자
		// 여기부터 자바 swing을 이용해서 인터페이스 구성
		if (mode == 2) { // 관리자 모드
			c.setLayout(null);

			JButton bR = new JButton("초기화");
			JButton bA = new JButton("전체 테이블 보기");
			JButton b1 = new JButton("입력 실행");
			JButton b2 = new JButton("삭제");
			JButton b3 = new JButton("변경");
			JButton b4 = new JButton("입력 취소");

			sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

			// (x, y, x길이, y길이)
			bR.setBounds(20, 10, 100, 30);
			bA.setBounds(135, 10, 150, 30);
			b1.setBounds(1000, 10, 120, 30);
			b2.setBounds(765, 10, 100, 30);
			b3.setBounds(880, 10, 100, 30);
			b4.setBounds(1000, 400, 120, 30);
			Ctable.setBounds(1000, 50, 120, 20);
			// Ccol.setBounds(1000, 70, 120, 20);
			tInput1.setBounds(1000, 100, 120, 20);
			tInput2.setBounds(1000, 130, 120, 20);
			tInput3.setBounds(1000, 160, 120, 20);
			tInput4.setBounds(1000, 190, 120, 20);
			tInput5.setBounds(1000, 220, 120, 20);
			tInput6.setBounds(1000, 250, 120, 20);
			tInput7.setBounds(1000, 280, 120, 20);
			tInput8.setBounds(1000, 310, 120, 20);
			tInput9.setBounds(1000, 340, 120, 20);
			DInput.setBounds(500, 15, 250, 20);
			sp.setBounds(10, 50, 985, 410);
			la1.setBounds(300, 15, 250, 20);

			c.add(bR);
			c.add(bA);
			c.add(b1);
			c.add(b2);
			c.add(b3);
			c.add(b4);
			c.add(Ctable);
			c.add(tInput1);
			c.add(tInput2);
			c.add(tInput3);
			c.add(tInput4);
			c.add(tInput5);
			c.add(tInput6);
			c.add(tInput7);
			c.add(tInput8);
			c.add(tInput9);
			c.add(DInput);
			// c.add(Ccol);
			c.add(sp);
			c.add(la1);

			Ctable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedIndex = Ctable.getSelectedIndex();
					if (selectedIndex == 0) {
						// DInput.setText("삭제를 위한 조건식을 입력해주세요. (현재 선택 테이블 : Movies)");
						tInput1.setText("0");
						tInput2.setText("movie_title");
						tInput3.setText("running_time");
						tInput4.setText("movie_rating");
						tInput5.setText("director");
						tInput6.setText("actor");
						tInput7.setText("genre");
						tInput8.setText("movie_introduction");
						tInput9.setText("release_date");
						tInput1.setVisible(false);
						tInput4.setVisible(true);
						tInput5.setVisible(true);
						tInput6.setVisible(true);
						tInput7.setVisible(true);
						tInput8.setVisible(true);
						tInput9.setVisible(true);
						la1.setText("DELETE  FROM  Movies  WHERE ");
						/*
						 * Ccol.removeAllItems(); Ccol.addItem(colList1[0]); Ccol.addItem(colList1[1]);
						 * Ccol.addItem(colList1[2]); Ccol.addItem(colList1[3]);
						 * Ccol.addItem(colList1[4]); Ccol.addItem(colList1[5]);
						 * Ccol.addItem(colList1[6]); Ccol.addItem(colList1[7]);
						 * Ccol.addItem(colList1[8])
						 */
					} else if (selectedIndex == 1) {
						// DInput.setText("삭제를 위한 조건식을 입력해주세요. (현재 선택 테이블 : Theater)");
						tInput1.setText("0");
						tInput2.setText("Theater_capacity");
						tInput3.setText("Theater_status");
						tInput1.setVisible(false);
						tInput4.setVisible(false);
						tInput5.setVisible(false);
						tInput6.setVisible(false);
						tInput7.setVisible(false);
						tInput8.setVisible(false);
						tInput9.setVisible(false);
						la1.setText("DELETE  FROM  Theater  WHERE ");
						/*
						 * Ccol.removeAllItems(); Ccol.addItem(colList2[0]); Ccol.addItem(colList2[1]);
						 * Ccol.addItem(colList2[2]);
						 */
					} else if (selectedIndex == 2) {
						// DInput.setText("삭제를 위한 조건식을 입력해주세요. (현재 선택 테이블 : Seat)");
						tInput1.setText("0");
						tInput2.setText("Theater_number");
						tInput3.setText("seat_status");
						la1.setText("DELETE  FROM  Seat  WHERE ");
						tInput1.setVisible(false);
						tInput4.setVisible(false);
						tInput5.setVisible(false);
						tInput6.setVisible(false);
						tInput7.setVisible(false);
						tInput8.setVisible(false);
						tInput9.setVisible(false);
						/*
						 * Ccol.removeAllItems(); Ccol.addItem(colList3[0]); Ccol.addItem(colList3[1]);
						 * Ccol.addItem(colList3[2]);
						 */
					} else if (selectedIndex == 3) {
						// DInput.setText("삭제를 위한 조건식을 입력해주세요. (현재 선택 테이블 : Members)");
						tInput1.setText("member_id");
						tInput2.setText("member_name");
						tInput3.setText("phone_number");
						tInput4.setText("email");
						la1.setText("DELETE  FROM  Members  WHERE ");
						tInput1.setVisible(true);
						tInput4.setVisible(true);
						tInput5.setVisible(false);
						tInput6.setVisible(false);
						tInput7.setVisible(false);
						tInput8.setVisible(false);
						tInput9.setVisible(false);
						/*
						 * Ccol.removeAllItems(); Ccol.addItem(colList4[0]); Ccol.addItem(colList4[1]);
						 * Ccol.addItem(colList4[2]); Ccol.addItem(colList4[3]);
						 */
					} else if (selectedIndex == 4) {
						// DInput.setText("삭제를 위한 조건식을 입력해주세요. (현재 선택 테이블 : Schedules)");
						tInput1.setText("0");
						tInput2.setText("movie_number");
						tInput3.setText("Theater_number");
						tInput4.setText("start_date");
						tInput5.setText("screen_day");
						tInput6.setText("screen_round");
						tInput7.setText("show_time");
						tInput8.setText("show_date");
						la1.setText("DELETE  FROM  Schedules  WHERE ");
						tInput1.setVisible(false);
						tInput4.setVisible(true);
						tInput5.setVisible(true);
						tInput6.setVisible(true);
						tInput7.setVisible(true);
						tInput8.setVisible(true);
						tInput9.setVisible(false);
						/*
						 * Ccol.removeAllItems(); Ccol.addItem(colList5[0]); Ccol.addItem(colList5[1]);
						 * Ccol.addItem(colList5[2]); Ccol.addItem(colList5[3]);
						 * Ccol.addItem(colList5[4]); Ccol.addItem(colList5[5]);
						 * Ccol.addItem(colList5[6]); Ccol.addItem(colList5[7]);
						 */
					} else if (selectedIndex == 5) {
						// DInput.setText("삭제를 위한 조건식을 입력해주세요. (현재 선택 테이블 : Reservation)");
						tInput1.setText("0");
						tInput2.setText("payment_method");
						tInput3.setText("payment_status");
						tInput4.setText("payment_amount");
						tInput5.setText("member_id");
						tInput6.setText("payment_date");
						la1.setText("DELETE FROM Reservation WHERE ");
						tInput1.setVisible(false);
						tInput7.setVisible(false);
						tInput8.setVisible(false);
						tInput9.setVisible(false);
						/*
						 * Ccol.removeAllItems(); Ccol.addItem(colList6[0]); Ccol.addItem(colList6[1]);
						 * Ccol.addItem(colList6[2]); Ccol.addItem(colList6[3]);
						 * Ccol.addItem(colList6[4]); Ccol.addItem(colList6[5]);
						 */
					} else if (selectedIndex == 6) {
						// DInput.setText("삭제를 위한 조건식을 입력해주세요. (현재 선택 테이블 : Ticket)");
						tInput1.setText("0");
						tInput2.setText("schedule_number");
						tInput3.setText("Theater_number");
						tInput4.setText("seat_number");
						tInput5.setText("reservation_number");
						tInput6.setText("ticket_status");
						tInput7.setText("standard_price");
						tInput8.setText("sale_price");
						la1.setText("DELETE  FROM  Ticket  WHERE ");
						tInput1.setVisible(false);
						tInput4.setVisible(true);
						tInput5.setVisible(true);
						tInput6.setVisible(true);
						tInput7.setVisible(true);
						tInput8.setVisible(true);
						tInput9.setVisible(false);
						/*
						 * Ccol.removeAllItems(); Ccol.addItem(colList7[0]); Ccol.addItem(colList7[1]);
						 * Ccol.addItem(colList7[2]); Ccol.addItem(colList7[3]);
						 * Ccol.addItem(colList7[4]); Ccol.addItem(colList7[5]);
						 * Ccol.addItem(colList7[6]); Ccol.addItem(colList7[7]);
						 */
					}
				}
			});
			bR.addActionListener(new BtListener());
			bA.addActionListener(new BtListener());
			b1.addActionListener(new BtListener());
			b2.addActionListener(new BtListener());
			b3.addActionListener(new BtListener());
			b4.addActionListener(new BtListener());

			setSize(1150, 500);
			setVisible(true);

			ta.setEditable(false);
			ta.setLineWrap(true);
			tInput1.setVisible(false);
			tInput1.setText("0");
			tInput2.setText("movie_title");
			tInput3.setText("running_time");
			tInput4.setText("movie_rating");
			tInput5.setText("director");
			tInput6.setText("actor");
			tInput7.setText("genre");
			tInput8.setText("movie_introduction");
			tInput9.setText("release_date");
			DInput.setText("조건식을 입력해주세요. (테이블 변경은 오른쪽)");
			la1.setText("DELETE  FROM  Movies  WHERE ");

			sp.setVisible(true);
			sp.setViewportView(ta);

			setTitle("20003201/김성민");
		} else { // 회원 모드
			c.setLayout(null);

			JButton bt1 = new JButton("조회");
			JButton bt2 = new JButton("예매");
			JButton bt3 = new JButton("예매 조회");
			JButton bt4 = new JButton("삭제");
			JButton bt5 = new JButton("변경");

			sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

			// (x, y, x길이, y길이)
			bt1.setBounds(510, 5, 100, 35);
			bt2.setBounds(1000, 5, 110, 35);
			bt3.setBounds(1000, 110, 110, 35);
			bt4.setBounds(1000, 215, 110, 35);
			bt5.setBounds(1000, 320, 110, 35);
			tf1.setBounds(10, 15, 110, 20);
			Ctable2.setBounds(1000, 45, 110, 20);
			Ctable3.setBounds(1000, 255, 110, 20);
			Ctable4.setBounds(1000, 360, 110, 20);
			tf2.setBounds(135, 15, 110, 20);
			tf3.setBounds(260, 15, 110, 20);
			tf4.setBounds(385, 15, 110, 20);
			tf5.setBounds(1000, 150, 110, 20);
			sp.setBounds(10, 50, 985, 410);

			// add
			c.add(sp);
			c.add(bt1);
			c.add(bt2);
			c.add(bt3);
			c.add(bt4);
			c.add(bt5);
			c.add(tf1);
			c.add(tf2);
			c.add(tf3);
			c.add(tf4);
			c.add(tf5);
			c.add(Ctable2);
			c.add(Ctable3);
			c.add(Ctable4);

			// 이어서 해야할 일
			// 히원 기능 구현
			// 회원 로그인기능 없애기
			// 회원 검색기능 구현 ㅇ
			// 데이터베이스 티켓같은거 수정

			// set text
			tf1.setText("영화명");
			tf2.setText("감독명");
			tf3.setText("배우명");
			tf4.setText("장르");
			tf5.setText("ID");

			// listener
			bt1.addActionListener(new BtListener3());
			bt2.addActionListener(new BtListener3());
			bt3.addActionListener(new BtListener3());
			bt4.addActionListener(new BtListener3());
			bt5.addActionListener(new BtListener3());

			// setting
			ta.setEditable(false);
			ta.setLineWrap(true);
			setSize(1140, 500);
			setVisible(true);
			sp.setVisible(true);
			sp.setViewportView(ta);

			setTitle("20003201/김성민");
		}
	}

	public static void main(String[] args) {
		// 데이터베이스 초기화

		// MYSQL 객체
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		// MySQL과 프로젝트 연결
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost/madang";

			conn = DriverManager.getConnection(url, "madang", "madang");

			stmt = conn.createStatement();

			// drop database
			stmt.executeUpdate("DROP DATABASE IF EXISTS madang;");
			stmt.executeUpdate("create database madang;");
			stmt.executeUpdate("grant all privileges on madang.* to madang@localhost with grant option;");
			stmt.executeUpdate("commit;");
			stmt.executeUpdate("USE madang;");
			stmt.executeUpdate("SET SQL_SAFE_UPDATES = 0;");

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `madang`.`Movies` (\r\n"
					+ "  `movie_number` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `movie_title` VARCHAR(100) NOT NULL,\r\n"
					+ "  `running_time` TIME NOT NULL,\r\n" + "  `movie_rating` VARCHAR(15) NOT NULL,\r\n"
					+ "  `director` VARCHAR(45) NOT NULL,\r\n" + "  `actor` VARCHAR(200) NOT NULL,\r\n"
					+ "  `genre` VARCHAR(45) NOT NULL,\r\n" + "  `movie_introduction` VARCHAR(2000) NOT NULL,\r\n"
					+ "  `release_date` DATE NOT NULL,\r\n" + "  PRIMARY KEY (`movie_number`)\r\n" + ");");

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `madang`.`Theater` (\r\n"
					+ "  `Theater_number` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `Theater_capacity` INT NOT NULL,\r\n"
					+ "  `Theater_status` VARCHAR(4) NOT NULL,\r\n" + "  PRIMARY KEY (`Theater_number`)\r\n" + ");");

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `madang`.`Seat` (\r\n"
					+ "  `seat_number` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `Theater_number` INT NOT NULL,\r\n"
					+ "  `seat_status` VARCHAR(4) NOT NULL,\r\n"
					+ "  PRIMARY KEY (`seat_number`, `Theater_number`),\r\n"
					+ "  INDEX `fk_seat_Theater1_idx` (`Theater_number` ASC) VISIBLE,\r\n"
					+ "  CONSTRAINT `fk_seat_Theater1`\r\n" + "    FOREIGN KEY (`Theater_number`)\r\n"
					+ "    REFERENCES `madang`.`Theater` (`Theater_number`)\r\n" + "    ON DELETE CASCADE\r\n"
					+ "    ON UPDATE CASCADE\r\n" + ");");

			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS `madang`.`Members` (\r\n" + "  `member_id` VARCHAR(45) NOT NULL,\r\n"
							+ "  `member_name` VARCHAR(45) NOT NULL,\r\n" + "  `phone_number` VARCHAR(45) NOT NULL,\r\n"
							+ "  `email` VARCHAR(45) NOT NULL,\r\n" + "  PRIMARY KEY (`member_id`)\r\n" + ");");

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `madang`.`Schedules` (\r\n"
					+ "  `schedule_number` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `movie_number` INT NOT NULL,\r\n"
					+ "  `Theater_number` INT NOT NULL,\r\n" + "  `start_date` DATE NOT NULL,\r\n"
					+ "  `screen_day` VARCHAR(45) NOT NULL,\r\n" + "  `screen_round` INT NOT NULL,\r\n"
					+ "  `show_time` TIME NOT NULL,\r\n" + "  `show_date` DATE NOT NULL,\r\n"
					+ "  PRIMARY KEY (`schedule_number`),\r\n"
					+ "  INDEX `fk_schedule_movie_idx` (`movie_number` ASC) VISIBLE,\r\n"
					+ "  INDEX `fk_schedule_Theater1_idx` (`Theater_number` ASC) VISIBLE,\r\n"
					+ "  CONSTRAINT `fk_schedule_movie`\r\n" + "    FOREIGN KEY (`movie_number`)\r\n"
					+ "    REFERENCES `madang`.`Movies` (`movie_number`)\r\n" + "    ON DELETE CASCADE\r\n"
					+ "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `fk_schedule_Theater1`\r\n"
					+ "    FOREIGN KEY (`Theater_number`)\r\n"
					+ "    REFERENCES `madang`.`Theater` (`Theater_number`)\r\n" + "    ON DELETE CASCADE\r\n"
					+ "    ON UPDATE CASCADE\r\n" + ");");

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `madang`.`Reservation` (\r\n"
					+ "  `reservation_number` INT NOT NULL AUTO_INCREMENT,\r\n"
					+ "  `payment_method` VARCHAR(45) NOT NULL,\r\n" + "  `payment_status` VARCHAR(45) NOT NULL,\r\n"
					+ "  `payment_amount` INT NOT NULL,\r\n" + "  `member_id` VARCHAR(45) NOT NULL,\r\n"
					+ "  `payment_date` DATETIME NOT NULL,\r\n" + "  PRIMARY KEY (`reservation_number`),\r\n"
					+ "  INDEX `fk_reservation_member1_idx` (`member_id` ASC) VISIBLE,\r\n"
					+ "  CONSTRAINT `fk_reservation_member1`\r\n" + "    FOREIGN KEY (`member_id`)\r\n"
					+ "    REFERENCES `madang`.`Members` (`member_id`)\r\n" + "    ON DELETE CASCADE\r\n"
					+ "    ON UPDATE CASCADE\r\n" + ");");

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `madang`.`Ticket` (\r\n"
					+ "  `ticket_number` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `schedule_number` INT NOT NULL,\r\n"
					+ "  `Theater_number` INT NOT NULL,\r\n" + "  `seat_number` INT NOT NULL,\r\n"
					+ "  `reservation_number` INT NOT NULL,\r\n" + "  `ticket_status` VARCHAR(4) NOT NULL,\r\n"
					+ "  `standard_price` INT NOT NULL,\r\n" + "  `sale_price` INT NOT NULL,\r\n"
					+ "  PRIMARY KEY (`ticket_number`),\r\n"
					+ "  INDEX `fk_ticket_seat1_idx` (`seat_number` ASC, `Theater_number` ASC) VISIBLE,\r\n"
					+ "  INDEX `fk_ticket_schedule1_idx` (`schedule_number` ASC) VISIBLE,\r\n"
					+ "  INDEX `fk_ticket_reservation1_idx` (`reservation_number` ASC) VISIBLE,\r\n"
					+ "  CONSTRAINT `fk_ticket_seat1`\r\n" + "    FOREIGN KEY (`seat_number` , `Theater_number`)\r\n"
					+ "    REFERENCES `madang`.`Seat` (`seat_number` , `Theater_number`)\r\n"
					+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE CASCADE,\r\n"
					+ "  CONSTRAINT `fk_ticket_schedule1`\r\n" + "    FOREIGN KEY (`schedule_number`)\r\n"
					+ "    REFERENCES `madang`.`Schedules` (`schedule_number`)\r\n" + "    ON DELETE CASCADE\r\n"
					+ "    ON UPDATE CASCADE,\r\n" + "  CONSTRAINT `fk_ticket_reservation1`\r\n"
					+ "    FOREIGN KEY (`reservation_number`)\r\n"
					+ "    REFERENCES `madang`.`Reservation` (`reservation_number`)\r\n" + "    ON DELETE CASCADE\r\n"
					+ "    ON UPDATE CASCADE\r\n" + ");");

			stmt.executeUpdate("ALTER TABLE Movies AUTO_INCREMENT=10001;");
			stmt.executeUpdate("ALTER TABLE Theater AUTO_INCREMENT=20001;");
			stmt.executeUpdate("ALTER TABLE Seat AUTO_INCREMENT=30001;");
			stmt.executeUpdate("ALTER TABLE Schedules AUTO_INCREMENT=50001;");
			stmt.executeUpdate("ALTER TABLE Reservation AUTO_INCREMENT=60001;");
			stmt.executeUpdate("ALTER TABLE Ticket AUTO_INCREMENT=70001;");

			// 여기서부터 입력할 데이터 쿼리문으로 쭉 쏴주면 됨.
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '범죄도시2', '01:46:00', '15세', '이상용','마동석, 손석구, 최귀화','범죄, 액션','가리봉동 소탕작전 후 4년 뒤, 금천서 강력반은 베트남으로 도주한 용의자를 인도받아 오라는 미션을 받는다. 괴물형사 ‘마석도’(마동석)와 ‘전일만’(최귀화) 반장은 현지 용의자에게서 수상함을 느끼고, 그의 뒤에 무자비한 악행을 벌이는 ‘강해상’(손석구)이 있음을 알게 된다. ‘마석도’와 금천서 강력반은 한국과 베트남을 오가며 역대급 범죄를 저지르는 ‘강해상’을 본격적으로 쫓기 시작하는데... 나쁜 놈들 잡는 데 국경 없다! 통쾌하고 화끈한 범죄 소탕 작전이 다시 펼쳐진다!','2021-01-01');");
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '닥터스트레인지', '02:06:00', '12세', '샘 레이미','베네딕트 컴버배치, 엘리자베스 올슨','액션','끝없이 균열되는 차원과 뒤엉킨 시공간의 멀티버스가 열리며 오랜 동료들, 그리고 차원을 넘어 들어온 새로운 존재들을 맞닥뜨리게 된 ‘닥터 스트레인지’. 대혼돈 속, 그는 예상치 못한 극한의 적과 맞서 싸워야만 하는데….','2021-02-02');\r\n");
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '피는 물보다 진하다', '01:30:00', '15세', '김희성','조동혁, 이완, 임정은','액션','최대 청부폭력조직 백정파는 무자비함으로 악명 높은 해결사, 일명 도깨비를 앞세워 일대를 장악한다. 그러나, 베일에 싸인 ‘도깨비’ 두현은 친형제 같았던 영민의 죄를 뒤집어쓰고 10년의 수감생활을 하게 된다. 한편, 두현이 사라진 사이, ‘도깨비＇행세를 하며 조직을 차지한 영민은 두현의 출소 소식을 듣고 불안에 휩싸인다. 영민은 두현을 먼저 치기로 하고 새 삶을 시작하려던 두현은 결국, 진짜 ‘도깨비’의 부활을 선언하며 영민과 조직을 상대로 싸움을 시작하는데...','2021-03-03');\r\n");
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '안녕하세요', '01:58:00', '전체관람가', '차봉주','김환희, 유선, 이순재','드라마','외로운 세상 속에서 죽음을 결심한 열아홉 수미(김환희). ‘죽는 법’을 알려주겠다는 수간호사 서진(유선)의 제안에 따라 늘봄 호스피스 병동을 찾아간다. 이 사람들이 곧 죽을 사람들이라고?! 예상치 못한 유쾌함과 따뜻함이 수미를 반기고, 하루하루를 소중히 살아가는 그들에게 점차 스며들며 세상의 온기를 배워가기 시작하는데… ‘힐링 메이트’들이 전하는 눈부시게 찬란한 위로! “안녕하세요”','2021-04-04');\r\n");
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '아치의 노래, 정태춘', '01:53:00', '15세', '고영재','정태춘, 박은옥','다큐멘터리','10대 가수상, 가요 사전심의 철폐운동 그리고 음악시장을 홀연히 떠나기까지 어디서도 들어보지 못한 노랫말과 서정적인 음율로 시대와 함께한 뮤지션 데뷔 40주년, 우리가 몰랐던 정태춘의 음악과 삶을 만나다!','2021-05-05');\r\n");

			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '오마주', '01:48:00', '12세', '신수원','이정은, 권해효, 탕준상','드라마','엄마 영화는 재미없다는 아들과 늘상 밥타령인 남편, 잇따른 흥행 실패로 슬럼프에 빠진 중년의 영화감독 지완. 아르바이트 삼아 60년대에 활동한 한국 두 번째 여성 영화감독 홍은원 감독의 작품 <여판사>의 필름을 복원하게 된다. 사라진 필름을 찾아 홍감독의 마지막 행적을 따라가던 지완은 정체를 알 수 없는 모자 쓴 여성의 그림자와 함께 그 시간 속을 여행하게 되는데... 어쩐지, 희미해진 꿈과 영화에 대한 열정이 되살아나는 것만 같다. ','2021-06-04');\r\n");
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '쥬라기 월드: 도미니언', '02:27:00', '12세', '콜린 트레보로우','크리스 프랫, 브라이스 달라스 하워드','액션','공룡들의 터전이었던 이슬라 누블라 섬이 파괴된 후, 마침내 공룡들은 섬을 벗어나 세상 밖으로 출몰한다. 지상에 함께 존재해선 안 될 위협적 생명체인 공룡의 등장으로 인류 역사상 겪어보지 못한 사상 최악의 위기를 맞이한 인간들. 지구의 최상위 포식자 자리를 걸고 인간과 공룡의 최후의 사투가 펼쳐진다.','2021-07-01');\r\n");
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '베르네 부인의 장미정원', '01:35:00', '12세', '피에르 피노드','카트린 프로, 팟사 부야메드, 올리비아 코트','드라마','프랑스 최고의 원예사 에브 베르네는 대를 이어 장미정원을 운영해오고 있다. 하지만 장미를 공산품 취급하는 사업가 라마르젤에 밀려 명성과 고객은 물론, 자신의 정원까지 모두 빼앗길 위기에 처한다. 신입 원예사를 뽑아 정원을 지키려 하지만 경력도 지식도 없는 초짜 직원들은 문제를 일으키기만 하는데... 과연 에브와 원예 초보 신입들은 정원을 구해낼 수 있을까?','2021-08-01');\r\n");
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '노트북', '02:03:00', '15세', '닉 카사베츠','라이언 고슬링, 레이첼 맥아담스','멜로/로맨스','17살, ‘노아’는 밝고 순수한 ‘앨리’를 보고 첫눈에 반한다. 빠른 속도로 서로에게 빠져드는 둘. 그러나 이들 앞에 놓인 장벽에 막혀 이별하게 된다. 24살, ‘앨리’는 우연히 신문에서 ‘노아’의 소식을 접하고 잊을 수 없는 첫사랑 앞에서 다시 한 번 선택의 기로에 서게 되는데… 열일곱의 설렘, 스물넷의 아픈 기억, 그리고 마지막까지… 한 사람을 지극히 사랑했으니 내 인생은 성공한 인생입니다','2021-09-10');\r\n");
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '이터널 선샤인', '01:47:00', '15세', '미셸 공드리','짐 캐리, 케이트 윈슬렛, 커스틴 던스트','멜로/로맨스','조엘은 아픈 기억만을 지워준다는 라쿠나사를 찾아가 헤어진 연인 클레멘타인의 기억을 지우기로 결심한다. 기억이 사라져 갈수록 조엘은 사랑이 시작되던 순간, 행복한 기억들, 가슴 속에 각인된 추억들을 지우기 싫어지기만 하는데... 당신을 지우면 이 아픔도 사라질까요? 사랑은 그렇게 다시 기억된다.','2021-10-10');\r\n");

			stmt.executeUpdate("INSERT INTO Theater VALUES(0, 50 ,'Y');");
			stmt.executeUpdate("INSERT INTO Theater VALUES(0, 80 ,'Y');");
			stmt.executeUpdate("INSERT INTO Theater VALUES(0, 50 ,'Y');");
			stmt.executeUpdate("INSERT INTO Theater VALUES(0, 50 ,'Y');");
			stmt.executeUpdate("INSERT INTO Theater VALUES(0, 30 ,'Y');");

			stmt.executeUpdate("INSERT INTO Theater VALUES(0, 50 ,'Y');");
			stmt.executeUpdate("INSERT INTO Theater VALUES(0, 40 ,'Y');");
			stmt.executeUpdate("INSERT INTO Theater VALUES(0, 30 ,'Y');");
			stmt.executeUpdate("INSERT INTO Theater VALUES(0, 30 ,'Y');");
			stmt.executeUpdate("INSERT INTO Theater VALUES(0, 50 ,'Y');");

			for (int i = 0; i < 50; i++)
				stmt.executeUpdate("INSERT INTO Seat VALUES(0,20001,'N');");
			for (int i = 0; i < 80; i++)
				stmt.executeUpdate("INSERT INTO Seat VALUES(0,20002,'N');");
			for (int i = 0; i < 50; i++)
				stmt.executeUpdate("INSERT INTO Seat VALUES(0,20003,'N');");
			for (int i = 0; i < 50; i++)
				stmt.executeUpdate("INSERT INTO Seat VALUES(0,20004,'N');");
			for (int i = 0; i < 30; i++)
				stmt.executeUpdate("INSERT INTO Seat VALUES(0,20005,'N');");

			for (int i = 0; i < 50; i++)
				stmt.executeUpdate("INSERT INTO Seat VALUES(0,20006,'N');");
			for (int i = 0; i < 40; i++)
				stmt.executeUpdate("INSERT INTO Seat VALUES(0,20007,'N');");
			for (int i = 0; i < 30; i++)
				stmt.executeUpdate("INSERT INTO Seat VALUES(0,20008,'N');");
			for (int i = 0; i < 30; i++)
				stmt.executeUpdate("INSERT INTO Seat VALUES(0,20009,'N');");
			for (int i = 0; i < 50; i++)
				stmt.executeUpdate("INSERT INTO Seat VALUES(0,20010,'N');");

			stmt.executeUpdate("INSERT INTO Members VALUES('bsu0404','변성은','010-3854-7009','bsu0404@naver.com');");
			stmt.executeUpdate("INSERT INTO Members VALUES('ksm0101','김성민','010-1234-5678','ksm0101@naver.com');");
			stmt.executeUpdate("INSERT INTO Members VALUES('kmk0412','김민경','010-1523-8956','kmk2364@naver.com');");
			stmt.executeUpdate("INSERT INTO Members VALUES('jyj0328','정유진','010-7852-2145','jyj0328@naver.com');");
			stmt.executeUpdate("INSERT INTO Members VALUES('jye0428','조예은','010-5266-7851','jye0428@naver.com');");

			stmt.executeUpdate("INSERT INTO Members VALUES('lwb1003','이수빈','010-2569-9685','lwb1003@naver.com');");
			stmt.executeUpdate("INSERT INTO Members VALUES('lhy0909','이하연','010-5682-4538','lhy0909@naver.com');");
			stmt.executeUpdate("INSERT INTO Members VALUES('lhj0911','이현정','010-9089-1932','lhj0911@naver.com');");
			stmt.executeUpdate("INSERT INTO Members VALUES('ksy0626','권수연','010-8910-9365','ksy0626@naver.com');");
			stmt.executeUpdate("INSERT INTO Members VALUES('lmk0211','이민경','010-2548-8542','lmk0211@naver.com');");

			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10001,20001,'2021-01-19','일요일','2','17:25:00','2021-01-24');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10002,20002,'2021-02-08','수요일','3','10:40:00','2021-02-05');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10003,20003,'2021-03-28','화요일','1','11:20:00','2021-03-28');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10004,20004,'2021-04-19','일요일','5','09:25:00','2021-04-20');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10005,20005,'2021-05-20','월요일','6','13:50:00','2021-05-23');");

			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10006,20006,'2021-06-13','월요일','2','14:30:00','2021-06-30');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10007,20007,'2021-07-19','일요일','4','19:45:00','2021-07-25');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10008,20008,'2021-08-12','일요일','7','22:20:00','2021-08-26');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10009,20009,'2021-09-15','수요일','3','12:15:00','2021-09-30');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10010,20010,'2021-10-04','토요일','1','15:05:00','2021-10-12');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10001,20002,'2021-11-19','토요일','1','15:05:00','2021-11-18');");

			
			stmt.executeUpdate("INSERT INTO Reservation VALUES(0,'카드','결제완료',10000,'bsu0404','2021-01-19 10:30:00');");
			stmt.executeUpdate("INSERT INTO Reservation VALUES(0,'현금','결제완료',10000,'ksm0101','2021-02-05 11:35:05');");
			stmt.executeUpdate("INSERT INTO Reservation VALUES(0,'카드','결제완료',10000,'kmk0412','2021-03-26 10:20:10');");
			stmt.executeUpdate(
					"INSERT INTO Reservation VALUES(0,'네이버페이','결제대기',10000,'jyj0328','2022-04-19 09:10:07');");
			stmt.executeUpdate(
					"INSERT INTO Reservation VALUES(0,'카카오페이','결제완료',10000,'jye0428','2022-05-19 11:30:08');");

			stmt.executeUpdate("INSERT INTO Reservation VALUES(0,'카드','결제완료',10000,'lwb1003','2021-06-27 10:30:06');");
			stmt.executeUpdate("INSERT INTO Reservation VALUES(0,'카드','결제대기',10000,'lhy0909','2021-07-02 13:30:48');");
			stmt.executeUpdate("INSERT INTO Reservation VALUES(0,'현금','결제완료',10000,'lhj0911','2021-08-02 15:30:45');");
			stmt.executeUpdate("INSERT INTO Reservation VALUES(0,'카드','결제완료',10000,'ksy0626','2021-09-27 16:30:26');");
			stmt.executeUpdate("INSERT INTO Reservation VALUES(0,'현금','결제대기',10000,'lmk0211','2021-10-11 08:30:18');");

			stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50001,20001,30001,60001,'Y',13000,10000);");
			stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50002,20002,30051,60002,'Y',13000,10000);");
			stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50003,20003,30131,60003,'Y',13000,10000);");
			stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50004,20004,30181,60004,'Y',13000,10000);");
			stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50005,20005,30231,60005,'Y',13000,10000);");

			stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50006,20006,30261,60006,'Y',13000,10000);");
			stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50007,20007,30311,60007,'Y',13000,10000);");
			stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50008,20008,30351,60008,'Y',13000,10000);");
			stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50009,20009,30381,60009,'Y',13000,10000);");
			stmt.executeUpdate("INSERT INTO Ticket VALUES(0,50010,20010,30411,60010,'Y',13000,10000);");

			stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30001");
			stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30051");
			stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30131");
			stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30181");
			stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30231");

			stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30261");
			stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30311");
			stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30351");
			stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30381");
			stmt.executeUpdate("UPDATE madang.Seat SET seat_status = 'Y' WHERE seat_number = 30411");

		} catch (ClassNotFoundException e1) {
			// 드라이버 로딩 실패
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e1) {
			// 에러
			System.out.println("에러: " + e1);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		// 데이터 베이스 초기화 완료

		// 프로그램 시작
		new Login();
	}

}

class Login extends JFrame {
	Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Container 객체
		Container c = getContentPane();
		c.setLayout(null);

		// ID 입력 구현했는데 여기서 할 필요 없을 것 같다.

		// Label 객체
		// JLabel l1 = new JLabel("ID");
		// JLabel l2 = new JLabel("잘못된 아이디입니다. 다시 입력해주세요.");

		// TextField, TextArea 객체
		// JTextField tInput1 = new JTextField(30);
		JTextArea ta = new JTextArea(20, 80);
		JScrollPane sp = new JScrollPane(ta);

		// JButton 객체
		JButton b1 = new JButton("회원");
		JButton b2 = new JButton("관리자");

		// (x, y, x길이, y길이)
		// l1.setBounds(20, 50, 100, 20);
		// tInput1.setBounds(50, 50, 200, 20);
		// l2.setBounds(30, 20, 250, 20);
		b1.setBounds(10, 10, 120, 140);
		b2.setBounds(150, 10, 120, 140);
		// c.add(l1);
		// c.add(tInput1);
		// c.add(l2);
		c.add(b1);
		c.add(b2);

		// visible, invisible
		// l2.setVisible(false);

		// Login Button Listener
		class BtListener2 implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton) e.getSource();

				if (b.getText().equals("회원")) {
					setVisible(false);
					new JC20003201M(1);
				} else if (b.getText().equals("관리자")) {
					setVisible(false);
					new JC20003201M(2);
				}
			}
		}

		// Button Listener
		b1.addActionListener(new BtListener2());
		b2.addActionListener(new BtListener2());

		setSize(300, 200);
		setVisible(true);
		setTitle("Login Page");

	}
}

class Change extends JFrame {
	Change() {
		// Container 객체
		Container c = getContentPane();
		c.setLayout(null);

		String tableList[] = { "Movies", "Theater", "Seat", "Members", "Schedules", "Reservation", "Ticket" };

		JComboBox<String> Ctable = new JComboBox<String>(tableList);

		JLabel l1 = new JLabel("UPDATE ");
		JLabel l2 = new JLabel("SET ");
		JLabel l3 = new JLabel("WHERE ");
		JLabel l4 = new JLabel("");

		JTextField Input1 = new JTextField(8);
		JTextField Input2 = new JTextField(8);

		JButton Cb = new JButton("변경");
		Cb.setBounds(80, 130, 120, 20);
		l1.setBounds(30, 30, 120, 20);
		l2.setBounds(30, 60, 120, 20);
		l3.setBounds(30, 90, 120, 20);
		l4.setBounds(50, 5, 200, 20);
		Ctable.setBounds(100, 30, 120, 20);
		Input1.setBounds(100, 60, 180, 20);
		Input2.setBounds(100, 90, 180, 20);
		c.add(Cb);
		c.add(l1);
		c.add(l2);
		c.add(l3);
		c.add(l4);
		c.add(Ctable);
		c.add(Input1);
		c.add(Input2);

		class BtListener3 implements ActionListener {
			// MYSQL 객체
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;

			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton) e.getSource();

				// 여기다가 버튼을 눌렀을 때 할 일 지정
				if (b.getText().equals("변경")) { // 변경 버튼
					String table = (String) Ctable.getSelectedItem();
					String col = Input1.getText();
					String di = Input2.getText();
					// MySQL과 프로젝트 연결
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");

						String url = "jdbc:mysql://localhost/madang";

						conn = DriverManager.getConnection(url, "madang", "madang");

						stmt = conn.createStatement();

						stmt.executeUpdate("UPDATE " + table + " SET " + col + " WHERE " + di + ";");
						l4.setText("데이터를 성공적으로 변경했습니다.");
						// setVisible(false);
					} catch (ClassNotFoundException e1) {
						// 드라이버 로딩 실패 System.out.println("드라이버 로딩 실패");
					} catch (SQLException e1) {
						// 에러 System.out.println("에러: " + e1);
						l4.setText("데이터 변경에 실패했습니다."); // 변경 실패
					} finally {
						try {
							if (conn != null && !conn.isClosed()) {
								conn.close();
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}

		Cb.addActionListener(new BtListener3());

		setSize(300, 200);
		setVisible(true);
		setTitle("변경");
	}
}

class Ticketing extends JFrame {
	Ticketing(String Smovie) {
		// Container 객체
		Container c = getContentPane();
		c.setLayout(null);

		// MYSQL 객체
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String paymentM[] = { "현금", "카드", "네이버페이", "카카오페이" };

		JComboBox<String> Ctable1 = new JComboBox<String>();
		JComboBox<String> Ctable2 = new JComboBox<String>();
		JComboBox<String> Ctable3 = new JComboBox<String>(paymentM);

		JTextArea ta = new JTextArea();
		JScrollPane sp2 = new JScrollPane(ta);

		JLabel l1 = new JLabel(" 상영일");
		JLabel l2 = new JLabel("고른 좌석");
		JLabel l3 = new JLabel("결재 수단");
		JLabel l4 = new JLabel("ID");
		JLabel l5 = new JLabel("결재 금액 : 10000");
		JLabel l6 = new JLabel("잘못된 예매입니다.");

		JTextField Input1 = new JTextField(8);

		JButton Cb = new JButton("예매");
		sp2.setBounds(10, 160, 500, 500);
		Cb.setBounds(190, 120, 120, 30);
		l1.setBounds(30, 30, 120, 20);
		l2.setBounds(30, 60, 120, 20);
		l3.setBounds(30, 90, 120, 20);
		l4.setBounds(250, 30, 200, 20);
		l5.setBounds(250, 60, 200, 20);
		l6.setBounds(250, 90, 120, 20);
		Ctable1.setBounds(100, 30, 120, 20);
		Ctable2.setBounds(100, 60, 120, 20);
		Ctable3.setBounds(100, 90, 120, 20);
		Input1.setBounds(280, 30, 200, 20);
		c.add(Cb);
		c.add(l1);
		c.add(l2);
		c.add(l3);
		c.add(l4);
		c.add(l5);
		c.add(l6);
		c.add(Ctable1);
		c.add(Ctable2);
		c.add(Ctable3);
		c.add(Input1);
		// c.add(sp2);

		// 기능
		Ctable1.removeAllItems();
		ResultSet rs2 = null;
		String movieid = "";
		// MySQL과 프로젝트 연결
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost/madang";

			conn = DriverManager.getConnection(url, "madang", "madang");

			stmt = conn.createStatement();

			rs2 = stmt.executeQuery("SELECT * FROM madang.Movies WHERE movie_title=\'" + Smovie + "\';");
			// System.out.println("SELECT * FROM madang.Movies WHERE movie_title=\'"+ Smovie
			// +"\';");

			while (rs2.next()) {
				movieid = rs2.getString("movie_number");
			}

			rs = stmt.executeQuery("SELECT * FROM Schedules WHERE movie_number=" + movieid + ";");

			while (rs.next()) {
				Ctable1.addItem(rs.getString("show_date"));
			}

		} catch (ClassNotFoundException e1) {
			// 드라이버 로딩 실패 System.out.println("드라이버 로딩 실패");
		} catch (SQLException e1) {
			// 에러 System.out.println("에러: " + e1);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		// 고른 스케쥴에 따라 시트 'N'인 애들 넣어줌
		// 기능

		Ctable2.removeAllItems();
		ResultSet rs3 = null;
		String selected = Ctable1.getSelectedItem().toString();
		String Theaterid = "";
		// MySQL과 프로젝트 연결
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost/madang";

			conn = DriverManager.getConnection(url, "madang", "madang");

			stmt = conn.createStatement();

			rs2 = stmt.executeQuery("SELECT * FROM madang.Movies WHERE movie_title=\'" + Smovie + "\';");
			// System.out.println("SELECT * FROM madang.Movies WHERE movie_title=\'"+ Smovie
			// +"\';");

			while (rs2.next()) {
				movieid = rs2.getString("movie_number");
			}
			// System.out.println("SELECT * FROM madang.Schedules WHERE movie_number=\'"+
			// movieid +"\' AND show_date=\'" + selected + "\';");
			rs3 = stmt.executeQuery("SELECT * FROM madang.Schedules WHERE movie_number=\'" + movieid
					+ "\' AND show_date=\'" + selected + "\';");

			while (rs3.next()) {
				Theaterid = rs3.getString("Theater_number");
			}

			// System.out.println("SELECT * FROM madang.Seat WHERE seat_status=\'N\' AND
			// Theater_number=" + Theaterid + ";");
			rs = stmt.executeQuery(
					"SELECT * FROM madang.Seat WHERE seat_status=\'N\' AND Theater_number=" + Theaterid + ";");

			while (rs.next()) {
				Ctable2.addItem(rs.getString("seat_number"));
				// System.out.println(rs.getString("seat_number"));
			}

		} catch (ClassNotFoundException e1) {
			// 드라이버 로딩 실패
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e1) {
			// 에러
			System.out.println("에러: " + e1);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		Ctable1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// MYSQL 객체
				Ctable2.removeAllItems();
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				ResultSet rs2 = null;
				ResultSet rs3 = null;
				String selected = Ctable1.getSelectedItem().toString();
				String movieid = "";
				String Theaterid = "";
				// MySQL과 프로젝트 연결
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");

					String url = "jdbc:mysql://localhost/madang";

					conn = DriverManager.getConnection(url, "madang", "madang");

					stmt = conn.createStatement();

					rs2 = stmt.executeQuery("SELECT * FROM madang.Movies WHERE movie_title=\'" + Smovie + "\';");
					// System.out.println("SELECT * FROM madang.Movies WHERE movie_title=\'"+ Smovie
					// +"\';");

					while (rs2.next()) {
						movieid = rs2.getString("movie_number");
					}
					// System.out.println("SELECT * FROM madang.Schedules WHERE movie_number=\'"+
					// movieid +"\' AND show_date=\'" + selected + "\';");
					rs3 = stmt.executeQuery("SELECT * FROM madang.Schedules WHERE movie_number=\'" + movieid
							+ "\' AND show_date=\'" + selected + "\';");

					while (rs3.next()) {
						Theaterid = rs3.getString("Theater_number");
					}

					// System.out.println("SELECT * FROM madang.Seat WHERE seat_status=\'N\' AND
					// Theater_number=" + Theaterid + ";");
					rs = stmt.executeQuery(
							"SELECT * FROM madang.Seat WHERE seat_status=\'N\' AND Theater_number=" + Theaterid + ";");

					while (rs.next()) {
						Ctable2.addItem(rs.getString("seat_number"));
						// System.out.println(rs.getString("seat_number"));
					}

				} catch (ClassNotFoundException e1) {
					// 드라이버 로딩 실패
					System.out.println("드라이버 로딩 실패");
				} catch (SQLException e1) {
					// 에러
					System.out.println("에러: " + e1);
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		class BtListener3 implements ActionListener {
			// MYSQL 객체
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton) e.getSource();
				String n1="", n2="", n4="";
				String sche; // 선택 스케쥴
				String seat; // 선택 좌석
				String paym; // 결재수단
				String n3="", id;
				
				// 여기다가 버튼을 눌렀을 때 할 일 지정
				if (b.getText().equals("예매")) { // 변경 버튼
					sche = Ctable1.getSelectedItem().toString(); // 선택 스케쥴 show_date
					seat = Ctable2.getSelectedItem().toString(); // 선택 좌석 seat_number
					paym = Ctable3.getSelectedItem().toString(); // 결재수단 payment_method
					id = Input1.getText(); // member_id
					n3 = seat;
					// MySQL과 프로젝트 연결
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");

						String url = "jdbc:mysql://localhost/madang";

						conn = DriverManager.getConnection(url, "madang", "madang");

						stmt = conn.createStatement();
						
						// System.out.println("INSERT INTO Reservation VALUES(0," + '\'' + paym + "\',"
						// + '\''+ "결제대기" + "\'," + "10000," + '\''+ id + "\',\'" + "2021-05-19
						// 11:30:08" +"\');");
						stmt.executeUpdate("INSERT INTO Reservation VALUES(0," + '\'' + paym + "\'," + '\'' + "결제대기"
								+ "\'," + "10000," + '\'' + id + "\',\'" + "2021-05-19 11:30:08" + "\');");
						stmt.executeUpdate("UPDATE madang.Seat SET seat_status='Y' WHERE seat_number=" + seat + ";");
						
						
						rs = stmt.executeQuery("SELECT * FROM madang.Schedules LEFT JOIN madang.Movies ON Schedules.movie_number=Movies.movie_number WHERE show_date=\'" + sche + "\';");
						while (rs.next()) {
							n1 = rs.getString("schedule_number");
							n2 = rs.getString("Theater_number");
						}
						
						rs = stmt.executeQuery("SELECT * FROM madang.Reservation WHERE member_id=\'" + id + "\';");
						while (rs.next()) {
							n4 = rs.getString("reservation_number");
						}
						
						stmt.executeUpdate("INSERT INTO madang.Ticket VALUE(0,"+ n1 + "," + n2 + "," + n3 + "," + n4 + ",\'N\'," + "10000," + "10000" + ");");
						
						stmt.close();
						conn.close();
						
						// 예매 성공
						setVisible(false);
						l6.setVisible(false);
					} catch (ClassNotFoundException e1) {
						// 드라이버 로딩 실패 System.out.println("드라이버 로딩 실패");
						l6.setVisible(true);
					} catch (SQLException e1) {
						// 에러 System.out.println("에러: " + e1);
						l6.setVisible(true); // 예매 실패
					}
				
				}
			}
		}

		Cb.addActionListener(new BtListener3());

		l5.setVisible(true);
		l6.setVisible(false);
		ta.setEditable(false);
		ta.setLineWrap(true);
		sp2.setVisible(true);
		sp2.setViewportView(ta);
		setSize(535, 210);
		setVisible(true);
		setTitle("예매");
	}
}

class Change2 extends JFrame {
	Change2(String rn) {
		// Container 객체
		Container c = getContentPane();
		c.setLayout(null);

		JLabel l1 = new JLabel("영화 ");
		JLabel l2 = new JLabel("상영일");
		JLabel l3 = new JLabel("좌석 ");
		JLabel l4 = new JLabel("");
		
		JComboBox<String> Ctable = new JComboBox<String>();
		JComboBox<String> Cb2= new JComboBox<String>();
		JComboBox<String> Cb3 = new JComboBox<String>();

		JTextField Input1 = new JTextField(8);
		JTextField Input2 = new JTextField(8);

		JButton Cb = new JButton("예매 변경");
		Cb.setBounds(80, 130, 120, 20);
		l1.setBounds(30, 30, 120, 20);
		l2.setBounds(30, 60, 120, 20);
		l3.setBounds(30, 90, 120, 20);
		l4.setBounds(50, 5, 200, 20);
		
		Ctable.setBounds(100, 30, 120, 20);
		Cb2.setBounds(100, 60, 120, 20);
		Cb3.setBounds(100, 90, 120, 20);
		
		JLabel scn = new JLabel("");
		JLabel tn = new JLabel("");
		JLabel sen = new JLabel("");
		
		//Ctable.setBounds(100, 30, 120, 20);
		//Input1.setBounds(100, 60, 180, 20);
		//Input2.setBounds(100, 90, 180, 20);
		c.add(Cb);
		c.add(l1);
		c.add(l2);
		c.add(l3);
		c.add(l4);
		c.add(Ctable);
		c.add(Cb2);
		c.add(Cb3);
		c.add(scn);
		c.add(tn);
		c.add(sen);
		//c.add(Input1);
		//c.add(Input2);
		
		
		// MYSQL 객체
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		// MySQL과 프로젝트 연결
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost/madang";

			conn = DriverManager.getConnection(url, "madang", "madang");

			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("SELECT * FROM Movies;");
			
			Ctable.removeAllItems();
			while (rs.next()) {
				Ctable.addItem(rs.getString("movie_title"));
			}

				
		} catch (ClassNotFoundException e1) {
			// 드라이버 로딩 실패 System.out.println("드라이버 로딩 실패");
		} catch (SQLException e1) {
			// 에러 System.out.println("에러: " + e1);
			//l4.setText("데이터 변경에 실패했습니다."); // 변경 실패
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		class BtListener3 implements ActionListener {
			// MYSQL 객체
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;

			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton) e.getSource();

				// 여기다가 버튼을 눌렀을 때 할 일 지정
				if (b.getText().equals("예매 변경")) { // 변경 버튼
					String movieT = (String) Ctable.getSelectedItem(); // movie_title
					String scheDT = (String) Cb2.getSelectedItem(); // show_date
					String seatId = (String) Cb3.getSelectedItem(); // seat_number
					// rn = reservation_number
					// MySQL과 프로젝트 연결
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");

						String url = "jdbc:mysql://localhost/madang";

						conn = DriverManager.getConnection(url, "madang", "madang");

						stmt = conn.createStatement();
						
						// 업데이트 해줘야함 Ticket 테이블
						// 원래 시트 N으로 새로운 시트 Y로 만드렁야함!
						// 객체 생성해서 화면열 때, 매개변수로 기존 Reservation_number 받아오기
						//System.out.println(sen.getText() + "," + tn.getText() + "," + Cb3.getSelectedItem().toString());
						
						String senOrigin="";
						
						rs = stmt.executeQuery("SELECT * FROM madang.Ticket WHERE reservation_number=" + rn + ";");
						
						if (rs.next()) {
							senOrigin = rs.getString("seat_number");
						}
						
						stmt.executeUpdate("UPDATE madang.Seat SET seat_status=\'N\' WHERE seat_number=" + senOrigin + ";");
						stmt.executeUpdate("UPDATE madang.Ticket SET schedule_number=" + sen.getText() + "," + "Theater_number=" + tn.getText() + "," + "seat_number=" + Cb3.getSelectedItem().toString() + " WHERE reservation_number=" + rn + ";");
						stmt.executeUpdate("UPDATE madang.Seat SET seat_status=\'Y\' WHERE seat_number=" + Cb3.getSelectedItem().toString() + ";");
						
						// sen.getText(), tn.getText(), Cb3.getSelectedItem().toString() 50002 20002 30052
						
						l4.setText("예매를 성공적으로 변경했습니다.");
						setVisible(false);
					} catch (ClassNotFoundException e1) {
						// 드라이버 로딩 실패 System.out.println("드라이버 로딩 실패");
					} catch (SQLException e1) {
						// 에러 System.out.println("에러: " + e1);
						l4.setText("데이터 변경에 실패했습니다."); // 변경 실패
					} finally {
						try {
							if (conn != null && !conn.isClosed()) {
								conn.close();
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}
		
		Ctable.addActionListener(new ActionListener() {
			// MYSQL 객체
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			public void actionPerformed(ActionEvent e) {
				String strT = Ctable.getSelectedItem().toString();
				// MySQL과 프로젝트 연결
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					
					String url = "jdbc:mysql://localhost/madang";

					conn = DriverManager.getConnection(url, "madang", "madang");

					stmt = conn.createStatement();
					
					rs = stmt.executeQuery("SELECT * FROM Movies WHERE movie_title=\'" + strT + "\';");
					
					String id="";
					
					if (rs.next()) {
						id = rs.getString("movie_number");
					}
					
					rs = stmt.executeQuery("SELECT * FROM madang.Schedules LEFT JOIN madang.Movies ON Schedules.movie_number=Movies.movie_number WHERE Movies.movie_number=" + id + ";");
					
					String sid="";
					int i = 0;
					
					// cb2에 addItem 해줌
					Cb2.removeAllItems();
					while (rs.next()) {
						Cb2.addItem(rs.getString("show_date"));
						if(i++==0) {
							sid = rs.getString("Theater_number"); // 여기서 가져올 수 있음
						}
					}
					
					// 상영관에 따라 빈 seat 목록 가져와서 Cb3에 전부 addItem해줘야함
					rs2 = stmt.executeQuery("SELECT * FROM Seat WHERE Theater_number=" + sid + " AND seat_status=\'N\';");
					
					Cb3.removeAllItems();
					while (rs2.next()) {
						Cb3.addItem(rs2.getString("seat_number"));
					}
					
				} catch (ClassNotFoundException e1) {
					// 드라이버 로딩 실패 System.out.println("드라이버 로딩 실패");
				} catch (SQLException e1) {
					// 에러 System.out.println("에러: " + e1);
					l4.setText("데이터 변경에 실패했습니다."); // 변경 실패
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		Cb2.addActionListener(new ActionListener() {
			// MYSQL 객체
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			public void actionPerformed(ActionEvent e) {
				String strT = Ctable.getSelectedItem().toString();
				// MySQL과 프로젝트 연결
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					
					String url = "jdbc:mysql://localhost/madang";

					conn = DriverManager.getConnection(url, "madang", "madang");

					stmt = conn.createStatement();
					
					rs = stmt.executeQuery("SELECT * FROM Movies WHERE movie_title=\'" + strT + "\';");
					
					String id="";
					
					if (rs.next()) {
						id = rs.getString("movie_number");
					}
					
					rs = stmt.executeQuery("SELECT * FROM madang.Schedules LEFT JOIN madang.Movies ON Schedules.movie_number=Movies.movie_number WHERE Movies.movie_number=" + id + ";");
					
					String sid="";
					int i = 0;
					
					while (rs.next()) {
						if(i++==Cb2.getSelectedIndex()) {
							sid = rs.getString("Theater_number"); // 여기서 가져올 수 있음
							sen.setText(rs.getString("schedule_number"));
						}
					}
					
					tn.setText(sid);
					
					// 상영관에 따라 빈 seat 목록 가져와서 Cb3에 전부 addItem해줘야함
					rs2 = stmt.executeQuery("SELECT * FROM Seat WHERE Theater_number=" + sid + " AND seat_status=\'N\';");
					
					Cb3.removeAllItems();
					while (rs2.next()) {
						Cb3.addItem(rs2.getString("seat_number"));
					}
					l4.setText("");
				} catch (ClassNotFoundException e1) {
					// 드라이버 로딩 실패 System.out.println("드라이버 로딩 실패");
				} catch (SQLException e1) {
					// 에러 System.out.println("에러: " + e1);
					l4.setText("데이터 변경에 실패했습니다."); // 변경 실패
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		

		Cb.addActionListener(new BtListener3());

		setSize(300, 200);
		setVisible(true);
		setTitle("변경");
	}
}