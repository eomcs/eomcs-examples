// <sql> 태그 사용법 (사용 후)
package com.eomcs.mybatis.ex04.a;

import java.util.List;
import java.util.Scanner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0120 {

  public static void main(String[] args) throws Exception {
    Scanner keyboard = new Scanner(System.in);

    System.out.print("검색 항목:\n  1. 제목\n  2. 내용\n검색 항목? ");
    int menuNo = Integer.parseInt(keyboard.nextLine());

    if (!(menuNo == 1 || menuNo == 2)) {
      System.out.println("검색 항목 번호가 옳지 않습니다.");
      keyboard.close();
      return;
    }

    System.out.print("검색어? ");
    String keyword = keyboard.nextLine();

    keyboard.close();

    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex04/a/mybatis-config.xml")).openSession();

    List<Board> boards = null;

    // 여러 SQL문에 공통들어 가는 코드가 있다면 
    // <sql> 태그를 사용하여 별도로 정의할 수 있다.
    // <include> 태그를 사용하여 <sql> 태그의 값을 가져올 수 있다.
    // 
    if (menuNo == 1) {
      boards = sqlSession.selectList("BoardMapper.select3", keyword);
    } else if (menuNo == 2) {
      boards = sqlSession.selectList("BoardMapper.select4", keyword);
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


