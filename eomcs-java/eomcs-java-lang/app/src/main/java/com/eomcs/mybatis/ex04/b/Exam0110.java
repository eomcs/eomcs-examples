// Dynamic SQL - if 태그 사용법(사용 전)
package com.eomcs.mybatis.ex04.b;

import java.util.List;
import java.util.Scanner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0110 {

  public static void main(String[] args) throws Exception {
    Scanner keyboard = new Scanner(System.in);

    System.out.print("검색 항목:\n  1. 제목\n  2. 내용\n검색 항목? ");
    int menuNo = Integer.parseInt(keyboard.nextLine());

    System.out.print("검색어? ");
    String keyword = keyboard.nextLine();

    keyboard.close();

    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex04/b/mybatis-config.xml")).openSession();

    List<Board> boards = null;

    // dynamic sql?
    // => 조건에 따라 SQL이 변경되는 것.
    // => mybatis는 이를 위해 조건문, 반복문 등을 다룰 수 있도록 특별한 태그를 제공한다.
    //
    // dynamic sql 사용 전:
    // - 검색 항목 선택에 따라 SQL 문이 달라지기 때문에 
    //   다음과 같이 where 절의 일부분만 다른,
    //   SQL문을 여러 개 작성해야 한다.
    // - 그리고 조건에 따라 실행할 SQL 문을 구분해서 호출해야 한다.
    // 
    if (menuNo == 1) {
      boards = sqlSession.selectList("BoardMapper.select1", keyword);
    } else if (menuNo == 2) {
      boards = sqlSession.selectList("BoardMapper.select2", keyword);
    }

    for (Board b : boards) {
      System.out.printf("%d,%s,%s,%s,%d\n",
          b.getNo(),
          b.getTitle(),
          b.getContent(),
          b.getRegisteredDate(),
          b.getViewCount());
    }

    sqlSession.close();
  }

}


