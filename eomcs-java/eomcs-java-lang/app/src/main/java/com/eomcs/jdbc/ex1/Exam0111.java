// JDBC 드라이버 준비 - 드라이버 다운로드 및 로딩
package com.eomcs.jdbc.ex1;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Exam0111 {

  public static void main(String[] args) {

    try {
      java.sql.Driver mariadbDriver = new org.mariadb.jdbc.Driver(); 
      DriverManager.registerDriver(mariadbDriver);
      System.out.println("JDBC 드라이버 로딩 및 등록 완료!");

      // 등록되지 않은 드라이버를 찾을 경우 예외 발생!
      java.sql.Driver driver = DriverManager.getDriver("jdbc:db2://");
      System.out.println(driver);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
