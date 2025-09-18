// Mybatis 설정 파일 - 도메인 클래스의 별명을 설정하는 또 다른 방법
package com.eomcs.mybatis.ex01.e;

import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Exam0110 {

  public static void main(String[] args) throws Exception {
    // 패키지에 소속된 전체 클래스에 대해 별명을 자동으로 부여할 수 있다.
    // => 단 별명은 클래스 이름이다.
    //
    // <typeAliases>
    //   <package name="com.eomcs.mybatis.ex01.e"/>
    // </typeAliases>
    //
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex01/e/mybatis-config.xml")).openSession();

    List<Board> boards = sqlSession.selectList("BoardMapper.selectBoard");

    System.out.println(boards.size());

    sqlSession.close();
  }

}


