// ${} 문법 : 문자열 치환(string substitution) 문법의 활용 IV
package com.eomcs.mybatis.ex03.f;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0140 {

  public static void main(String[] args) throws Exception {
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex03/f/mybatis-config.xml")).openSession();

    // 컬럼 이름과 정렬 타입을 파라미터로 넘기기
    //
    HashMap<String,Object> params = new HashMap<>();
    params.put("columnName", "title");
    params.put("sortType", "asc");

    List<Board> boards = sqlSession.selectList("BoardMapper.select4", params);

    for (Board b : boards) {
      System.out.printf("%d,%s,%s,%s,%d\n",
          b.getNo(),
          b.getTitle(),
          b.getContent(),
          b.getRegisteredDate(),
          b.getViewCount());
    }

    sqlSession.close();
  }

}


