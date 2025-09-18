// SQL 문에 삽입할 파라미터 전달하기 - Map을 이용하여 여러 개의 값 넘기기
package com.eomcs.mybatis.ex03.b;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0110 {

  public static void main(String[] args) throws Exception {
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex03/b/mybatis-config.xml")).openSession();

    // selectList(),selectOne(),insert(),update(),delete()
    // - selectList(SQL ID)
    // - selectList(SQL ID, 파라미터)
    // - 위와 같이 SQL을 실행할 때 오직 한 개의 파라미터만 넘길 수 있다.
    // - 여러 개의 파라미터를 넘기고 싶다면 객체에 담아서 넘겨라!
    // 
    // 예) 특정 범위의 번호에 해당하는 게시글을 가져온다.
    //
    Map<String,Object> params = new HashMap<>();
    params.put("startNo", 2);
    params.put("endNo", 4);

    List<Board> boards = sqlSession.selectList("BoardMapper.select1", params);

    for (Board b : boards) {
      System.out.printf("%d,%s,%s,%s,%d\n",
          b.getNo(),
          b.getTitle(),
          b.getContent(),
          b.getRegisteredDate(),
          b.getViewCount());
    }
    sqlSession.close();
    System.out.println("실행 완료!");
  }

}


