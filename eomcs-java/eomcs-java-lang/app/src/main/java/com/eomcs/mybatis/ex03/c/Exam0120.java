// insert 문 실행 - Map 객체에 값을 담아 파라미터로 넘기기
package com.eomcs.mybatis.ex03.c;

import java.util.HashMap;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Exam0120 {

  public static void main(String[] args) throws Exception {
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex03/c/mybatis-config.xml")).openSession();

    HashMap<String,Object> params = new HashMap<>();
    params.put("title", "제목입니다222"); // #{title} 을 사용하여 SQL 문에 값을 삽입한다.
    params.put("content", "내용입니다222."); // #{content} 를 사용하여 SQL 문에 값을 삽입한다.

    // 예) 게시글을 입력한다.
    //
    int count = sqlSession.insert("BoardMapper.insert2", params);
    System.out.printf("%d 개의 데이터를 입력 했음!\n", count);

    sqlSession.commit();

    sqlSession.close();

    System.out.println("실행 완료!");
  }

}


