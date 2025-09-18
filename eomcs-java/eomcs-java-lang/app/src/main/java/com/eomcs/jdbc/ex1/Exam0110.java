// JDBC 드라이버 준비 - 드라이버 다운로드 및 로딩
package com.eomcs.jdbc.ex1;

import java.sql.DriverManager;
import java.sql.SQLException;

// DBMS에 연결하기
// => MariaDB에 연결을 대행하는 클래스를 사용한다.
// => 이 클래스는 JDBC API 규칙에 따라 작성되었다.
// => 이 클래스는 JDBC Driver 파일(*.jar)에 들어 있다.
// => 이 클래스를 사용하려면 먼저 이 JDBC Driver 파일을 다운로드 받아
//    프로젝트의 CLASSPATH에 등록해야 한다.
// => 절차
// 1) mvnrepository.com 또는 search.maven.org에서 mariadb jdbc driver를 검색한다.
// 2) 라이브러리 정보를 build.gradle 파일에 설정한다.
// 3) gradle을 이용하여 eclipse 설정 파일을 갱신한다.
//    > gradle eclipse
//    - 다운로드 받지 않은 라이브러리가 있다면 자동으로 서버에서 받을 것이다.
//    - 라이브러리 정보가 변경되었다면 해당 라이브러리를 서버에서 받을 것이다.
//    - .classpath(이클립스 자바 프로젝트의 classpath 정보를 담고 있는 파일) 파일에
//      다운로드 받은 JDBC Driver 파일의 경로를 추가할 것이다.
// 4) 이클립스 프로젝트를 리프래시 한다.
//    - 프로젝트에 mariadb jdbc driver 라이브러리가 추가되었는지 확인한다.
//
public class Exam0110 {

  public static void main(String[] args) {

    // JDBC 드라이버 등록
    // => java.sql.Driver 규칙에 따라 정의된 클래스를 로딩한다.
    // => Driver 구현체는 JDBC에 대한 정보를 제공한다.
    // => 또한 DBMS에 연결작업을 수행한다.
    // => Driver 구현체는 DriverManager가 관리한다.
    // => 따라서 접속할 DBMS의 Driver 구현체를 생성하여 DriverManager에게 등록해야 한다.
    //
    // DriverManager
    // => java.sql.Driver 구현 객체를 관리하는 일을 한다.
    // => DBMS 연결 요청이 들어오면 해당 DBMS의 Driver 구현체를 찾아 작업을 위임한다.
    //
    //
    // JDBC 드라이버 등록 방법1: 직접 Driver 구현 객체를 생성하고 직접 등록하기
    // => java.sql.Driver 구현체를 생성하여 JDBC 드라이버 관리자에 등록한다.
    // => MariaDB의 JDBC 드라이버에는 org.mariadb.jdbc.Driver 클래스가 이 구현체이다.
    try {

      // 1) java.sql.Driver 구현체의 인스턴스를 생성한다.
      java.sql.Driver mariadbDriver = new org.mariadb.jdbc.Driver();
      java.sql.Driver oracleDriver = new oracle.jdbc.OracleDriver();
      java.sql.Driver mssqlDriver = new com.microsoft.sqlserver.jdbc.SQLServerDriver();
      java.sql.Driver mysqlDriver = new com.mysql.jdbc.Driver();

      // 2) java.sql.Driver 구현체의 인스턴스를 드라이버 관리자에 등록한다.
      DriverManager.registerDriver(mariadbDriver);
      DriverManager.registerDriver(oracleDriver);
      DriverManager.registerDriver(mssqlDriver);
      DriverManager.registerDriver(mysqlDriver);

      System.out.println("JDBC 드라이버 로딩 및 등록 완료!");

      // DriverManager에 등록된 Driver 인스턴스를 확인해보자!
      // => DriverManager.getDriver(jdbcUrl);
      // => jdbcUrl
      // jdbc:[DBMS]://서버주소:포트번호/데이터베이스명
      java.sql.Driver driver = DriverManager.getDriver("jdbc:mariadb://");
      System.out.println(driver);

      java.sql.Driver driver2 = DriverManager.getDriver("jdbc:oracle:thin://");
      System.out.println(driver2);

      java.sql.Driver driver3 = DriverManager.getDriver("jdbc:sqlserver://");
      System.out.println(driver3);

      java.sql.Driver driver4 = DriverManager.getDriver("jdbc:mysql://");
      System.out.println(driver4);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
