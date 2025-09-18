// Dynamic SQL - trim 태그 사용법
package com.eomcs.mybatis.ex04.c;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0230 {

  public static void main(String[] args) throws Exception {
    Scanner keyboard = new Scanner(System.in);

    System.out.print("제목?(건너 뛰기: 빈 문자열) ");
    String title = keyboard.nextLine();

    System.out.print("내용?(건너 뛰기: 빈 문자열) ");
    String contents = keyboard.nextLine();

    keyboard.close();

    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex04/c/mybatis-config.xml")).openSession();

    // 내용만 입력 했을 때 문제점
    // => 다음과 같이 where 다음에 내용을 검사하는 코드가 붙는다.
    //    문제는 제목을 검사하는 조건이 빠지는 바람에 
    //    내용을 검사하는 코드 앞에 and 가 남아 있어서 SQL 문법 오류가 발생한다.
    ///         select board_id, title, contents, created_date, view_count
    //          from x_board
    //          where and contents like concat('%', ?, '%')
    //          order by board_id desc
    // 해결책)
    //   - <where> 태그를 사용하면 된다.
    //   - <trim> 태그를 사용하여 쓸데없이 붙은 and 나 or 를 제거하면 된다.
    // 
    HashMap<String,Object> params = new HashMap<>();

    if (title.length() > 0) {
      params.put("title", title);
    }

    if (contents.length() > 0) {
      params.put("contents", contents);
    }

    List<Board> boards = sqlSession.selectList("BoardMapper.select0230", params);

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


