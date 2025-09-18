// SQL 문에 삽입할 파라미터 전달하기 - XML Entity를 사용할 때
package com.eomcs.mybatis.ex03.a;

import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0130 {

  public static void main(String[] args) throws Exception {
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex03/a/mybatis-config.xml")).openSession();

    // XML 파서가 혼동을 일으킬 수 있는(XML 파싱 오류 발생) 문자를 사용할 때, 
    // 그 문자 대신 XML Entity를 사용하라.
    //   " => &quot;
    //   ' => &apos;
    //   & => &amp;
    //   < => &lt;
    //   > => &gt;
    // 
    // 예) 특정 번호 미만의 게시글을 가져온다.
    List<Board> boards = sqlSession.selectList("BoardMapper.select3", 3);

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


