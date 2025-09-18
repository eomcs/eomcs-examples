// 게시판 관리 - JDBC 코드를 별도의 클래스로 캡슐화시킴. DAO 적용.
package com.eomcs.jdbc.ex5;

import java.util.List;

public class Exam0120 {

  public static void main(String[] args) throws Exception {
    try {
      DataSource dataSource = new DefaultDataSource();
      BoardDao boardDao = new BoardDao(dataSource);
      List<Board> list = boardDao.findAll();
      System.out.println("번호, 제목, 등록일, 조회수");
      for (Board b : list) {
        System.out.printf("%d, %s, %s, %s, %d\n", //
            b.getNo(), //
            b.getTitle(), //
            b.getContent(), //
            b.getRegisteredDate(), //
            b.getViewCount());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


