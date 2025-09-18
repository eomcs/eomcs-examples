// 클라이언트가 보낸 데이터 읽기 - 여러 개의 데이터를 같은 이름으로 보낸 경우
package com.eomcs.web.ex04;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/ex04/s6_2")
public class Servlet06_2 extends GenericServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {

    req.setCharacterEncoding("UTF-8");

    // POST 요청
    // - 웹 페이지의 폼(method='POST' 일 때)에서 전송 버튼을 클릭하면 POST 요청을 보낸다.
    //
    // 테스트
    // - http://localhost:8080/java-web/ex04/test06.html 실행
    //

    // 2) 같은 이름으로 값을 보낼 경우
    // => 예) genre=1&genre=2&genre=4
    // => 다음과 같이 한 번에 값을 리턴 받는다.
    // => 만약 해당 이름으로 넘어온 값이 없으면 빈 배열이 아닌 null을 리턴한다.
    String[] genres = req.getParameterValues("genre");
    String[] genreData = { "", "로맨틱", "스릴러", "호러", "드라마", "액션", "SF" };

    res.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = res.getWriter();
    out.println("선택한 장르:");
    if (genres != null) {
      for (String genre : genres) {
        out.println(genreData[Integer.parseInt(genre)]);
      }
    }
    // 같은 값을 여러 개 입력 받아야 하는 경우
    // 같은 이름을 사용하라.
    // 그러면 위와 같이 한 번에 배열로 그 값들을 받을 수 있다.
    // 배열로 받으면 반복문을 이용하여 보다 쉽고 간결하게 처리할 수 있다.
  }
}
