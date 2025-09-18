// 데이터를 처리하는 코드를 별도의 클래스로 캡슐화시킨다.
// => data 영속성(지속성)을 관리하는 클래스를 DAO(Data Access Object)라 부른다.
// => data 영속성(지속성)
//    - 데이터를 저장하고 유지하는 것.
//    - "데이터 퍼시스턴스(persistence)"라 부른다.
package com.eomcs.jdbc.ex2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BoardDao2 {

  Connection con;

  public BoardDao2(String jdbcUrl, String username, String password) throws Exception {
    con = DriverManager.getConnection(jdbcUrl, username, password);
  }

  public int delete(int no) throws Exception {
    try (Statement stmt = con.createStatement()) {

      // 첨부파일 삭제
      stmt.executeUpdate("delete from x_board_file where board_id = " + no);

      // 게시글 삭제
      return stmt.executeUpdate("delete from x_board where board_id=" + no);
    }
  }

  public List<Board> findAll() throws Exception {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from x_board order by board_id desc")) {

      ArrayList<Board> list = new ArrayList<>();
      while (rs.next()) {
        Board board = new Board();
        board.setNo(rs.getInt("board_id"));
        board.setTitle(rs.getString("title"));
        board.setContent(rs.getString("contents"));
        board.setRegisteredDate(rs.getDate("created_date"));
        board.setViewCount(rs.getInt("view_count"));
        list.add(board);
      }
      return list;
    }
  }

  public int insert(Board board) throws Exception {
    try (Statement stmt = con.createStatement();) {

      String sql = String.format(
          "insert into x_board(title,contents) values('%s','%s')", 
          board.getTitle(),
          board.getContent());

      return stmt.executeUpdate(sql);
    }
  }

  public int update(Board board) throws Exception {
    try (Statement stmt = con.createStatement()) {

      String sql = String.format(
          "update x_board set title='%s', contents='%s' where board_id=%d", 
          board.getTitle(),
          board.getContent(),
          board.getNo());

      return stmt.executeUpdate(sql);
    }
  }

  public Board findBy(String no) throws Exception {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from x_board where board_id = " + no)) {

      if (rs.next()) {
        Board board = new Board();
        board.setNo(rs.getInt("board_id"));
        board.setTitle(rs.getString("title"));
        board.setContent(rs.getString("contents"));
        board.setRegisteredDate(rs.getDate("created_date"));
        board.setViewCount(rs.getInt("view_count"));
        return board;

      } else {
        return null;
      }
    }
  }
}


