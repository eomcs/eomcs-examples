// 세션(session)의 활용 - 페이지3
package com.eomcs.web.ex11;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ex11/s13")
@SuppressWarnings("serial")
public class Servlet13 extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 세션을 준비한다.
    HttpSession session = request.getSession();

    // 클라이언트가 보낸 데이터를 세션에 보관한다.
    session.setAttribute("age", request.getParameter("age"));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>페이지3</title></head><body>");
    out.println("<form action='s14' method='post'>");
    out.println("전화: <input type='text' name='tel'><br>");
    out.println("<button>다음</button>");
    out.println("</form>");
    out.println("</body></html>");
  }
}


