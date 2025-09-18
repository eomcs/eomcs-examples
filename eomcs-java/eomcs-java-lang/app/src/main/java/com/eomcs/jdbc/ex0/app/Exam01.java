package com.eomcs.jdbc.ex0.app;

import com.eomcs.jdbc.ex0.api.Statement;
import com.eomcs.jdbc.ex0.jre.driver.Type1Statement;
import com.eomcs.jdbc.ex0.microsoft.typ2.MSType2Statement;
import com.eomcs.jdbc.ex0.oracle.type2driver.OracleType2Statement;
import com.eomcs.jdbc.ex0.oracle.type4driver.OracleType4Statement;

public class Exam01 {

  public static void main(String[] args) throws Exception {
    // Statement 객체를 사용하여 SQL 을 실행해 보자.
    String sql = "insert into board values('제목...', '내용...', '홍길동')";

    // Statement 객체 만들기

    // Statement 는 SQL을 실행하는 객체의 사용 규칙을 정의한 인터페이스다.
    // 직접 객체를 만들어 사용할 수 없다.
    // 해결책?
    // - Statement 규칙에 따라 정의한 클래스(드라이버)의 객체를 만들어야 한다.
    // Statement stmt = new Statement();

    // [Oracle DBMS를 사용하여 SQL을 실행하기]
    // 
    // 방법1: JRE에서 기본으로 제공하는 type1 드라이버 사용하기
    // - 단 이 드라이버는 실행될 때 ODBC Driver를 사용하기 때문에
    //   반드시 로컬에 ODBC 드라이버가 설치되어 있어야 한다.
    Statement stmt = new Type1Statement(/* 어느 DBMS의 ODBC 드라이버를 사용할 지 파라미터로 넘긴다*/);
    stmt.executeUpdate(sql);

    // 방법2: Oracle에서 제공하는 type2 드라이버 사용하기
    // - 단 이 드라이버는 Oracle vendor api를 사용하기 때문에 
    //   반드시 로컬에 오라클 벤더 API 가 설치되어 있어야 한다.
    Statement stmt2 = new OracleType2Statement();
    stmt2.executeUpdate(sql);
    // 집중!
    // - 드라이버를 교체하더라도 SQL을 실행시키는 메서드 사용법은 같다!!!
    // 

    // 방법3: Oracle에서 제공하는 type4 드라이버 사용하기
    // - 이 드라이버는 내부적으로 직접 오라클 DBMS에 접속하기 때문에 
    //   로컬에 따로 C/C++ 드라이버를 설치할 필요가 없다.
    Statement stmt3 = new OracleType4Statement();
    stmt3.executeUpdate(sql);
    // 집중!
    // - 드라이버를 교체하더라도 SQL을 실행시키는 메서드 사용법은 같다!!!
    //

    // [MS-SQL DBMS로 교체하기]
    // 
    // 방법4: Microsoft에서 제공하는 MS-SQL type2 드라이버 사용하기
    // - 단 이 드라이버는 MS-SQL vendor api를 사용하기 때문에 
    //   반드시 로컬에 MS-SQL 벤더 API 가 설치되어 있어야 한다.
    Statement stmt4 = new MSType2Statement();
    stmt2.executeUpdate(sql);
    // 집중!
    // - 드라이버를 교체하더라도 SQL을 실행시키는 메서드 사용법은 같다!!!
    // 

  }

}
