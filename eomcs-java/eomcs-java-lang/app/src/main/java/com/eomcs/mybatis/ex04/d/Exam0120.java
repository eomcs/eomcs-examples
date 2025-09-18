// Dynamic SQL - set 태그의 필요성
package com.eomcs.mybatis.ex04.d;

import java.util.Scanner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0120 {

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

    // 다음과 같이 set 문장 앞뒤에 쓸데없이 콤마(,)가 붙을 경우 자동으로 제거하는 방법?
    //        update x_board set                 
    //               title=?,    <=== 문장 뒤에 콤마(,)가 붙어 있다.                     
    //        where board_id=?
    // 
    // - <set> 태그를 사용하라!
    // 
    int count = sqlSession.update("BoardMapper.update2", board);

    System.out.println(count);

    sqlSession.close();
  }

}


