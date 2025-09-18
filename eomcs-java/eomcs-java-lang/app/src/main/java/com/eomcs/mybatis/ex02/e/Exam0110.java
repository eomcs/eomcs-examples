// SqlSession.selectList() 사용법 - 결과 레코드를 Map 객체로 받기
package com.eomcs.mybatis.ex02.e;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Exam0110 {

  public static void main(String[] args) throws Exception {
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex02/e/mybatis-config.xml")).openSession();

    // 결과 타입이 Map 이면,
    // - selectList()의 리턴 타입은 List<Map> 이 된다.
    // 
    List<Map<String,Object>> list = sqlSession.selectList("BoardMapper.selectBoard");

    // 각각의 Map 객체에는 레코드 값이 보관되어 있다.
    // - 즉 레코드의 각 컬럼 값이 Map 객체에 보관되어 있다.
    // - 컬럼 값을 꺼낼 때는 select 할 때 사용한 컬럼이름으로 꺼내야 한다.
    // 
    for (Map<String,Object> map : list) {
      System.out.printf("%d,%s,%s,%s,%d\n",
          map.get("board_id"),
          map.get("title"),
          map.get("contents"),
          map.get("created_date"),
          map.get("view_count"));
    }

    sqlSession.close();
  }

}


