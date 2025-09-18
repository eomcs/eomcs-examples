// SqlSession.selectList() 사용법 - 컬럼 별명을 이용하여 자바 객체의 프로퍼티 이름과 일치시키기
package com.eomcs.mybatis.ex02.c;

import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0110 {

  public static void main(String[] args) throws Exception {
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex02/c/mybatis-config.xml")).openSession();

    List<Board> boards = sqlSession.selectList("BoardMapper.selectBoard");

    // select 문에서 컬럼을 지정할 때 자바 인스턴스의 프로퍼티 이름과 다르다면 
    // 별명을 이용하여 프로퍼티명과 같게 한다.
    // 컬러몀과 자바 객체의 프로퍼티명이 일치한다면 다음과 같이 정상적으로 데이터를 꺼내올 수 있다.
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

//컬럼 이름과 자바 객체의 프로퍼티 이름을 같게 하기
//---------------------------------------------------------------------
//<select id="selectBoard" resultType="com.eomcs.mybatis.ex01.Board">
//  select
//    board_id as no,
//    title,
//    contents as content,
//    created_date as registeredDate,
//    view_count as viewCount
//  from x_board
//</select>
//---------------------------------------------------------------------
//=> 컬럼의 값을 자바 객체에 담으려면 컬럼과 같은 이름의 프로퍼티가 있어야 한다.
//=> 없다면 위와 같이 프로퍼티 명을 컬럼의 별명으로 지정하라.
//

