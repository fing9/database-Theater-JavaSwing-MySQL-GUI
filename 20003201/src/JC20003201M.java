import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class JC20003201M extends JFrame {
	// Container ��ü
	Container c = getContentPane();

	String tableList[] = { "Movies", "Theater", "Seat", "Members", "Schedules", "Reservation", "Ticket" };
	// String tableList2[] = { "��ȭ��", "������", "����", "�帣" }; // Movies
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

	// TextField, TextArea, ComboBox, JLabel ��ü
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

	// ȸ�� ������Ʈ
	JComboBox<String> Ctable2 = new JComboBox<String>();
	JComboBox<String> Ctable3 = new JComboBox<String>();
	JComboBox<String> Ctable4 = new JComboBox<String>();
	JTextField tf1 = new JTextField(8);
	JTextField tf2 = new JTextField(8);
	JTextField tf3 = new JTextField(8);
	JTextField tf4 = new JTextField(8);
	JTextField tf5 = new JTextField(8);
	// JScrollPane sp2 = new JScrollPane(ta);

	// MYSQL ��ü
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

			// ����ٰ� ��ư�� ������ �� �� �� ����
			if (b.getText().equals("�ʱ�ȭ")) { // �ʱ�ȭ ��ư
				// MySQL�� ������Ʈ ����
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

					// ���⼭���� �Է��� ������ ���������� �� ���ָ� ��.
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '���˵���2', '01:46:00', '15��', '�̻��','������, �ռ���, �ֱ�ȭ','����, �׼�','�������� �������� �� 4�� ��, ��õ�� ���¹��� ��Ʈ������ ������ �����ڸ� �ε��޾� ����� �̼��� �޴´�. �������� ����������(������)�� �����ϸ���(�ֱ�ȭ) ������ ���� �����ڿ��Լ� �������� ������, ���� �ڿ� ���ں��� ������ ���̴� �����ػ�(�ռ���)�� ������ �˰� �ȴ�. ������������ ��õ�� ���¹��� �ѱ��� ��Ʈ���� ������ ����� ���˸� �������� �����ػ��� ���������� �ѱ� �����ϴµ�... ���� ��� ��� �� ���� ����! �����ϰ� ȭ���� ���� ���� ������ �ٽ� ��������!','2021-01-01');");
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '���ͽ�Ʈ������', '02:06:00', '12��', '�� ���̹�','���׵�Ʈ �Ĺ���ġ, �����ں��� �ý�','�׼�','������ �տ��Ǵ� ������ �ھ�Ų �ð����� ��Ƽ������ ������ ���� �����, �׸��� ������ �Ѿ� ���� ���ο� ������� �´ڶ߸��� �� ������ ��Ʈ��������. ��ȥ�� ��, �״� ����ġ ���� ������ ���� �¼� �ο��߸� �ϴµ���.','2021-02-02');\r\n");
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '�Ǵ� ������ ���ϴ�', '01:30:00', '15��', '����','������, �̿�, ������','�׼�','�ִ� û���������� �����Ĵ� ���ں������� �Ǹ� ���� �ذ��, �ϸ� ������ �ռ��� �ϴ븦 ����Ѵ�. �׷���, ���Ͽ� ���� �������� ������ ģ���� ���Ҵ� ������ �˸� ������� 10���� ������Ȱ�� �ϰ� �ȴ�. ����, ������ ����� ����, ���������༼�� �ϸ� ������ ������ ������ ������ ��� �ҽ��� ��� �Ҿȿ� �۽��δ�. ������ ������ ���� ġ��� �ϰ� �� ���� �����Ϸ��� ������ �ᱹ, ��¥ ���������� ��Ȱ�� �����ϸ� ���ΰ� ������ ���� �ο��� �����ϴµ�...','2021-03-03');\r\n");
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '�ȳ��ϼ���', '01:58:00', '��ü������', '������','��ȯ��, ����, �̼���','���','�ܷο� ���� �ӿ��� ������ ����� ����ȩ ����(��ȯ��). ���״� ������ �˷��ְڴٴ� ����ȣ�� ����(����)�� ���ȿ� ���� �ú� ȣ���ǽ� ������ ã�ư���. �� ������� �� ���� ������̶��?! ����ġ ���� �����԰� �������� ���̸� �ݱ��, �Ϸ��Ϸ縦 ������ ��ư��� �׵鿡�� ���� ������ ������ �±⸦ ������� �����ϴµ��� ������ ����Ʈ������ ���ϴ� ���νð� ������ ����! ���ȳ��ϼ��䡱','2021-04-04');\r\n");
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '��ġ�� �뷡, ������', '01:53:00', '15��', '����','������, ������','��ť���͸�','10�� ������, ���� �������� ö�� �׸��� ���ǽ����� Ȧ���� ��������� ��𼭵� ���� ���� �뷧���� �������� ������ �ô�� �Բ��� ������ ���� 40�ֳ�, �츮�� ������ �������� ���ǰ� ���� ������!','2021-05-05');\r\n");

					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '������', '01:48:00', '12��', '�ż���','������, ����ȿ, ���ػ�','���','���� ��ȭ�� ��̾��ٴ� �Ƶ�� �û� ��Ÿ���� ����, �յ��� ���� ���з� �������� ���� �߳��� ��ȭ���� ����. �Ƹ�����Ʈ ��� 60��뿡 Ȱ���� �ѱ� �� ��° ���� ��ȭ���� ȫ���� ������ ��ǰ <���ǻ�>�� �ʸ��� �����ϰ� �ȴ�. ����� �ʸ��� ã�� ȫ������ ������ ������ ���󰡴� ������ ��ü�� �� �� ���� ���� �� ������ �׸��ڿ� �Բ� �� �ð� ���� �����ϰ� �Ǵµ�... ��¾��, ������� �ް� ��ȭ�� ���� ������ �ǻ�Ƴ��� �͸� ����. ','2021-06-04');\r\n");
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '���� ����: ���̴Ͼ�', '02:27:00', '12��', '�ݸ� Ʈ�����ο�','ũ���� ����, ����̽� �޶� �Ͽ���','�׼�','������� �����̾��� �̽��� ����� ���� �ı��� ��, ��ħ�� ������� ���� ��� ���� ������ ����Ѵ�. ���� �Բ� �����ؼ� �� �� ������ ����ü�� ������ �������� �η� ����� �޾�� ���� ��� �־��� ���⸦ ������ �ΰ���. ������ �ֻ��� ������ �ڸ��� �ɰ� �ΰ��� ������ ������ ������ ��������.','2021-07-01');\r\n");
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '������ ������ �������', '01:35:00', '12��', '�ǿ��� �ǳ��','īƮ�� ����, �̻� �ξ߸޵�, �ø���� ��Ʈ','���','������ �ְ��� ������ ���� �����״� �븦 �̾� ��������� ��ؿ��� �ִ�. ������ ��̸� ����ǰ ����ϴ� ����� �󸶸����� �з� ���� ���� ����, �ڽ��� �������� ��� ���ѱ� ���⿡ ó�Ѵ�. ���� �����縦 �̾� ������ ��Ű�� ������ ��µ� ���ĵ� ���� ��¥ �������� ������ ����Ű�⸸ �ϴµ�... ���� ����� ���� �ʺ� ���Ե��� ������ ���س� �� ������?','2021-08-01');\r\n");
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '��Ʈ��', '02:03:00', '15��', '�� ī�纣��','���̾� ����, ����ÿ �ƾƴ㽺','���/�θǽ�','17��, ����ơ��� ��� ������ ���ٸ����� ���� ù���� ���Ѵ�. ���� �ӵ��� ���ο��� ������� ��. �׷��� �̵� �տ� ���� �庮�� ���� �̺��ϰ� �ȴ�. 24��, ���ٸ����� �쿬�� �Ź����� ����ơ��� �ҽ��� ���ϰ� ���� �� ���� ù��� �տ��� �ٽ� �� �� ������ ��ο� ���� �Ǵµ��� ���ϰ��� ����, �������� ���� ���, �׸��� ������������ �� ����� ������ ��������� �� �λ��� ������ �λ��Դϴ�','2021-09-10');\r\n");
					stmt.executeUpdate(
							"INSERT INTO Movies VALUES(0, '���ͳ� ������', '01:47:00', '15��', '�̼� ���帮','�� ĳ��, ����Ʈ ������, Ŀ��ƾ ����Ʈ','���/�θǽ�','������ ���� ��︸�� �����شٴ� �����縦 ã�ư� ����� ���� Ŭ����Ÿ���� ����� ������ ����Ѵ�. ����� ����� ������ ������ ����� ���۵Ǵ� ����, �ູ�� ����, ���� �ӿ� ���ε� �߾���� ����� �Ⱦ����⸸ �ϴµ�... ����� ����� �� ���ĵ� ��������? ����� �׷��� �ٽ� ���ȴ�.','2021-10-10');\r\n");

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
							"INSERT INTO Members VALUES('bsu0404','������','010-3854-7009','bsu0404@naver.com');");
					stmt.executeUpdate(
							"INSERT INTO Members VALUES('ksm0101','�輺��','010-1234-5678','ksm0101@naver.com');");
					stmt.executeUpdate(
							"INSERT INTO Members VALUES('kmk0412','��ΰ�','010-1523-8956','kmk2364@naver.com');");
					stmt.executeUpdate(
							"INSERT INTO Members VALUES('jyj0328','������','010-7852-2145','jyj0328@naver.com');");
					stmt.executeUpdate(
							"INSERT INTO Members VALUES('jye0428','������','010-5266-7851','jye0428@naver.com');");

					stmt.executeUpdate(
							"INSERT INTO Members VALUES('lwb1003','�̼���','010-2569-9685','lwb1003@naver.com');");
					stmt.executeUpdate(
							"INSERT INTO Members VALUES('lhy0909','���Ͽ�','010-5682-4538','lhy0909@naver.com');");
					stmt.executeUpdate(
							"INSERT INTO Members VALUES('lhj0911','������','010-9089-1932','lhj0911@naver.com');");
					stmt.executeUpdate(
							"INSERT INTO Members VALUES('ksy0626','�Ǽ���','010-8910-9365','ksy0626@naver.com');");
					stmt.executeUpdate(
							"INSERT INTO Members VALUES('lmk0211','�̹ΰ�','010-2548-8542','lmk0211@naver.com');");

					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10001,20001,'2021-01-19','�Ͽ���','2','17:25:00','2021-01-24');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10002,20002,'2021-02-08','������','3','10:40:00','2021-02-05');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10003,20003,'2021-03-28','ȭ����','1','11:20:00','2021-03-28');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10004,20004,'2021-04-19','�Ͽ���','5','09:25:00','2021-04-20');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10005,20005,'2021-05-20','������','6','13:50:00','2021-05-23');");

					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10006,20006,'2021-06-13','������','2','14:30:00','2021-06-30');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10007,20007,'2021-07-19','�Ͽ���','4','19:45:00','2021-07-25');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10008,20008,'2021-08-12','�Ͽ���','7','22:20:00','2021-08-26');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10009,20009,'2021-09-15','������','3','12:15:00','2021-09-30');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10010,20010,'2021-10-04','�����','1','15:05:00','2021-10-12');");
					stmt.executeUpdate(
							"INSERT INTO Schedules VALUES(0,10001,20002,'2021-11-19','�����','1','15:05:00','2021-11-18');");

					
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'ī��','�����Ϸ�',10000,'bsu0404','2021-01-19 10:30:00');");
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'����','�����Ϸ�',10000,'ksm0101','2021-02-05 11:35:05');");
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'ī��','�����Ϸ�',10000,'kmk0412','2021-03-26 10:20:10');");
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'���̹�����','�������',10000,'jyj0328','2021-04-19 09:10:07');");
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'īī������','�����Ϸ�',10000,'jye0428','2021-05-19 11:30:08');");

					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'ī��','�����Ϸ�',10000,'lwb1003','2021-06-27 10:30:06');");
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'ī��','�������',10000,'lhy0909','2021-07-02 13:30:48');");
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'����','�����Ϸ�',10000,'lhj0911','2021-08-02 15:30:45');");
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'ī��','�����Ϸ�',10000,'ksy0626','2021-09-27 16:30:26');");
					stmt.executeUpdate(
							"INSERT INTO Reservation VALUES(0,'����','�������',10000,'lmk0211','2021-10-11 08:30:18');");

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
					ta.setText("������ ���̽��� �ʱ�ȭ �ƽ��ϴ�.");
				} catch (ClassNotFoundException e1) {
					// ����̹� �ε� ����
					System.out.println("����̹� �ε� ����");
				} catch (SQLException e1) {
					// ����
					System.out.println("����: " + e1);
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} else if (b.getText().equals("��ü ���̺� ����")) { // ��ü ���̺� ���� ��ư

				// MySQL�� ������Ʈ ����
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");

					String url = "jdbc:mysql://localhost/madang";

					conn = DriverManager.getConnection(url, "madang", "madang");

					stmt = conn.createStatement();

					ta.setText(""); // TextArea �ʱ�ȭ

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
					// ����̹� �ε� ���� System.out.println("����̹� �ε� ����");
				} catch (SQLException e1) {
					// ���� System.out.println("����: " + e1);
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} else if (b.getText().equals("�Է� ����")) { // �Է� ��ư
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

				// MySQL�� ������Ʈ ����
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");

					String url = "jdbc:mysql://localhost/madang";

					conn = DriverManager.getConnection(url, "madang", "madang");

					stmt = conn.createStatement();

					ta.setText(""); // TextArea �ʱ�ȭ

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

					ta.setText("�����Ͱ� ���������� �ԷµǾ����ϴ�.");
				} catch (ClassNotFoundException e1) {
					// ����̹� �ε� ���� System.out.println("����̹� �ε� ����");
				} catch (SQLException e1) {
					// ���� System.out.println("����: " + e1);
					ta.setText("�ùٸ� ������ �Է��� �ƴմϴ�. �ٽ� �Է����ּ���");
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			} else if (b.getText().equals("����")) { // ���� ��ư
				String table = (String) Ctable.getSelectedItem();
				String di = DInput.getText();
				// MySQL�� ������Ʈ ����
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");

					String url = "jdbc:mysql://localhost/madang";

					conn = DriverManager.getConnection(url, "madang", "madang");

					stmt = conn.createStatement();

					ta.setText(""); // TextArea �ʱ�ȭ

					stmt.execute("DELETE FROM " + table + " WHERE " + di + ";");

					DInput.setText("������ ���� ���ǽ��� �Է����ּ���.");
					ta.setText("�����͸� ���������� �����߽��ϴ�."); // ���� ����
				} catch (ClassNotFoundException e1) {
					// ����̹� �ε� ���� System.out.println("����̹� �ε� ����");
				} catch (SQLException e1) {
					// ���� System.out.println("����: " + e1);
					ta.setText("������ ������ �����߽��ϴ�."); // ���� ����
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} else if (b.getText().equals("����")) { // ���� ��ư
				new Change();
			} else if (b.getText().equals("�Է� ���")) { // �Է� ��� ��ư
				String table = (String) Ctable.getSelectedItem();
				if (table.equals("Movies")) {
					DInput.setText("������ ���� ���ǽ��� �Է����ּ���.");
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
					DInput.setText("������ ���� ���ǽ��� �Է����ּ���.");
					tInput1.setText("0");
					tInput2.setText("Theater_capacity");
					tInput3.setText("Theater_status");
				} else if (table.equals("Seat")) {
					DInput.setText("������ ���� ���ǽ��� �Է����ּ���.");
					tInput1.setText("0");
					tInput2.setText("Theater_number");
					tInput3.setText("seat_status");
				} else if (table.equals("Members")) {
					DInput.setText("������ ���� ���ǽ��� �Է����ּ���.");
					tInput1.setText("member_id");
					tInput2.setText("member_name");
					tInput3.setText("phone_number");
					tInput4.setText("email");
				} else if (table.equals("Schedules")) {
					DInput.setText("������ ���� ���ǽ��� �Է����ּ���.");
					tInput1.setText("0");
					tInput2.setText("movie_number");
					tInput3.setText("Theater_number");
					tInput4.setText("start_date");
					tInput5.setText("screen_day");
					tInput6.setText("screen_round");
					tInput7.setText("show_time");
					tInput8.setText("show_date");
				} else if (table.equals("Reservation")) {
					DInput.setText("������ ���� ���ǽ��� �Է����ּ���.");
					tInput1.setText("0");
					tInput2.setText("payment_method");
					tInput3.setText("payment_status");
					tInput4.setText("payment_amount");
					tInput5.setText("member_id");
					tInput6.setText("payment_date");
				} else if (table.equals("Ticket")) {
					DInput.setText("������ ���� ���ǽ��� �Է����ּ���.");
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

			// ����ٰ� ��ư�� ������ �� �� �� ����
			if (b.getText().equals("��ȸ")) { // ��ȸ ��ư
				// MySQL�� ������Ʈ ����
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");

					String url = "jdbc:mysql://localhost/madang";

					conn = DriverManager.getConnection(url, "madang", "madang");

					stmt = conn.createStatement();

					ta.setText(""); // TextArea �ʱ�ȭ

					if (!str1.equals("��ȭ��") && !str1.equals("")) {
						ques += "movie_title LIKE \'%" + str1 + "%\'";
						i++;
					}
					if (!str2.equals("������") && !str2.equals("")) {
						if (i != 0)
							ques += " AND ";
						ques += "director LIKE \'%" + str2 + "%\'";
						i++;
					}
					if (!str3.equals("����") && !str3.equals("")) {
						if (i != 0)
							ques += " AND ";
						ques += "actor LIKE \'%" + str3 + "%\'";
						i++;
					}
					if (!str4.equals("�帣") && !str4.equals("")) {
						if (i != 0)
							ques += " AND ";
						ques += "genre LIKE \'%" + str4 + "%\'";
						i++;
					}

					rs = stmt.executeQuery(ques);

					// ���ÿ� ���� �б�
					/*
					 * if (ques.equals("��ȭ��")) { rs =
					 * stmt.executeQuery("SELECT * FROM Movies WHERE movie_title LIKE \'%" + str1 +
					 * "%\'"); } else if (ques.equals("������")) { rs =
					 * stmt.executeQuery("SELECT * FROM Movies WHERE director LIKE \'%" + str1 +
					 * "%\'"); } else if (ques.equals("����")) { rs =
					 * stmt.executeQuery("SELECT * FROM Movies WHERE actor LIKE \'%" + str1 +
					 * "%\'"); } else if (ques.equals("�帣")) { rs =
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
					// ����̹� �ε� ���� System.out.println("����̹� �ε� ����");
				} catch (SQLException e1) {
					// ���� System.out.println("����: " + e1);
					ta.setText("�˻� ����� �����ϴ�.");
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} else if (b.getText().equals("����")) { // ���� ��ư
				if (Ctable2.getItemCount() != 0) {
					new Ticketing(Ctable2.getSelectedItem().toString());
				}
			} else if (b.getText().equals("���� ��ȸ")) { // ���� ��ư
				
				// MySQL�� ������Ʈ ����
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

					// ���̵� �����ͺ��̽��� �����ϸ� ���� �������� �Ѿ.
					if (rs.next()) {
						// ����
						
						String mt="", sd="", tn="", sn="", sp="", rid="";
						Ctable3.removeAllItems();
						Ctable4.removeAllItems();
						
						rs2 = stmt2.executeQuery("select * from Reservation where member_id=\'" + tf5.getText() + "\';");
						
						
						// movie_title  show_date  theater_number seat_number sale_price
						ta.setText("������ȣ ��ȭ��  ����  �󿵰���ȣ  �¼���ȣ  �ǸŰ���\n");
						while (rs2.next()) {
							ResultSet rs3 = null;
							ResultSet rs4 = null;
							ResultSet rs5 = null;
							rid = rs2.getString("reservation_number");
							rs3 = stmt3.executeQuery("select * from Ticket where reservation_number=" + rs2.getString("reservation_number") + ";");
							if(rs3.next()) { // Ƽ��
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
					// ����̹� �ε� ���� System.out.println("����̹� �ε� ����");
					// ����
				} catch (SQLException e1) {
					// ���� System.out.println("����: " + e1);
					// ����
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} else if (b.getText().equals("����")) { // ���� ��ư
				if (Ctable4.getItemCount() != 0) {
					String rid = (String) Ctable3.getSelectedItem();
					// MySQL�� ������Ʈ ����
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
	
						String url = "jdbc:mysql://localhost/madang";
	
						conn = DriverManager.getConnection(url, "madang", "madang");
	
						stmt = conn.createStatement();
	
						ta.setText(""); // TextArea �ʱ�ȭ
	
						stmt.execute("DELETE FROM Reservation WHERE reservation_number=" + rid + ";");
	
						ta.setText("���ű���� ���������� �����߽��ϴ�."); // ���� ����
						Ctable3.removeAllItems();
						Ctable4.removeAllItems();
					} catch (ClassNotFoundException e1) {
						// ����̹� �ε� ���� System.out.println("����̹� �ε� ����");
					} catch (SQLException e1) {
						// ���� System.out.println("����: " + e1);
						ta.setText("���ű�� ������ �����߽��ϴ�."); // ���� ����
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
			} else if (b.getText().equals("����")) { // ���� ��ư
				if (Ctable4.getItemCount() != 0) {
					new Change2((String) Ctable4.getSelectedItem());
				}
			}
		}
	}


	public JC20003201M(int mode) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// mode 1:���� 2:������
		// ������� �ڹ� swing�� �̿��ؼ� �������̽� ����
		if (mode == 2) { // ������ ���
			c.setLayout(null);

			JButton bR = new JButton("�ʱ�ȭ");
			JButton bA = new JButton("��ü ���̺� ����");
			JButton b1 = new JButton("�Է� ����");
			JButton b2 = new JButton("����");
			JButton b3 = new JButton("����");
			JButton b4 = new JButton("�Է� ���");

			sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

			// (x, y, x����, y����)
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
						// DInput.setText("������ ���� ���ǽ��� �Է����ּ���. (���� ���� ���̺� : Movies)");
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
						// DInput.setText("������ ���� ���ǽ��� �Է����ּ���. (���� ���� ���̺� : Theater)");
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
						// DInput.setText("������ ���� ���ǽ��� �Է����ּ���. (���� ���� ���̺� : Seat)");
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
						// DInput.setText("������ ���� ���ǽ��� �Է����ּ���. (���� ���� ���̺� : Members)");
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
						// DInput.setText("������ ���� ���ǽ��� �Է����ּ���. (���� ���� ���̺� : Schedules)");
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
						// DInput.setText("������ ���� ���ǽ��� �Է����ּ���. (���� ���� ���̺� : Reservation)");
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
						// DInput.setText("������ ���� ���ǽ��� �Է����ּ���. (���� ���� ���̺� : Ticket)");
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
			DInput.setText("���ǽ��� �Է����ּ���. (���̺� ������ ������)");
			la1.setText("DELETE  FROM  Movies  WHERE ");

			sp.setVisible(true);
			sp.setViewportView(ta);

			setTitle("20003201/�輺��");
		} else { // ȸ�� ���
			c.setLayout(null);

			JButton bt1 = new JButton("��ȸ");
			JButton bt2 = new JButton("����");
			JButton bt3 = new JButton("���� ��ȸ");
			JButton bt4 = new JButton("����");
			JButton bt5 = new JButton("����");

			sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

			// (x, y, x����, y����)
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

			// �̾ �ؾ��� ��
			// ���� ��� ����
			// ȸ�� �α��α�� ���ֱ�
			// ȸ�� �˻���� ���� ��
			// �����ͺ��̽� Ƽ�ϰ����� ����

			// set text
			tf1.setText("��ȭ��");
			tf2.setText("������");
			tf3.setText("����");
			tf4.setText("�帣");
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

			setTitle("20003201/�輺��");
		}
	}

	public static void main(String[] args) {
		// �����ͺ��̽� �ʱ�ȭ

		// MYSQL ��ü
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		// MySQL�� ������Ʈ ����
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

			// ���⼭���� �Է��� ������ ���������� �� ���ָ� ��.
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '���˵���2', '01:46:00', '15��', '�̻��','������, �ռ���, �ֱ�ȭ','����, �׼�','�������� �������� �� 4�� ��, ��õ�� ���¹��� ��Ʈ������ ������ �����ڸ� �ε��޾� ����� �̼��� �޴´�. �������� ����������(������)�� �����ϸ���(�ֱ�ȭ) ������ ���� �����ڿ��Լ� �������� ������, ���� �ڿ� ���ں��� ������ ���̴� �����ػ�(�ռ���)�� ������ �˰� �ȴ�. ������������ ��õ�� ���¹��� �ѱ��� ��Ʈ���� ������ ����� ���˸� �������� �����ػ��� ���������� �ѱ� �����ϴµ�... ���� ��� ��� �� ���� ����! �����ϰ� ȭ���� ���� ���� ������ �ٽ� ��������!','2021-01-01');");
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '���ͽ�Ʈ������', '02:06:00', '12��', '�� ���̹�','���׵�Ʈ �Ĺ���ġ, �����ں��� �ý�','�׼�','������ �տ��Ǵ� ������ �ھ�Ų �ð����� ��Ƽ������ ������ ���� �����, �׸��� ������ �Ѿ� ���� ���ο� ������� �´ڶ߸��� �� ������ ��Ʈ��������. ��ȥ�� ��, �״� ����ġ ���� ������ ���� �¼� �ο��߸� �ϴµ���.','2021-02-02');\r\n");
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '�Ǵ� ������ ���ϴ�', '01:30:00', '15��', '����','������, �̿�, ������','�׼�','�ִ� û���������� �����Ĵ� ���ں������� �Ǹ� ���� �ذ��, �ϸ� ������ �ռ��� �ϴ븦 ����Ѵ�. �׷���, ���Ͽ� ���� �������� ������ ģ���� ���Ҵ� ������ �˸� ������� 10���� ������Ȱ�� �ϰ� �ȴ�. ����, ������ ����� ����, ���������༼�� �ϸ� ������ ������ ������ ������ ��� �ҽ��� ��� �Ҿȿ� �۽��δ�. ������ ������ ���� ġ��� �ϰ� �� ���� �����Ϸ��� ������ �ᱹ, ��¥ ���������� ��Ȱ�� �����ϸ� ���ΰ� ������ ���� �ο��� �����ϴµ�...','2021-03-03');\r\n");
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '�ȳ��ϼ���', '01:58:00', '��ü������', '������','��ȯ��, ����, �̼���','���','�ܷο� ���� �ӿ��� ������ ����� ����ȩ ����(��ȯ��). ���״� ������ �˷��ְڴٴ� ����ȣ�� ����(����)�� ���ȿ� ���� �ú� ȣ���ǽ� ������ ã�ư���. �� ������� �� ���� ������̶��?! ����ġ ���� �����԰� �������� ���̸� �ݱ��, �Ϸ��Ϸ縦 ������ ��ư��� �׵鿡�� ���� ������ ������ �±⸦ ������� �����ϴµ��� ������ ����Ʈ������ ���ϴ� ���νð� ������ ����! ���ȳ��ϼ��䡱','2021-04-04');\r\n");
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '��ġ�� �뷡, ������', '01:53:00', '15��', '����','������, ������','��ť���͸�','10�� ������, ���� �������� ö�� �׸��� ���ǽ����� Ȧ���� ��������� ��𼭵� ���� ���� �뷧���� �������� ������ �ô�� �Բ��� ������ ���� 40�ֳ�, �츮�� ������ �������� ���ǰ� ���� ������!','2021-05-05');\r\n");

			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '������', '01:48:00', '12��', '�ż���','������, ����ȿ, ���ػ�','���','���� ��ȭ�� ��̾��ٴ� �Ƶ�� �û� ��Ÿ���� ����, �յ��� ���� ���з� �������� ���� �߳��� ��ȭ���� ����. �Ƹ�����Ʈ ��� 60��뿡 Ȱ���� �ѱ� �� ��° ���� ��ȭ���� ȫ���� ������ ��ǰ <���ǻ�>�� �ʸ��� �����ϰ� �ȴ�. ����� �ʸ��� ã�� ȫ������ ������ ������ ���󰡴� ������ ��ü�� �� �� ���� ���� �� ������ �׸��ڿ� �Բ� �� �ð� ���� �����ϰ� �Ǵµ�... ��¾��, ������� �ް� ��ȭ�� ���� ������ �ǻ�Ƴ��� �͸� ����. ','2021-06-04');\r\n");
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '���� ����: ���̴Ͼ�', '02:27:00', '12��', '�ݸ� Ʈ�����ο�','ũ���� ����, ����̽� �޶� �Ͽ���','�׼�','������� �����̾��� �̽��� ����� ���� �ı��� ��, ��ħ�� ������� ���� ��� ���� ������ ����Ѵ�. ���� �Բ� �����ؼ� �� �� ������ ����ü�� ������ �������� �η� ����� �޾�� ���� ��� �־��� ���⸦ ������ �ΰ���. ������ �ֻ��� ������ �ڸ��� �ɰ� �ΰ��� ������ ������ ������ ��������.','2021-07-01');\r\n");
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '������ ������ �������', '01:35:00', '12��', '�ǿ��� �ǳ��','īƮ�� ����, �̻� �ξ߸޵�, �ø���� ��Ʈ','���','������ �ְ��� ������ ���� �����״� �븦 �̾� ��������� ��ؿ��� �ִ�. ������ ��̸� ����ǰ ����ϴ� ����� �󸶸����� �з� ���� ���� ����, �ڽ��� �������� ��� ���ѱ� ���⿡ ó�Ѵ�. ���� �����縦 �̾� ������ ��Ű�� ������ ��µ� ���ĵ� ���� ��¥ �������� ������ ����Ű�⸸ �ϴµ�... ���� ����� ���� �ʺ� ���Ե��� ������ ���س� �� ������?','2021-08-01');\r\n");
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '��Ʈ��', '02:03:00', '15��', '�� ī�纣��','���̾� ����, ����ÿ �ƾƴ㽺','���/�θǽ�','17��, ����ơ��� ��� ������ ���ٸ����� ���� ù���� ���Ѵ�. ���� �ӵ��� ���ο��� ������� ��. �׷��� �̵� �տ� ���� �庮�� ���� �̺��ϰ� �ȴ�. 24��, ���ٸ����� �쿬�� �Ź����� ����ơ��� �ҽ��� ���ϰ� ���� �� ���� ù��� �տ��� �ٽ� �� �� ������ ��ο� ���� �Ǵµ��� ���ϰ��� ����, �������� ���� ���, �׸��� ������������ �� ����� ������ ��������� �� �λ��� ������ �λ��Դϴ�','2021-09-10');\r\n");
			stmt.executeUpdate(
					"INSERT INTO Movies VALUES(0, '���ͳ� ������', '01:47:00', '15��', '�̼� ���帮','�� ĳ��, ����Ʈ ������, Ŀ��ƾ ����Ʈ','���/�θǽ�','������ ���� ��︸�� �����شٴ� �����縦 ã�ư� ����� ���� Ŭ����Ÿ���� ����� ������ ����Ѵ�. ����� ����� ������ ������ ����� ���۵Ǵ� ����, �ູ�� ����, ���� �ӿ� ���ε� �߾���� ����� �Ⱦ����⸸ �ϴµ�... ����� ����� �� ���ĵ� ��������? ����� �׷��� �ٽ� ���ȴ�.','2021-10-10');\r\n");

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

			stmt.executeUpdate("INSERT INTO Members VALUES('bsu0404','������','010-3854-7009','bsu0404@naver.com');");
			stmt.executeUpdate("INSERT INTO Members VALUES('ksm0101','�輺��','010-1234-5678','ksm0101@naver.com');");
			stmt.executeUpdate("INSERT INTO Members VALUES('kmk0412','��ΰ�','010-1523-8956','kmk2364@naver.com');");
			stmt.executeUpdate("INSERT INTO Members VALUES('jyj0328','������','010-7852-2145','jyj0328@naver.com');");
			stmt.executeUpdate("INSERT INTO Members VALUES('jye0428','������','010-5266-7851','jye0428@naver.com');");

			stmt.executeUpdate("INSERT INTO Members VALUES('lwb1003','�̼���','010-2569-9685','lwb1003@naver.com');");
			stmt.executeUpdate("INSERT INTO Members VALUES('lhy0909','���Ͽ�','010-5682-4538','lhy0909@naver.com');");
			stmt.executeUpdate("INSERT INTO Members VALUES('lhj0911','������','010-9089-1932','lhj0911@naver.com');");
			stmt.executeUpdate("INSERT INTO Members VALUES('ksy0626','�Ǽ���','010-8910-9365','ksy0626@naver.com');");
			stmt.executeUpdate("INSERT INTO Members VALUES('lmk0211','�̹ΰ�','010-2548-8542','lmk0211@naver.com');");

			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10001,20001,'2021-01-19','�Ͽ���','2','17:25:00','2021-01-24');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10002,20002,'2021-02-08','������','3','10:40:00','2021-02-05');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10003,20003,'2021-03-28','ȭ����','1','11:20:00','2021-03-28');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10004,20004,'2021-04-19','�Ͽ���','5','09:25:00','2021-04-20');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10005,20005,'2021-05-20','������','6','13:50:00','2021-05-23');");

			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10006,20006,'2021-06-13','������','2','14:30:00','2021-06-30');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10007,20007,'2021-07-19','�Ͽ���','4','19:45:00','2021-07-25');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10008,20008,'2021-08-12','�Ͽ���','7','22:20:00','2021-08-26');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10009,20009,'2021-09-15','������','3','12:15:00','2021-09-30');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10010,20010,'2021-10-04','�����','1','15:05:00','2021-10-12');");
			stmt.executeUpdate(
					"INSERT INTO Schedules VALUES(0,10001,20002,'2021-11-19','�����','1','15:05:00','2021-11-18');");

			
			stmt.executeUpdate("INSERT INTO Reservation VALUES(0,'ī��','�����Ϸ�',10000,'bsu0404','2021-01-19 10:30:00');");
			stmt.executeUpdate("INSERT INTO Reservation VALUES(0,'����','�����Ϸ�',10000,'ksm0101','2021-02-05 11:35:05');");
			stmt.executeUpdate("INSERT INTO Reservation VALUES(0,'ī��','�����Ϸ�',10000,'kmk0412','2021-03-26 10:20:10');");
			stmt.executeUpdate(
					"INSERT INTO Reservation VALUES(0,'���̹�����','�������',10000,'jyj0328','2022-04-19 09:10:07');");
			stmt.executeUpdate(
					"INSERT INTO Reservation VALUES(0,'īī������','�����Ϸ�',10000,'jye0428','2022-05-19 11:30:08');");

			stmt.executeUpdate("INSERT INTO Reservation VALUES(0,'ī��','�����Ϸ�',10000,'lwb1003','2021-06-27 10:30:06');");
			stmt.executeUpdate("INSERT INTO Reservation VALUES(0,'ī��','�������',10000,'lhy0909','2021-07-02 13:30:48');");
			stmt.executeUpdate("INSERT INTO Reservation VALUES(0,'����','�����Ϸ�',10000,'lhj0911','2021-08-02 15:30:45');");
			stmt.executeUpdate("INSERT INTO Reservation VALUES(0,'ī��','�����Ϸ�',10000,'ksy0626','2021-09-27 16:30:26');");
			stmt.executeUpdate("INSERT INTO Reservation VALUES(0,'����','�������',10000,'lmk0211','2021-10-11 08:30:18');");

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
			// ����̹� �ε� ����
			System.out.println("����̹� �ε� ����");
		} catch (SQLException e1) {
			// ����
			System.out.println("����: " + e1);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		// ������ ���̽� �ʱ�ȭ �Ϸ�

		// ���α׷� ����
		new Login();
	}

}

class Login extends JFrame {
	Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Container ��ü
		Container c = getContentPane();
		c.setLayout(null);

		// ID �Է� �����ߴµ� ���⼭ �� �ʿ� ���� �� ����.

		// Label ��ü
		// JLabel l1 = new JLabel("ID");
		// JLabel l2 = new JLabel("�߸��� ���̵��Դϴ�. �ٽ� �Է����ּ���.");

		// TextField, TextArea ��ü
		// JTextField tInput1 = new JTextField(30);
		JTextArea ta = new JTextArea(20, 80);
		JScrollPane sp = new JScrollPane(ta);

		// JButton ��ü
		JButton b1 = new JButton("ȸ��");
		JButton b2 = new JButton("������");

		// (x, y, x����, y����)
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

				if (b.getText().equals("ȸ��")) {
					setVisible(false);
					new JC20003201M(1);
				} else if (b.getText().equals("������")) {
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
		// Container ��ü
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

		JButton Cb = new JButton("����");
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
			// MYSQL ��ü
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;

			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton) e.getSource();

				// ����ٰ� ��ư�� ������ �� �� �� ����
				if (b.getText().equals("����")) { // ���� ��ư
					String table = (String) Ctable.getSelectedItem();
					String col = Input1.getText();
					String di = Input2.getText();
					// MySQL�� ������Ʈ ����
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");

						String url = "jdbc:mysql://localhost/madang";

						conn = DriverManager.getConnection(url, "madang", "madang");

						stmt = conn.createStatement();

						stmt.executeUpdate("UPDATE " + table + " SET " + col + " WHERE " + di + ";");
						l4.setText("�����͸� ���������� �����߽��ϴ�.");
						// setVisible(false);
					} catch (ClassNotFoundException e1) {
						// ����̹� �ε� ���� System.out.println("����̹� �ε� ����");
					} catch (SQLException e1) {
						// ���� System.out.println("����: " + e1);
						l4.setText("������ ���濡 �����߽��ϴ�."); // ���� ����
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
		setTitle("����");
	}
}

class Ticketing extends JFrame {
	Ticketing(String Smovie) {
		// Container ��ü
		Container c = getContentPane();
		c.setLayout(null);

		// MYSQL ��ü
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String paymentM[] = { "����", "ī��", "���̹�����", "īī������" };

		JComboBox<String> Ctable1 = new JComboBox<String>();
		JComboBox<String> Ctable2 = new JComboBox<String>();
		JComboBox<String> Ctable3 = new JComboBox<String>(paymentM);

		JTextArea ta = new JTextArea();
		JScrollPane sp2 = new JScrollPane(ta);

		JLabel l1 = new JLabel(" ����");
		JLabel l2 = new JLabel("�� �¼�");
		JLabel l3 = new JLabel("���� ����");
		JLabel l4 = new JLabel("ID");
		JLabel l5 = new JLabel("���� �ݾ� : 10000");
		JLabel l6 = new JLabel("�߸��� �����Դϴ�.");

		JTextField Input1 = new JTextField(8);

		JButton Cb = new JButton("����");
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

		// ���
		Ctable1.removeAllItems();
		ResultSet rs2 = null;
		String movieid = "";
		// MySQL�� ������Ʈ ����
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
			// ����̹� �ε� ���� System.out.println("����̹� �ε� ����");
		} catch (SQLException e1) {
			// ���� System.out.println("����: " + e1);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		// �� �����쿡 ���� ��Ʈ 'N'�� �ֵ� �־���
		// ���

		Ctable2.removeAllItems();
		ResultSet rs3 = null;
		String selected = Ctable1.getSelectedItem().toString();
		String Theaterid = "";
		// MySQL�� ������Ʈ ����
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
			// ����̹� �ε� ����
			System.out.println("����̹� �ε� ����");
		} catch (SQLException e1) {
			// ����
			System.out.println("����: " + e1);
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
				// MYSQL ��ü
				Ctable2.removeAllItems();
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				ResultSet rs2 = null;
				ResultSet rs3 = null;
				String selected = Ctable1.getSelectedItem().toString();
				String movieid = "";
				String Theaterid = "";
				// MySQL�� ������Ʈ ����
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
					// ����̹� �ε� ����
					System.out.println("����̹� �ε� ����");
				} catch (SQLException e1) {
					// ����
					System.out.println("����: " + e1);
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
			// MYSQL ��ü
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton) e.getSource();
				String n1="", n2="", n4="";
				String sche; // ���� ������
				String seat; // ���� �¼�
				String paym; // �������
				String n3="", id;
				
				// ����ٰ� ��ư�� ������ �� �� �� ����
				if (b.getText().equals("����")) { // ���� ��ư
					sche = Ctable1.getSelectedItem().toString(); // ���� ������ show_date
					seat = Ctable2.getSelectedItem().toString(); // ���� �¼� seat_number
					paym = Ctable3.getSelectedItem().toString(); // ������� payment_method
					id = Input1.getText(); // member_id
					n3 = seat;
					// MySQL�� ������Ʈ ����
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");

						String url = "jdbc:mysql://localhost/madang";

						conn = DriverManager.getConnection(url, "madang", "madang");

						stmt = conn.createStatement();
						
						// System.out.println("INSERT INTO Reservation VALUES(0," + '\'' + paym + "\',"
						// + '\''+ "�������" + "\'," + "10000," + '\''+ id + "\',\'" + "2021-05-19
						// 11:30:08" +"\');");
						stmt.executeUpdate("INSERT INTO Reservation VALUES(0," + '\'' + paym + "\'," + '\'' + "�������"
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
						
						// ���� ����
						setVisible(false);
						l6.setVisible(false);
					} catch (ClassNotFoundException e1) {
						// ����̹� �ε� ���� System.out.println("����̹� �ε� ����");
						l6.setVisible(true);
					} catch (SQLException e1) {
						// ���� System.out.println("����: " + e1);
						l6.setVisible(true); // ���� ����
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
		setTitle("����");
	}
}

class Change2 extends JFrame {
	Change2(String rn) {
		// Container ��ü
		Container c = getContentPane();
		c.setLayout(null);

		JLabel l1 = new JLabel("��ȭ ");
		JLabel l2 = new JLabel("����");
		JLabel l3 = new JLabel("�¼� ");
		JLabel l4 = new JLabel("");
		
		JComboBox<String> Ctable = new JComboBox<String>();
		JComboBox<String> Cb2= new JComboBox<String>();
		JComboBox<String> Cb3 = new JComboBox<String>();

		JTextField Input1 = new JTextField(8);
		JTextField Input2 = new JTextField(8);

		JButton Cb = new JButton("���� ����");
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
		
		
		// MYSQL ��ü
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		// MySQL�� ������Ʈ ����
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
			// ����̹� �ε� ���� System.out.println("����̹� �ε� ����");
		} catch (SQLException e1) {
			// ���� System.out.println("����: " + e1);
			//l4.setText("������ ���濡 �����߽��ϴ�."); // ���� ����
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
			// MYSQL ��ü
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;

			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton) e.getSource();

				// ����ٰ� ��ư�� ������ �� �� �� ����
				if (b.getText().equals("���� ����")) { // ���� ��ư
					String movieT = (String) Ctable.getSelectedItem(); // movie_title
					String scheDT = (String) Cb2.getSelectedItem(); // show_date
					String seatId = (String) Cb3.getSelectedItem(); // seat_number
					// rn = reservation_number
					// MySQL�� ������Ʈ ����
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");

						String url = "jdbc:mysql://localhost/madang";

						conn = DriverManager.getConnection(url, "madang", "madang");

						stmt = conn.createStatement();
						
						// ������Ʈ ������� Ticket ���̺�
						// ���� ��Ʈ N���� ���ο� ��Ʈ Y�� ���巷����!
						// ��ü �����ؼ� ȭ�鿭 ��, �Ű������� ���� Reservation_number �޾ƿ���
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
						
						l4.setText("���Ÿ� ���������� �����߽��ϴ�.");
						setVisible(false);
					} catch (ClassNotFoundException e1) {
						// ����̹� �ε� ���� System.out.println("����̹� �ε� ����");
					} catch (SQLException e1) {
						// ���� System.out.println("����: " + e1);
						l4.setText("������ ���濡 �����߽��ϴ�."); // ���� ����
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
			// MYSQL ��ü
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			public void actionPerformed(ActionEvent e) {
				String strT = Ctable.getSelectedItem().toString();
				// MySQL�� ������Ʈ ����
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
					
					// cb2�� addItem ����
					Cb2.removeAllItems();
					while (rs.next()) {
						Cb2.addItem(rs.getString("show_date"));
						if(i++==0) {
							sid = rs.getString("Theater_number"); // ���⼭ ������ �� ����
						}
					}
					
					// �󿵰��� ���� �� seat ��� �����ͼ� Cb3�� ���� addItem�������
					rs2 = stmt.executeQuery("SELECT * FROM Seat WHERE Theater_number=" + sid + " AND seat_status=\'N\';");
					
					Cb3.removeAllItems();
					while (rs2.next()) {
						Cb3.addItem(rs2.getString("seat_number"));
					}
					
				} catch (ClassNotFoundException e1) {
					// ����̹� �ε� ���� System.out.println("����̹� �ε� ����");
				} catch (SQLException e1) {
					// ���� System.out.println("����: " + e1);
					l4.setText("������ ���濡 �����߽��ϴ�."); // ���� ����
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
			// MYSQL ��ü
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			public void actionPerformed(ActionEvent e) {
				String strT = Ctable.getSelectedItem().toString();
				// MySQL�� ������Ʈ ����
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
							sid = rs.getString("Theater_number"); // ���⼭ ������ �� ����
							sen.setText(rs.getString("schedule_number"));
						}
					}
					
					tn.setText(sid);
					
					// �󿵰��� ���� �� seat ��� �����ͼ� Cb3�� ���� addItem�������
					rs2 = stmt.executeQuery("SELECT * FROM Seat WHERE Theater_number=" + sid + " AND seat_status=\'N\';");
					
					Cb3.removeAllItems();
					while (rs2.next()) {
						Cb3.addItem(rs2.getString("seat_number"));
					}
					l4.setText("");
				} catch (ClassNotFoundException e1) {
					// ����̹� �ε� ���� System.out.println("����̹� �ε� ����");
				} catch (SQLException e1) {
					// ���� System.out.println("����: " + e1);
					l4.setText("������ ���濡 �����߽��ϴ�."); // ���� ����
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
		setTitle("����");
	}
}