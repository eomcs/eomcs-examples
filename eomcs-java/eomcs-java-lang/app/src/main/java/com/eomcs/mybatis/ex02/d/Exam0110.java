// SqlSession.selectList() 사용법 - <resultMap>을 통해 자바 객체의 프로퍼티 이름과 일치시키기
package com.eomcs.mybatis.ex02.d;

import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0110 {

  public static void main(String[] args) throws Exception {
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex02/d/mybatis-config.xml")).openSession();

    List<Board> boards = sqlSession.selectList("BoardMapper.selectBoard");

    // select 문에서 컬럼 값을 자바 인스턴스의 프로퍼티와 맞추기 위해 
    // 별명을 부여하는 방식은 번거롭다.
    // 특히 컬럼 개수가 많은 경우 더더욱 번거롭다.
    // 이를 해결하기 위해 mybatis는 컬럼의 이름과 프로퍼티 이름을 연결해주는 문법을 제공한다.
    //    <resultMap></resultMap>
    // 
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

