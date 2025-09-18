// Dynamic SQL - where 태그의 필요성 II
package com.eomcs.mybatis.ex04.c;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0210 {

  public static void main(String[] args) throws Exception {
    Scanner keyboard = new Scanner(System.in);

    System.out.print("제목?(건너 뛰기: 빈 문자열) ");
    String title = keyboard.nextLine();

    System.out.print("내용?(건너 뛰기: 빈 문자열) ");
    String contents = keyboard.nextLine();

    keyboard.close();

    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex04/c/mybatis-config.xml")).openSession();

    // where 절에 여러 개의 조건이 있을 경우 발생할 수 있는 문제점을 확인해 보자.
    // 테스트 1)
    //   - 제목, 내용 모두 검색어를 입력한다.
    //     => 해당 제목과 내용을 포함하고 있는 게시글을 정상적으로 리턴한다.
    // 테스트 2)
    //   - 제목만 입력한다.
    //     => where 다음에 title 컬럼의 조건을 검사하는 코드가 붙기 때문에 
    //        정상적으로 수행한다.
    // 테스트 3)
    //   - 제목, 내용 모두 빈 문자열로 입력한다.
    //     => where 다음에 조건을 지정하는 SQL 문이 없기 때문에 SQL 문법 오류가 발생한다.
    // 테스트 4)
    //   - 내용만 입력한다.
    //     => where 다음에 내용을 검사하는 코드가 붙는다.
    //        문제는 제목을 검사하는 조건이 빠지는 바람에 
    //        내용을 검사하는 코드 앞에 and 가 남아 있어서 SQL 문법 오류가 발생한다.
    ///         select board_id, title, contents, created_date, view_count
    //          from x_board
    //          where and contents like concat('%', ?, '%')
    //          order by board_id desc
    // 해결책)
    //   - <where> 태그를 사용하면 
    //     1. 조건이 비어 있을 때 where 절을 붙이기 않는다. 
    //     2. and, or 잔여물이 붙어 있을 때 자동으로 제거한다. 
    //   - <trim> 태그를 사용하면
    //     1. 제목이 빠진 후에 내용 조건을 검사하는 코드 앞에 and 가 붙는 것을 제거할 수 있다.
    //
    HashMap<String,Object> params = new HashMap<>();

    if (title.length() > 0) {
      params.put("title", title);
    }

    if (contents.length() > 0) {
      params.put("contents", contents);
    }

    List<Board> boards = sqlSession.selectList("BoardMapper.select0210", params);

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


