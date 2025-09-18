// 트랜잭션 다루기 - openSession() : 수동 커밋
package com.eomcs.mybatis.ex03.d;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0120 {

  public static void main(String[] args) throws Exception {

    // openSession(auto commit 여부)
    // - true : 데이터 변경 후 즉시 커밋한다.
    // - false : 데이터 변경 후 commit()을 호출할 때까지 실제 테이블에 변경 사항을 반영하지 않는다.
    // 
    // openSession()
    // - 기본이 수동 커밋이다.
    // - openSession(false) 와 같다.
    // 
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex03/d/mybatis-config.xml")).openSession();

    Board board = new Board();
    board.setTitle("제목입니다yyy");
    board.setContent("내용입니다yyy");

    int count = sqlSession.insert("BoardMapper.insert", board);
    System.out.printf("%d 개의 데이터를 입력 했음!\n", count);

    // commit() 을 호출하지 않으면 테이블에 변경 사항을 반영하지 않는다.

    sqlSession.close();

    System.out.println("실행 완료!");
  }

}


