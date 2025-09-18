// 게시판 관리 - JDBC 코드를 별도의 클래스로 캡슐화시킴. DAO 적용.
package com.eomcs.jdbc.ex5;

import java.util.Scanner;

public class Exam0140 {

  public static void main(String[] args) throws Exception {
    Board board = new Board();

    try (Scanner keyScan = new Scanner(System.in)) {
      System.out.print("번호? ");
      board.setNo(Integer.parseInt(keyScan.nextLine()));

      System.out.print("제목? ");
      board.setTitle(keyScan.nextLine());

      System.out.print("내용? ");
      board.setContent(keyScan.nextLine());
    }

    try {
      DataSource dataSource = new DefaultDataSource();
      BoardDao boardDao = new BoardDao(dataSource);
      int count = boardDao.update(board);

      if (count == 0) {
        System.out.println("해당 번호의 게시물이 존재하지 않습니다.");
      } else {
        System.out.println("변경하였습니다.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


