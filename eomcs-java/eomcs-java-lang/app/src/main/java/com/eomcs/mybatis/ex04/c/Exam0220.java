// Dynamic SQL - where 태그 사용법 II
package com.eomcs.mybatis.ex04.c;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0220 {

  public static void main(String[] args) throws Exception {
    Scanner keyboard = new Scanner(System.in);

    System.out.print("제목?(건너 뛰기: 빈 문자열) ");
    String title = keyboard.nextLine();

    System.out.print("내용?(건너 뛰기: 빈 문자열) ");
    String contents = keyboard.nextLine();

    keyboard.close();

    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex04/c/mybatis-config.xml")).openSession();

    // <where> 태그를 사용하면 
    // 1) 조건이 비어 있을 때 where 절을 붙이기 않는다. 
    // 2) and, or 잔여물이 붙어 있을 때 자동으로 제거한다. 
    // 
    HashMap<String,Object> params = new HashMap<>();

    if (title.length() > 0) {
      params.put("title", title);
    }

    if (contents.length() > 0) {
      params.put("contents", contents);
    }

    List<Board> boards = sqlSession.selectList("BoardMapper.select0220", params);

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


