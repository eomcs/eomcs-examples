// SQL 문에 삽입할 파라미터 전달하기 - 한 개의 int 값 넘기기
package com.eomcs.mybatis.ex03.a;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0110 {

  public static void main(String[] args) throws Exception {
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex03/a/mybatis-config.xml")).openSession();

    // in-parameter에 int 값 넘기기
    // => SQL을 실행할 때 파라미터 값을 전달하려면
    //   두 번째 파라미터로 전달해야 한다.
    //      예) selectOne(sqlID, in-parameter 값)
    // => in-parameter 값의 타입은 Object이다.
    //    자바 원시 타입의 값을 지정하면 자동으로 오토 박싱되어 mybatis에 전달된다.
    // => 여러 개의 값을 전달해야 한다면,
    //    도메인 객체나 Map 객체에 담아 전달하라!

    // 예) 특정 번호의 게시글을 가져온다.
    Board board = sqlSession.selectOne("BoardMapper.select1", 5); // auto-boxing 수행

    if (board != null) {
      System.out.printf("%d,%s,%s,%s,%d\n",
          board.getNo(),
          board.getTitle(),
          board.getContent(),
          board.getRegisteredDate(),
          board.getViewCount());
    }
    sqlSession.close();
    System.out.println("실행 완료!");
  }

}


