// Dynamic SQL - update 문에 if 태그의 활용 
package com.eomcs.mybatis.ex04.d;

import java.util.Scanner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0110 {

  public static void main(String[] args) throws Exception {
    Scanner keyboard = new Scanner(System.in);

    Board board = new Board();

    // 게시글 번호 입력 받기
    System.out.print("변경할 게시글의 번호? ");
    board.setNo(Integer.parseInt(keyboard.nextLine()));

    System.out.print("제목?(건너 뛰기: 빈 문자열) ");
    String input = keyboard.nextLine();
    if (input.length() > 0) {
      board.setTitle(input);
    }

    System.out.print("내용?(건너 뛰기: 빈 문자열) ");
    input = keyboard.nextLine();
    if (input.length() > 0) {
      board.setContent(input);
    }

    keyboard.close();

    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex04/d/mybatis-config.xml")).openSession(true);

    // if 태그를 활용하면 전체 컬럼이 아니라 일부 항목만 변경하는 SQL을 만들 수 있다.
    // 테스트 1)
    //   - 제목, 내용 모두 변경하기
    // 텍스트 2)
    //   - 내용만 변경하기
    // 테스트 3)
    //   - 제목만 변경하기
    //   - set 다음에 오는 문장 뒤에 콤마(,)가 붙기 때문에 SQL 문법 오류가 발생한다.
    //        update x_board set                 
    //               title=?,    <=== 문장 뒤에 콤마(,)가 붙어 있다.                     
    //        where board_id=?
    //   - 해결책?
    //     <set> 태그를 사용하라!
    // 
    int count = sqlSession.update("BoardMapper.update1", board);

    System.out.println(count);

    sqlSession.close();
  }

}


