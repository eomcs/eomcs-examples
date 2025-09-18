// Statement 와 SQL 삽입 공격
package com.eomcs.jdbc.ex3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class Exam0110 {

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
        Statement stmt = con.createStatement()) {

      // SQL 삽입 공격
      // => 입력 문자열에 SQL 명령을 삽입하여 프로그램의 의도와 다르게 데이터를 조작하는 행위.
      // => 사용자가 입력한 값을 가지고 SQL 문장을 만들 때 이런 문제가 발생한다.
      // => 예를 들어 이 예제를 실행할 때 다음과 같이 입력해 보라!
      // 제목? aaaa
      // 내용? bbbb'), ('haha', 'hoho'), ('hehe', 'puhul
      //
      int count = stmt.executeUpdate(
          "insert into x_board(title, contents) values('" + title + "','" + contents + "')");

      // 위에서 사용자가 입력한 값을 가지고 SQL 문장을 만들면 다음과 같다.
      //
      // insert into x_board(title, contents) values('aaaa','bbbb'), ('haha', 'hoho'), ('hehe', 'puhul')
      //

      System.out.println(count + " 개를 입력하였습니다.");
    }
  }
}
