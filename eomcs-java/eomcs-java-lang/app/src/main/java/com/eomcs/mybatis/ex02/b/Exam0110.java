// SqlSession.selectList() 사용법 - select 문 실행하기
package com.eomcs.mybatis.ex02.b;

import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.vo.Board;

public class Exam0110 {

  public static void main(String[] args) throws Exception {
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex02/b/mybatis-config.xml")).openSession();

    // selectList()
    // => 여러 개의 결과 값을 리턴하는 select를 실행할 때 사용한다.
    // => select 실행으로 생성된 각 row의 값은 resultType에 지정한 클래스의 인스턴스에 저장된다.
    // => 그리고 그 인스턴스는 List 구현체에 담겨 리턴된다.
    // => 결과가 없으면 size 가 0인 List 객체를 리턴한다.
    //

    List<Board> boards = sqlSession.selectList("BoardMapper.selectBoard");

    for (Board b : boards) {
      System.out.println(b);
      //      System.out.printf("%d,%s,%s,%s,%d\n",
      //          b.getNo(),
      //          b.getTitle(),
      //          b.getContent(),
      //          b.getRegisteredDate(),
      //          b.getViewCount());
    }
    // 실행 결과가 기대와 같지 않은 이유?
    // => mybatis에서 결과 값을 Board 객체에 담을 때 일부 컬럼의 값을 담지 못했기 때문이다.
    //
    // 왜 일부 컬럼의 값을 Board에 담지 못했는가?
    // => mybatis에서 결과의 컬럼 값을 자바 객체에 담을 때
    //    컬럼 이름과 같은 이름을 가진 프로퍼티(셋터 메서드)를 찾는다.
    // => 컬럼 이름과 일치하는 프로퍼티가 없다면,
    //    컬럼 이름과 일치하는 필드를 찾는다.
    // => 컬럼 이름과 일치하는 세터나 필드가 없다면 
    //    해당 컬럼의 값이 자바 객체에 저장되지 못한다.
    // => 컬럼과 일치하는 프로퍼티나 필드가 한 개도 없다면,
    //    자바 객체를 만들지 않고 null을 리턴한다.

    sqlSession.close();
  }

}
// Mybatis에서 select 결과를 자바 인스턴스에 담을 때 규칙 
// => 컬럼 이름과 같은 프로퍼티를 찾아서 값을 담는다.
// => 자바에서 '프로퍼티'란?
//    세터/게터를 가리키는 용어다.
//    예) setNo()/getNo()
// => 프로퍼티 이름?
//    세터/게터 이름에서 set/get 이름을 제거한 후, 나머지 이름이다.
//    단 첫 알파벳은 소문자이다.
//    예) setNo()/getNo() => no
//    예) setCreatedDate()/getCreatedDate() => createdDate
// => 주의! 
//    필드 이름이 프로퍼티 이름이 아니다!

// 결론!
// - Board 클래스의 프로퍼티 이름을 보면 
//   no, title, content, registeredDate, viewCount 가 있다.
// - Board 클래스의 필드 이름을 보면
//   no, title, content, registeredDate, viewCount 가 있다.
// - 이 프로퍼티나 필드 중에서 컬럼 이름과 일치하는 프로퍼티나 필드는 title 뿐이다.
// - Mybatis는 컬럼 이름과 같은 이름을 가진 
//   프로퍼티(title,contents) 또는 필드에 대해서만 결과 값을 넣어 준다.
// - 그래서 Board 객체를 출력해보면 title 값만 정상적으로 출력된다.
//
// 해결책?
// => 컬럼 이름을 프로퍼티명이나 필드명과 같게 하라.
//    - DB의 이름짓는 규칙과 자바의 이름 짓는 규칙은 다르다.
//    - 또한 DBMS 마다 이름 짓는 규칙이 다르다.
//    - 그래서 자바에서 프로퍼티 이름을 지을 때는 특정 DBMS에 종속되게 지어서는 안된다.
//    - 자바는 자바의 명명 규칙을 따르고, DB는 DBMS의 명명 규칙을 따르면 된다.
// => 그러면 어떻게 하자는 것인가?
//    - select 할 때 컬럼의 별명을 자바 프로퍼티(또는 필드) 이름과 같게 하라!

