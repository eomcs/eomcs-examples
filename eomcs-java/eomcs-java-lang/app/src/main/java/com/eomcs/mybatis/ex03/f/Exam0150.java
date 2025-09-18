// ${} 문법 : 문자열 치환(string substitution) 문법의 활용 III
package com.eomcs.mybatis.ex03.f;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Exam0150 {

  public static void main(String[] args) throws Exception {
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex03/f/mybatis-config.xml")).openSession();

    // 테이블명을 파라미터로 넘기기
    // => ${} 문법은 파라미터 값을 SQL 문에 그대로 삽입한다.
    //
    int count = sqlSession.selectOne("BoardMapper.count", "x_board");

    System.out.println(count);

    sqlSession.close();
  }

}


