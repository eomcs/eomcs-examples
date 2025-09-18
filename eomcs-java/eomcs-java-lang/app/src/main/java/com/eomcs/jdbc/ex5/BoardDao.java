// 데이터를 처리하는 코드를 별도의 클래스로 캡슐화시킨다.
// => data 영속성(지속성)을 관리하는 클래스를 DAO(Data Access Object)라 부른다.
// => data 영속성(지속성)
// - 데이터를 저장하고 유지하는 것.
// - "데이터 퍼시스턴스(persistence)"라 부른다.
package com.eomcs.jdbc.ex5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {
  DataSource dataSource;

  public BoardDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public int delete(int no) throws Exception {
    try (Connection con = dataSource.getConnection();
        PreparedStatement stmt = con.prepareStatement( //
            "delete from x_board where board_id=?")) {

      stmt.setInt(1, no);
      return stmt.executeUpdate();
    }
  }

  public List<Board> findAll() throws Exception {
    try (Connection con = dataSource.getConnection();
        PreparedStatement stmt = con.prepareStatement( //
            "select * from x_board order by board_id desc");
        ResultSet rs = stmt.executeQuery()) {

      ArrayList<Board> arr = new ArrayList<>();
      while (rs.next()) {
        Board board = new Board();
        board.setNo(rs.getInt("board_id"));
        board.setTitle(rs.getString("title"));
        board.setContent(rs.getString("contents"));
        board.setRegisteredDate(rs.getDate("created_date"));
        board.setViewCount(rs.getInt("view_count"));
        arr.add(board);
      }
      return arr;
    }
  }

  public int insert(Board board) throws Exception {
    try (Connection con = dataSource.getConnection();
        PreparedStatement stmt =
            con.prepareStatement("insert into x_board(title,contents) values(?,?)");) {

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());

      return stmt.executeUpdate();
    }
  }

  public int update(Board board) throws Exception {
    try (Connection con = dataSource.getConnection();
        PreparedStatement stmt = con.prepareStatement( //
            "update x_board set title = ?, contents = ? where board_id = ?")) {

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setInt(3, board.getNo());
      stmt.executeUpdate();

      return stmt.executeUpdate();
    }
  }

  public Board findBy(String no) throws Exception {
    try (Connection con = dataSource.getConnection();
        PreparedStatement stmt = con.prepareStatement( //
            "select * from x_board where board_id = ?")) {

      stmt.setString(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next())
          return null;

        Board board = new Board();
        board.setNo(rs.getInt("board_id"));
        board.setTitle(rs.getString("title"));
        board.setContent(rs.getString("contents"));
        board.setRegisteredDate(rs.getDate("created_date"));
        board.setViewCount(rs.getInt("view_count"));
        return board;
      }
    }
  }
}


