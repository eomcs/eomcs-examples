// 트랜잭션 다루기 - 수동 커밋의 활용
package com.eomcs.mybatis.ex03.e;

import java.util.HashMap;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0120 {

  public static void main(String[] args) throws Exception {

    // 수동 커밋 상태로 동작하는 SqlSession 객체를 얻는다.
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex03/e/mybatis-config.xml")).openSession();

    // 1) 게시글 입력
    Board board = new Board();
    board.setTitle("게시글2");
    board.setContent("내용2");

    int count = sqlSession.insert("BoardMapper.insertBoard", board);
    System.out.printf("%d 개의 게시글을 입력 했음!\n", count);

    HashMap<String,Object> fileInfo = new HashMap<>();

    // 2) 첫 번째 첨부파일 입력
    fileInfo.put("filePath", "aaa.gif");
    fileInfo.put("boardNo", board.getNo());
    count = sqlSession.insert("BoardMapper.insertFile", fileInfo);
    System.out.printf("%d 개의 첨부파일을 입력 했음!\n", count);

    // 3) 두 번째 첨부파일 입력
    fileInfo.put("filePath", "bbb.gif");
    fileInfo.put("boardNo", board.getNo());
    count = sqlSession.insert("BoardMapper.insertFile", fileInfo);
    System.out.printf("%d 개의 첨부파일을 입력 했음!\n", count);

    // 4) 세 번째 첨부파일 입력
    fileInfo.put("filePath", 
        "12345678901234567890123456789012345678901234567890"
            + "12345678901234567890123456789012345678901234567890"
            + "12345678901234567890123456789012345678901234567890"
            + "12345678901234567890123456789012345678901234567890"
            + "12345678901234567890123456789012345678901234567890"
            + "12345678901234567890123456789012345678901234567890"
            + ".gif");
    fileInfo.put("boardNo", board.getNo());
    count = sqlSession.insert("BoardMapper.insertFile", fileInfo);
    System.out.printf("%d 개의 첨부파일을 입력 했음!\n", count);

    // 수동 commit 일 때,
    // - 작업을 완료한 후 반드시 commit()을 호출해야만 실제 테이블에 변경사항을 적용한다.
    // - commit() 을 호출하지 않고 SqlSession 객체를 close() 하면 자동 rollback 이다.
    // - SqlSession 객체를 재사용하는 방식이면,
    //   오류가 발생했을 때 개발자가 명시적으로 rollback()을 호출해 줘야 한다.
    // 
    sqlSession.commit();

    sqlSession.close();

    System.out.println("실행 완료!");
  }

}


