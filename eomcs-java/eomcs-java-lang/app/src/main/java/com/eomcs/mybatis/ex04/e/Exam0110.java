// Dynamic SQL - foreach 태그의 필요성
package com.eomcs.mybatis.ex04.e;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0110 {

  public static void main(String[] args) throws Exception {
    Scanner keyboard = new Scanner(System.in);

    System.out.print("검색어1? ");
    String keyword1 = keyboard.nextLine();

    System.out.print("검색어2? ");
    String keyword2 = keyboard.nextLine();

    System.out.print("검색어3? ");
    String keyword3 = keyboard.nextLine();

    keyboard.close();

    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex04/e/mybatis-config.xml")).openSession();

    // 여러 개의 검색어로 데이터를 찾을 때
    // => 최대 3개의 검색어로 제한하는 경우
    HashMap<String,Object> params = new HashMap<>();
    params.put("keyword1", keyword1);
    params.put("keyword2", keyword2);
    params.put("keyword3", keyword3);

    // if 태그를 사용하는 방식의 한계는
    // - 조건의 개수가 고정된다.
    // - 즉 if 태그의 개수만큼만 조건을 검사할 수 있다.
    // - 조건 개수를 늘리려면 if 태그를 추가해야 한다.
    // 해결책?
    // - foreach 태그를 사용하라!
    // 
    List<Board> boards = sqlSession.selectList("BoardMapper.select1", params);

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


