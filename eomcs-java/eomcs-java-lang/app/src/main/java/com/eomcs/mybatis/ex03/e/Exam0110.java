// 트랜잭션 다루기 - 수동 커밋의 필요성
package com.eomcs.mybatis.ex03.e;

import java.util.HashMap;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0110 {

  public static void main(String[] args) throws Exception {

    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex03/e/mybatis-config.xml")).openSession(true);

    // 1) 게시글 입력
    Board board = new Board();
    board.setTitle("게시글1");
    board.setContent("내용1");

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

    // auto commit 일 때,
    // - 데이터 변경 SQL 문을 실행하면 즉시 테이블에 적용한다.
    // - 따라서 세 번째 첨부파일을 입력하다가 오류가 발생하더라도 
    //   이전에 수행했던 데이터 변경 작업은 완료된 상태다.
    // 

    sqlSession.close();

    System.out.println("실행 완료!");
  }

}


