// 세션(session)의 타임아웃 설정 테스트
package com.eomcs.web.ex11;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ex11/s22")
@SuppressWarnings("serial")
public class Servlet22 extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // setMaxInactiveInterval() 메서드로 지정된 시간 동안
    // 서버에 아무런 요청을 하지 않으면 해당 세션은 무효화 된다.
    // 무효화된 세션은 사용할 수 없기 때문에
    // getSession()은 새 세션을 만들어 리턴한다.
    //
    HttpSession session = request.getSession();

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.printf("v1=%s\n", session.getAttribute("v1"));
  }
}


