// 게시판 관리 - JDBC 코드를 별도의 클래스로 캡슐화시킴. DAO 적용.
package com.eomcs.jdbc.ex5;

import java.util.Scanner;

public class Exam0110 {

  public static void main(String[] args) throws Exception {

    Board board = new Board();

    try (Scanner keyScan = new Scanner(System.in)) {

      // 사용자로부터 제목, 내용을 입력 받는다.
      System.out.print("제목? ");
      board.setTitle(keyScan.nextLine());

      System.out.print("내용? ");
      board.setContent(keyScan.nextLine());

      System.out.print("입력하시겠습니까?(Y/n) ");
      String input = keyScan.nextLine();

      if (!input.equalsIgnoreCase("y") && input.length() != 0) {
        System.out.println("등록을 취소 하였습니다.");
        return;
      }
    }

    try {
      DataSource dataSource = new DefaultDataSource();
      BoardDao boardDao = new BoardDao(dataSource);
      int count = boardDao.insert(board);
      System.out.printf("%d 개 입력 성공!", count);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


