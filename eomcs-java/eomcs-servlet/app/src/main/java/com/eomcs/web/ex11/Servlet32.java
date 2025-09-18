// 세션(session) 무효화시키기 - 값 꺼내기
package com.eomcs.web.ex11;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ex11/s32")
@SuppressWarnings("serial")
public class Servlet32 extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 클라이언트가 세션 아이디 쿠키를 서버에 보냈다 하더라도
    // 해당 세션이 무효화 되었다면
    // getSession()은 새 세션 객체를 만들어 리턴한다.
    //
    HttpSession session = request.getSession();

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.printf("v1=%s\n", session.getAttribute("v1"));
  }
}


