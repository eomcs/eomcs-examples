// 보관소에 값 넣기 - forward/include 서블릿끼리 ServletRequest 공유하는 것 테스트
package com.eomcs.web.ex09;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ex09/s11")
@SuppressWarnings("serial")
public class Servlet11 extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 테스트 방법:
    // => http://localhost:8080/java-web/ex09/s11
    //

    // 1) ServletContext 보관소에 값 넣기
    ServletContext sc = this.getServletContext();
    sc.setAttribute("v1", "xxx");

    // 2) HttpSession 보관소에 값 넣기
    // => 이 요청을 한 클라이언트의 HttpSession 객체가 없다면 만들어준다.
    // => 이미 이 클라이언트를 위해 만든 객체가 있다면 그 객체를 리턴한다.
    HttpSession session = request.getSession();
    session.setAttribute("v2", "yyy");

    // 3) ServletRequest 보관소에 값 넣기
    request.setAttribute("v3", "zzz");

    // 주목!
    // => 포워드(인클루드 포함)할 때 이 서블릿이 파라미터로 받은
    // ServletRequest와 ServletResponse를 전달한다.
    // => 따라서 포워드/인클루드 서블릿들은 응답을 완료할 때까지 이 객체들을 공유하는 것이다.
    //
    request.getRequestDispatcher("s12").forward(request, response);

  }
}

