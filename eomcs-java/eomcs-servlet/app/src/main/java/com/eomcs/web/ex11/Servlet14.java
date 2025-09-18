// 세션(session)의 활용 - 페이지4
package com.eomcs.web.ex11;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ex11/s14")
@SuppressWarnings("serial")
public class Servlet14 extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 세션을 준비한다.
    HttpSession session = request.getSession();

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>페이지4</title></head><body>");

    // 이전 서블릿에서 세션에 보관했던 값을 꺼내기
    // => 이것이 세션 보관소를 사용하는 이유이다.
    // => 여러 서블릿이 수행한 작업을 공유할 수 있다.
    // => 단 같은 클라이언트일 경우에만 사용할 수 있다.
    //
    out.printf("이름: %s<br>\n", session.getAttribute("name"));
    out.printf("나이: %s<br>\n", session.getAttribute("age"));

    out.printf("전화: %s<br>\n", request.getParameter("tel"));
    out.println("</body></html>");
  }
}
