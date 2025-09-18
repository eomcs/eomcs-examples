// ${} 문법의 위험성 - #{} 사용할 때
package com.eomcs.mybatis.ex03.f;

import java.util.Scanner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Exam0210 {

  public static void main(String[] args) throws Exception {
    Scanner keyboard = new Scanner(System.in);
    System.out.print("삭제할 게시글의 제목? ");
    String input = keyboard.nextLine();

    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex03/f/mybatis-config.xml")).openSession(true);

    // #{} 문법은 SQL에 값을 삽입할 때 사용한다.
    // 사용자가 입력한 값(문자열)에 sql 코드를 포함하더라도 
    // 그냥 값으로 취급하기 때문에 실행에는 영향을 끼치지 않는다.
    // 즉 'SQL 삽입 공격'이 불가능하다.
    // 삭제할 게시글의 제목에 SQL에 영향을 끼치는 코드를 삽입하여 실행해 보라!
    // => 어떤 영향도 주지 못할 것이다.
    int count = sqlSession.delete("BoardMapper.delete1", input);

    System.out.println(count);

    sqlSession.close();
    keyboard.close();
  }

}


