// JDBC 프로그래밍 - DBMS에 연결하기
package com.eomcs.jdbc.ex1;

import java.sql.DriverManager;

public class Exam0221 {

  public static void main(String[] args) throws Exception {

    int count = 0;
    while (true) {
      try (java.sql.Connection con = DriverManager.getConnection(
          "jdbc:mariadb://localhost:3306/studydb", "study", "1111");) {
        System.out.printf("DBMS와 연결됨 = %d\n", ++count);
      }
      System.out.println("DBMS와 연결 해제됨!");
    }
  }
}


