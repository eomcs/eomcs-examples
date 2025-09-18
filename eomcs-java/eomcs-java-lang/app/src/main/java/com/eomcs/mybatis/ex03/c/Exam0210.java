// update 문 실행
package com.eomcs.mybatis.ex03.c;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0210 {

  public static void main(String[] args) throws Exception {
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex03/c/mybatis-config.xml")).openSession();

    Board board = new Board();
    board.setNo(1); // #{no} 
    board.setTitle("제목 변경!!!"); // #{title}
    board.setContent("내용 변경!!!"); // #{content}

    // update 문을 실행할 때는 update() 메서드를 호출한다.
    // - 리턴 값은 executeUpdate()의 실행 결과이다.
    // - 즉 update 된 데이터의 개수이다.
    //
    // 예) 게시글을 변경한다.
    //
    int count = sqlSession.update("BoardMapper.update", board);
    System.out.printf("%d 개의 데이터를 변경 했음!\n", count);

    sqlSession.commit();

    sqlSession.close();

    System.out.println("실행 완료!");
  }

}


