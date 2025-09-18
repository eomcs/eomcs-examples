// JDBC 프로그래밍 - DBMS에 연결하기
package com.eomcs.jdbc.ex1;

import java.sql.DriverManager;

public class Exam0220 {

  public static void main(String[] args) throws Exception {

    // try-with-resources 문법을 사용하면
    // try 블록을 벗어날 때 close()가 자동 호출된다.
    //
    try (java.sql.Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb", "study", "Bitcamp!@#123");) {

      System.out.println("DBMS와 연결됨!");

    }
    System.out.println("DBMS와 연결 해제됨!");
  }
}


