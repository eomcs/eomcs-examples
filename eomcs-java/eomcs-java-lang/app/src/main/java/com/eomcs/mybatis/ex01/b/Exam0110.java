// Mybatis 설정 파일 - DBMS 연결 정보를 .properties 파일로 분리하기
package com.eomcs.mybatis.ex01.b;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Exam0110 {

  public static void main(String[] args) throws Exception {

    // 1) jdbc.properties 파일을 추가한다.
    //    => 이 파일에 DB 접속 정보를 저장한다.
    // 2) mybatis-config.xml 파일을 변경한다.
    //    => DB 접속 정보를 설정할 때 jdbc.properties 파일의 데이터를 사용한다.
    //
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex01/b/mybatis-config.xml")).openSession();

    System.out.println("mybatis 사용 준비 완료!");

    sqlSession.close();
  }

}


