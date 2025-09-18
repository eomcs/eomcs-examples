// Spring과 Mybatis 연동 : Java Config로 설정하기
package com.eomcs.spring.ioc.ex12.e;

import java.util.HashMap;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.eomcs.spring.ioc.SpringUtils;
import com.eomcs.spring.ioc.ex12.Board;

public class Exam01 {

  public static void main(String[] args) {
    ApplicationContext iocContainer =
        new AnnotationConfigApplicationContext(AppConfig.class);
    //new AnnotationConfigApplicationContext("com.eomcs.spring.ioc.ex12.e");

    SpringUtils.printBeanList(iocContainer);

    BoardDao boardDao = iocContainer.getBean(BoardDao.class);

    // 1) 게시물 입력
    Board board = new Board();
    board.setTitle("제목입니다.");
    board.setContent("내용입니다.");
    boardDao.insert(board);

    // 2) 게시물 목록 조회
    // => selectList()의 파라미터 값을 한 개만 넘겨야 하기 때문에
    // 여러 개의 값을 넣고 싶으면 Map에 담아 넘긴다.
    HashMap<String, Object> params = new HashMap<>();
    params.put("startIndex", 0);
    params.put("pageSize", 5);

    List<Board> list = boardDao.selectList(params);
    for (Board b : list) {
      System.out.printf("%d, %s, %s\n", b.getNo(), b.getTitle(), b.getRegisteredDate());
    }
  }

}


