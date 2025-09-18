// JDBC 프로그래밍 - DBMS에 SQL문 보내기 : getXxx()
package com.eomcs.jdbc.ex1;

import java.sql.DriverManager;

public class Exam0321 {

  public static void main(String[] args) throws Exception {
    try (
        java.sql.Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/studydb", "study", "1111");
        java.sql.Statement stmt = con.createStatement();
        java.sql.ResultSet rs = stmt.executeQuery(//
            "select * from x_board order by board_id desc");
        ) {

      boolean isReceived = rs.next();
      if (isReceived) {
        System.out.printf("%d, %s, %s, %s, %s, %d\n",
            rs.getInt(1), // board_id
            rs.getString(2), // title
            rs.getString(3), // contents
            rs.getDate(4), // created_date
            rs.getTime(4), // created_date
            rs.getInt(5)); // view_count

        // getXxx(컬럼번호):
        // => DBMS에 설정된 컬럼의 타입에 따라 값을 변환해서 받고 싶다면,
        //    다음과 같이 해당 타입의 값을 리턴하는 getXxx()를 호출한다.
        // => int, number: getInt()
        // => char, varchar, text: getString()
        // => date, time, datetime: getDate(), getTime()
        // => float: getFloat()
        //

      } else {
        System.out.println("서버에서 한 개의 레코드를 가져오지 못했다!");
      }
    }
  }
}


