// insert 문 실행 - Board 객체에 값을 담아 파라미터로 넘기기
package com.eomcs.mybatis.ex03.c;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0110 {

  public static void main(String[] args) throws Exception {
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex03/c/mybatis-config.xml")).openSession();

    Board board = new Board();
    board.setTitle("제목입니다111"); // #{title} 을 사용하여 SQL 문에 값을 삽입한다.
    board.setContent("내용입니다111."); // #{content} 를 사용하여 SQL 문에 값을 삽입한다.

    // insert 문을 실행할 때는 insert() 메서드를 호출한다.
    // - 리턴 값은 executeUpdate()의 실행 결과이다.
    // - 즉 insert 된 데이터의 개수이다.
    // - update(), delete()을 사용해도 결과는 같다.
    //
    // 예) 게시글을 입력한다.
    //
    int count = sqlSession.insert("BoardMapper.insert1", board);
    System.out.printf("%d 개의 데이터를 입력 했음!\n", count);

    // SqlSessionFactory의 openSession()을 통해 SqlSession 얻을 때는 
    // autocommit이 기본으로 false이다.
    // autocommit?
    // - insert/update/delete 과 같이 데이터를 변경하는 작업은
    //   위험하기 때문에 DBMS의 임시 메모리에 그 작업 결과를 보관한다.
    // - 클라이언트에서 최종적으로 변경 사항에 대해 확정해줘야만 진짜 테이블에 값을 반영한다.
    //
    // 현재 SqlSession 객체가 수동 commit 상태이기 때문에
    // 데이터 변경을 수행한 후 commit을 반드시 실행해야 한다.
    // 
    sqlSession.commit();
    // commit 명령을 내리지 않으면 insert/update/delete을 통해 수행한 데이터 변경 작업을 
    // 실제 테이블에 반영하지 않는다.
    // close() 할 때 자동으로 취소된다.
    //
    // 용어 정리!
    // commit() : 임시 메모리에 저장된 작업 결과를 실제 테이블에 반영시키는 명령
    // rollback() : 임시 메모리에 저장된 작업 결과를 취소하는 명령
    //
    sqlSession.close();

    System.out.println("실행 완료!");
  }

}


