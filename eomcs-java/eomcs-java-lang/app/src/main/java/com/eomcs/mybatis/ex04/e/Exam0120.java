// Dynamic SQL - foreach 태그의 활용
package com.eomcs.mybatis.ex04.e;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0120 {

  public static void main(String[] args) throws Exception {
    Scanner keyboard = new Scanner(System.in);

    ArrayList<String> keywords = new ArrayList<>();

    while (true) {
      System.out.print("검색어?(빈 문자열: 입력 완료!) ");
      String keyword = keyboard.nextLine();
      if (keyword.length() == 0) {
        break;
      }

      keywords.add(keyword);
    }

    keyboard.close();

    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex04/e/mybatis-config.xml")).openSession();

    // 값을 여러 개 넘길 때는 항상 맵에 담아서 넘겨라!
    HashMap<String,Object> params = new HashMap<>();
    params.put("keywords", keywords);

    // foreach 태그를 사용하면 
    // - 값의 개수 만큼 SQL 코드를 반복해서 생성할 수 있다.
    // 
    List<Board> boards = sqlSession.selectList("BoardMapper.select2", params);

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


