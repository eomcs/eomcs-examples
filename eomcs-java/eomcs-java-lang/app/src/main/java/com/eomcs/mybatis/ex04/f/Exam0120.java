// insert 문에 foreach를 사용하기
package com.eomcs.mybatis.ex04.f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.AttachFile;

public class Exam0120 {

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

    // insert 문을 만들 때 사용할 값을 Map 에 담는다.
    HashMap<String,Object> params = new HashMap<>();
    params.put("boardNo", boardNo);
    params.put("files", files);

    // 한 번에 여러 개의 값을 insert 하는 방법?
    // - 다음과 같이 여러 개의 값을 한 번에 저장하는 insert 문을 생성한다.
    //     insert into x_board_file(file_path, board_id)
    //     values (파일명, 게시글번호),(파일명, 게시글번호),(파일명, 게시글번호),...
    // - 위와 같이 만든 insert 문장 한 개를 실행한다.
    //   즉 이것은 DBMS에 한 번만 요청한다는 의미다.
    // 
    int count = sqlSession.insert("BoardMapper.insert2", params);
    System.out.printf("%d 개의 데이터를 입력 했음!\n", count);

    sqlSession.close();
  }
}


