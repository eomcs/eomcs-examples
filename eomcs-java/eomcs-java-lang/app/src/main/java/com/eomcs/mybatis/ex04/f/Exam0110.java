// insert 문에 foreach를 사용할 필요성
package com.eomcs.mybatis.ex04.f;

import java.util.ArrayList;
import java.util.Scanner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.AttachFile;

public class Exam0110 {

  public static void main(String[] args) throws Exception {

    Scanner keyboard = new Scanner(System.in);

    System.out.print("게시글 번호? ");
    int boardNo = Integer.parseInt(keyboard.nextLine());

    ArrayList<AttachFile> files = new ArrayList<>();

    while (true) {
      System.out.print("첨부파일?(빈 문자열: 입력 종료) ");
      String input = keyboard.nextLine();
      if (input.length() == 0) {
        break;
      }
      files.add(new AttachFile(boardNo, input));
    }

    keyboard.close();

    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex04/f/mybatis-config.xml")).openSession(true);

    // 게시글의 첨부파일을 저장하기
    // - List 에 보관된 개수만큼 반복해서 insert를 실행한다.
    // - SQL을 실행한다는 것은 DBMS에 SQL 실행을 요청한다는 의미다.
    // - 여러 번 실행한다는 것은 DBMS에 여러 번 요청한다는 의미다.
    // - 즉 네트워크 사용이 증가한다.
    // 
    // 여러 개의 데이터 입력을 한 번에 처리하는 방법은 없을까?
    // - insert 를 한 번만 요청하면 된다.
    // 
    int count = 0;
    for (AttachFile file : files) {
      count += sqlSession.insert("BoardMapper.insert1", file);
    }
    System.out.printf("%d 개의 데이터를 입력 했음!\n", count);

    sqlSession.close();
  }
}


