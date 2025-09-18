// SqlSession 사용법 - SQL 문을 실행하는 메서드
package com.eomcs.mybatis.ex02.a;

import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0110 {

  public static void main(String[] args) throws Exception {
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex02/a/mybatis-config.xml")).openSession();

    // SqlSession 객체를 이용하여 SQL 맵퍼 파일에 작성한 SQL 문을 실행하는 방법
    // => select 문장 
    //    - sqlSession.selectList() : 목록 리턴
    //    - sqlSession.selectOne() : 한 개의 결과 리턴
    // => insert 문장
    //    - sqlSession.insert() 
    // => update 문장
    //    - sqlSession.update()
    // => delete 문장
    //    - sqlSession.delete()
    // => insert/update/delete인 경우 insert()/update()/delete() 메서드 중
    //    아무거나 호출해도 된다. 
    //    하지만 일관된 유지보수를 위해 메서드를 구분하여 사용하라!
    // 
    // 메서드 사용법)
    // => 예) selectList(SQL문 식별자, 파라미터값)
    //    - SQL문 식별자 = 네임스페이스명 + "." + SQL문장 아이디
    //      - 네임스페이스명: <mapper namespace="네임스페이스명">...</mapper>
    //      - SQL 문장 아이디: <select id="SQL문장 아이디">...</select>
    //    - 파라미터 값: 
    //      - primitive type 및 모든 자바 객체가 가능하다. 
    //      - 여러 개의 값을 전달하고 싶다면 Map인 객체에 담아 넘겨라!
    // 
    List<Board> boards = sqlSession.selectList("BoardMapper.selectBoard");

    System.out.println(boards.size());

    sqlSession.close();
  }

}


