// Mybatis 설정 파일 - SQL 매퍼 파일 설정과 SQL 문 사용법
package com.eomcs.mybatis.ex01.c;

import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Exam0110 {

  public static void main(String[] args) throws Exception {


    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex01/c/mybatis-config.xml")).openSession();

    // 1) SQL 매퍼 파일 준비
    //  - mybatis.org 사이트에서 문서 페이지를 참조한다.
    //  - SQL이 들어 있는 파일(예: BoardMapper.xml)을 생성한다.
    //  - 문서 페이지를 참조하여 매퍼 파일의 내용을 변경한다.
    // 2) select 결과를 저장할 클래스 정의
    //  - Board 클래스 생성
    // 3) BoardMapper.xml 파일에 보관된 select 문 실행하기
    //  - SQL을 실행하고 결과 데이터를 객체에 담아 리턴한다.
    //  - mybatis 문서 페이지를 참조한다.
    //  - SqlSession.selectList("네임스페이스명.SQL아이디")
    //
    List<Board> boards = sqlSession.selectList("BoardMapper.selectBoard");

    System.out.println(boards.size());

    sqlSession.close();
  }

}


