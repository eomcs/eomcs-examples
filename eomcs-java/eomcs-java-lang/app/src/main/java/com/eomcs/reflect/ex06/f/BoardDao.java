package com.eomcs.reflect.ex06.f;

import java.util.List;
import java.util.Map;

public interface BoardDao {
  List<Board> selectList(Map<String, Object> map);

  int insert(Board board);

  int delete(int no);
}


