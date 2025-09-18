// 세션(session)의 타임아웃 설정
package com.eomcs.web.ex11;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ex11/s21")
@SuppressWarnings("serial")
public class Servlet21 extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession();

    // 세션의 유효 시간(초)을 설정한다.
    // => 클라이언트가 요청하는 순간부터 세션 시간을 카운트 한다.
    // => 만약 10초 이내에 클라이언트의 요청이 없으면
    //    10초를 초과하는 순간 HttpSession 객체를 무효화시킨다.
    //    (어떤 서블릿을 요청하는 상관없다)
    // => 10초 이내에 다시 클라이언트 요청이 있다면
    //    카운트를 0부터 다시 시작한다.
    // => 예) 인터넷 뱅킹 에서 보통 세션 타임아웃을 10분으로 설정한다.
    //        클라이언트가 어떤 링크를 누르면 카운트는 다시 10분으로 초기화된다.
    session.setMaxInactiveInterval(10);

    session.setAttribute("v1", "aaa");

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("/ex11/s21 실행함!");
  }
}


