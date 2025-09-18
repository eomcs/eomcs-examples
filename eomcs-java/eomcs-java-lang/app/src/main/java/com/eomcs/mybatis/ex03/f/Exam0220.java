// ${} 문법의 위험성 - ${} 사용할 때
package com.eomcs.mybatis.ex03.f;

import java.util.Scanner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Exam0220 {

  public static void main(String[] args) throws Exception {
    Scanner keyboard = new Scanner(System.in);

    System.out.print("검색 항목:\n  1. 제목\n  2. 내용\n  3. 번호\n검색할 항목은? ");
    int menuNo = Integer.parseInt(keyboard.nextLine());

    String condition = null;
    switch (menuNo) {
      case 1:
        System.out.print("삭제할 게시글의 제목? ");
        condition = "title='" + keyboard.nextLine() + "'";
        break;
      case 2:
        System.out.print("삭제할 게시글의 내용? ");
        condition = "contents='" + keyboard.nextLine() + "'";
        break;
      case 3:
        System.out.print("삭제할 게시글의 번호? ");
        condition = "board_id=" + keyboard.nextLine();
        break;
      default:
        System.out.println("메뉴 번호가 옳지 않습니다.");
        keyboard.close();
        return;
    }

    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex03/f/mybatis-config.xml")).openSession(true);

    // ${} 문법은 SQL에 코드를 사입할 때 사용한다.
    // 잘 쓰면 매우 편리하다.
    // => SQL 문을 한 개만 만들어 놓고 
    //    조건 변경하면서 다양한 방식으로 사용할 수 있다.
    // => 즉 게시글 제목, 내용, 번호를 조건으로 삭제할 수 있는 SQL 문을 쉽게 만들 수 있다.
    // 
    // 문제는 잘못된 방식으로 사용하면 위험하다.
    // => 사용자의 입력 값을 정제하지 않고 그대로 SQL문에 삽입하면 
    //    해킹이 가능한 상태가 될 수 있어 매우 위험하다.
    //
    // 제목을 입력할 때 다음과 같이 입력해 보라!
    // => a' or 't'='t
    // => 사용자가 위와 같이 입력했다고 가정하면 
    //    실행할 SQL 문은 다음과 같다.
    //       delete from x_board
    //       where title='a' or 't'='t'
    //    즉 x_board 테이블의 모든 데이터를 삭제하는 SQL 문이 만들어진다.
    //
    int count = sqlSession.delete("BoardMapper.delete2", condition);

    System.out.println(count);

    sqlSession.close();
    keyboard.close();
  }

}

// 결론!
// - ${} 문법을 사용하지 말라는 것이 아니다.
// - ${} 문법을 사용했을 때 여러 개의 SQL 문을 만들 필요가 없어 매우 편리해지는 경우가 있다.
//   즉 약간의 조건에 따라 SQL 문이 바뀌는 상황에서 ${} 문법은 매우 유용하다.
// - 다만 SQL 삽입 공격이 불가능하도록 주의해서 사용해야 한다.
//   즉 사용자가 입력한 값이 SQL에 영향을 끼치지 않도록 해야 한다.









