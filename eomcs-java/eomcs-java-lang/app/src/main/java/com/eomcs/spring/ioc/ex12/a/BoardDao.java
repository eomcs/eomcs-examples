package com.eomcs.spring.ioc.ex12.a;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;
import com.eomcs.spring.ioc.ex12.Board;

@Repository
public class BoardDao {

  SqlSessionFactory sqlSessionFactory;

  public BoardDao(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public List<Board> selectList(int pageNo, int pageSize) {
    HashMap<String,Object> params = new HashMap<>();
    params.put("startIndex", (pageNo - 1) * pageSize);
    params.put("pageSize", pageSize);

    try (SqlSession sqlSession = this.sqlSessionFactory.openSession()) {
      return sqlSession.selectList("BoardMapper.selectList", params);
    }
  }

  public int insert(Board board) {
    try (SqlSession sqlSession = this.sqlSessionFactory.openSession()) {
      int count = sqlSession.insert("BoardMapper.insert", board);
      sqlSession.commit();
      return count;
    }
  }

  public int delete(int no) {
    try (SqlSession sqlSession = this.sqlSessionFactory.openSession()) {
      int count = sqlSession.delete("BoardMapper.delete", no);
      sqlSession.commit();
      return count;
    }
  }

}





