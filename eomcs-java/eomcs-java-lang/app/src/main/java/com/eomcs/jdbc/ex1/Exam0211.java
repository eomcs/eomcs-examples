// JDBC 프로그래밍 - DBMS에 연결하기
package com.eomcs.jdbc.ex1;

import java.sql.DriverManager;

public class Exam0211 {

  public static void main(String[] args) throws Exception {
    // close() 호출의 중요성
    int count = 0;
    while (true) {
      java.sql.Connection con = null;

      try {
        con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/studydb", "study", "1111");
        System.out.println("DBMS와 연결됨!");

        System.out.println(++count);
      } catch (Exception e) {
        e.printStackTrace();
        Thread.sleep(30000);
        break;
      } finally {
        try {
          // close()를 호출하지 않으면,
          // 서버쪽에서는 일정 기간(timeout) 동안 커넥션을 유지한다.
          con.close();
        } catch (Exception e) {
        }
        System.out.println("DBMS와 연결 해제됨!");
      }

    }
  }
}


