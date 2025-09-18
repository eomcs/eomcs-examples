// 트랜잭션 다루기 - commit() : 데이터 변경 결과를 실제 테이블에 반영하기
package com.eomcs.mybatis.ex03.d;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0130 {

  public static void main(String[] args) throws Exception {

    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex03/d/mybatis-config.xml")).openSession();

    Board board = new Board();
    board.setTitle("제목입니다zzz");
    board.setContent("내용입니다zzz");

    int count = sqlSession.insert("BoardMapper.insert", board);
    System.out.printf("%d 개의 데이터를 입력 했음!\n", count);

    // 수동 커밋 상태일 때는 
    // - 데이터 변경 후 commit() 을 명시적으로 호출해야 한다.
    // - 그래야 실제 테이블에 변경 사항이 반영된다.
    sqlSession.commit();

    sqlSession.close();

    System.out.println("실행 완료!");
  }

}


