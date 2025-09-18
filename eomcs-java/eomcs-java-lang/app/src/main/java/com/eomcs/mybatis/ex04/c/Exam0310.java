// Dynamic SQL - 무조건 false가 되는 조건을 포함해야 하는 경우 
package com.eomcs.mybatis.ex04.c;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0310 {

  public static void main(String[] args) throws Exception {
    Scanner keyboard = new Scanner(System.in);

    System.out.print("제목?(건너 뛰기: 빈 문자열) ");
    String title = keyboard.nextLine();

    System.out.print("내용?(건너 뛰기: 빈 문자열) ");
    String contents = keyboard.nextLine();

    keyboard.close();

    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex04/c/mybatis-config.xml")).openSession();

    // 사례1)
    //    where 
    //      <if test="title != null">
    //        title like concat('%', #{title}, '%')
    //      </if>
    //      <if test="contents != null">
    //        or contents like concat('%', #{contents}, '%')
    //      </if>
    //
    // - 위 where 절의 경우 
    //   => 검색어로 제목을 입력하면 해당 검색어를 제목으로 포함하는 게시글을 결과에 포함한다.
    //   => 검색어로 내용을 입력하면 해당 검색어를 내용으로 포함하는 게시글을 결과에 포함한다.
    //   => 만약 둘 다 입력하지 않으면 where 절 문법 오류가 발생한다.
    // 
    // 요구사항
    // - 검색어를 모두 입력하지 않았을 때 SQL 오류를 발생시키지 않고 빈 결과를 리턴하게 하는 방법은?
    //   => where 절에 무조건 false를 리턴하는 조건을 추가하면 된다.
    // 
    HashMap<String,Object> params = new HashMap<>();

    if (title.length() > 0) {
      params.put("title", title);
    }

    if (contents.length() > 0) {
      params.put("contents", contents);
    }

    List<Board> boards = sqlSession.selectList("BoardMapper.select0310", params);

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


