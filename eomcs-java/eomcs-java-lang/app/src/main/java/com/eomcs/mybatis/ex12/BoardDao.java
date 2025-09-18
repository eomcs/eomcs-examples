package com.eomcs.mybatis.ex12;

import java.util.List;

public interface BoardDao {
  List<Board> selectList();
  int insert(Board board);
}









