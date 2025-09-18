// PreparedStatement를 이용하여 SQL 삽입 공격 차단하기
package com.eomcs.jdbc.ex3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Exam0210 {

  public static void main(String[] args) throws Exception {
    String title = null;
    String contents = null;

    try (Scanner keyboard = new Scanner(System.in)) {
      System.out.print("제목? ");
      title = keyboard.nextLine();

      System.out.print("내용? ");
      contents = keyboard.nextLine();
    }

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb", "study", "1111");
        PreparedStatement stmt = con.prepareStatement(
            "insert into x_board(title,contents) values(?,?)")) {

      // SQL 삽입 공격
      // => 입력 문자열에 SQL 명령을 삽입하여 프로그램의 의도와 다르게 데이터를 조작하는 행위.
      // => 사용자가 입력한 값을 가지고 SQL 문장을 만들 때 이런 문제가 발생한다.
      // => 예를 들어 이 예제를 실행할 때 다음과 같이 입력해 보라!
      // 제목? aaaa
      // 내용? bbbb'), ('haha', 'hoho'), ('hehe', 'puhul
      //

      // 위에서 준비한 SQL 문에 값을 설정한다.
      // => ? : 값이 놓일 자리를 의미한다. 'in-parameter' 라 부른다.
      // => in-parameter 에 들어갈 값의 타입에 따라 적절한 setXxx() 메서드를 호출한다.
      //
      stmt.setString(1, title);
      stmt.setString(2, contents);

      // => 이미 SQL 을 준비한 상태이기 때문에 실행할 때는 SQL를 줄 필요가 없다.
      // => setXxx()로 설정된 값은 단순한 텍스트로 처리한 후
      //    SQL을 실행할 때 파라미터로 전달되기 때문에
      //    SQL 삽입 공격이 불가능 하다.
      int count = stmt.executeUpdate();

      System.out.println(count + " 개를 입력하였습니다.");
    }
  }
}
