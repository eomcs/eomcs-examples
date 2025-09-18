// bind 태그 사용법 - 사용 후
package com.eomcs.mybatis.ex04.g;

import java.util.List;
import java.util.Scanner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0120 {

  public static void main(String[] args) throws Exception {

    Scanner keyboard = new Scanner(System.in);

    System.out.print("검색어? ");
    String keyword = keyboard.nextLine();

    keyboard.close();

    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex04/g/mybatis-config.xml")).openSession();

    // 메서드를 실행할 때 넘겨준 값으로 
    // mybatis 는 <bind> 태그를 사용하여 like 연산에 사용할 값을 준비한다.
    // 
    List<Board> boards = sqlSession.selectList("BoardMapper.select2", keyword);

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


