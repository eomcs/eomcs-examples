// JDBC 드라이버 준비 - DriverManager가 Driver 구현체를 자동 로딩 II
package com.eomcs.jdbc.ex1;

import java.sql.DriverManager;

public class Exam0141 {

  public static void main(String[] args) {

    // JDBC 드라이버 로딩 방법4: Driver 구현체 자동 로딩
    // => DriverManager를 사용할 때,
    //    DriverManager 는 다음 절차에 따라 Driver 구현체를 찾아서 자동으로 로딩한다.
    //
    // 2) java.sql.Driver 클래스의 서비스 제공자를 찾아 로딩한다.
    // => jar 파일 안에 META-INF/services/java.sql.Driver 파일을 찾는다.
    // => 이때 JVM은 'service-provider loading' 절차에 따라 이 파일에 등록된 클래스를 로딩한다.
    // => jar 파일에 해당 정보가 있다면,
    //    앞의 예제처럼 개발자가 따로 java.sql.Driver 구현체를 명시적으로 등록하지 않아도 된다.
    // => mariadb JDBC 드라이버 jar 파일은 이 정보가 들어 있다.
    //    따라서 java.sql.Driver를 구현한 클래스를 로딩하거나 생성할 필요가 없다.
    //

    // 현재 jdbc.drivers 에 등록된 클래스 이름이 없다.
    System.out.printf("jdbc.drivers=%s\n", System.getProperty("jdbc.drivers"));

    try {
      // Driver 구현체를 명시적으로 로딩하지 않는다!

      // DriverManager에 자동 등록된 것을 확인해보자!
      java.sql.Driver driver = DriverManager.getDriver("jdbc:mariadb://");
      System.out.println(driver);

      // MariaDB Driver를 찾을 수 있다!
      // 왜?
      // - JVM은 프로그램을 실행할 때
      //   'service-provider loading' 절차에 따라
      //   .jar 파일에 있는 특정 클래스를 찾아 로딩하거나 객체를 자동으로 생성한다.
      // - JDBC의 경우도 이 규칙을 따른다.
      //   DriverManager의 getDriver()를 호출할 때
      //   'service-provider loading' 규칙에 따라 .jar 파일에서
      //   Driver 구현체를 찾아 자동으로 로딩한다.

      // 이 프로젝트에 Oracle JDBC 드라이버와 MSSQL Server의 JDBC 드라이버도 있다.
      // 이들 드라이버도 'service-provider loading' 규칙에 따라 만들어졌기 때문에
      // 따로 Driver 구현체를 생성한다거나 등록한다거나 클래스를 로딩하는 일을 하지 않아도
      // 드라이버를 찾을 수 있을 것이다.
      java.sql.Driver driver2 = DriverManager.getDriver("jdbc:oracle:thin://");
      System.out.println(driver2);

      java.sql.Driver driver3 = DriverManager.getDriver("jdbc:sqlserver://");
      System.out.println(driver3);

      System.out.println("테스트!");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


