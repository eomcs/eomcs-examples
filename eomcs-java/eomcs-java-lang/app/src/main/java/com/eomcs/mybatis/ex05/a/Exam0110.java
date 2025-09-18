// 조인 데이터 가져오기 - 각 테이블의 데이터를 별도로 가져오기
package com.eomcs.mybatis.ex05.a;

import java.util.List;
import java.util.Scanner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.AttachFile;
import com.eomcs.mybatis.vo.Board;

public class Exam0110 {

  public static void main(String[] args) throws Exception {
    Scanner keyboard = new Scanner(System.in);
    System.out.print("게시글 번호? ");
    int no = Integer.parseInt(keyboard.nextLine());
    keyboard.close();

    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex05/a/mybatis-config.xml")).openSession();

    Board b = sqlSession.selectOne("BoardMapper.selectBoard", no);

    System.out.println("[게시글 상세보기]");
    System.out.printf("번호: %d\n", b.getNo());
    System.out.printf("제목: %s\n", b.getTitle());
    System.out.printf("내용: %s\n", b.getContent());
    System.out.printf("등록일: %s\n", b.getRegisteredDate());
    System.out.printf("조회수: %d\n", b.getViewCount());

    System.out.println("첨부파일:");
    List<AttachFile> files = sqlSession.selectList("BoardMapper.selectFiles", b.getNo());

    for (AttachFile f : files) {
      System.out.printf("  %d - %s\n", f.getFileNo(), f.getFilePath());
    }

    sqlSession.close();
  }

}

