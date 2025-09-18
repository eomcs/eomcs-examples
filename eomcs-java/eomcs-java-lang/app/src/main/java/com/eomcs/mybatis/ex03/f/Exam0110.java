// #{} 문법의 한계
package com.eomcs.mybatis.ex03.f;

import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0110 {

  public static void main(String[] args) throws Exception {
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex03/f/mybatis-config.xml")).openSession();

    // 정렬 방식을 파라미터로 넘기기
    // => #{} 문법은 오직 값만 삽입할 수 있다.
    // => SQL 코드를 삽입할 수 없다.
    // => 파라미터 값을 SQL에 그대로 삽입하려면
    //    문자열 치환 문법인 ${} 를 사용해야 한다.
    List<Board> boards = sqlSession.selectList("BoardMapper.select1", "desc");

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


