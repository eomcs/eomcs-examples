// 클라이언트가 보낸 데이터 읽기 - 여러 개의 데이터를 같은 이름으로 보낸 경우
package com.eomcs.web.ex04;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/ex04/s6")
public class Servlet06 extends GenericServlet {

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

    // 1) 서로 다른 이름으로 값을 보낼 경우
    // => 예) genre1=on&genre2=on&genre4=on
    // => 다음과 같이 각각의 이름에 대해 값을 꺼내 확인해야 한다.
    String genre1 = req.getParameter("genre1");
    String genre2 = req.getParameter("genre2");
    String genre3 = req.getParameter("genre3");
    String genre4 = req.getParameter("genre4");
    String genre5 = req.getParameter("genre5");
    String genre6 = req.getParameter("genre6");

    res.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = res.getWriter();
    out.println("선택한 장르:");
    if (genre1 != null) {
      out.println("로맨틱");
    }
    if (genre2 != null) {
      out.println("스릴러");
    }
    if (genre3 != null) {
      out.println("호러");
    }
    if (genre4 != null) {
      out.println("드라마");
    }
    if (genre5 != null) {
      out.println("액션");
    }
    if (genre6 != null) {
      out.println("SF");
    }
  }
}


