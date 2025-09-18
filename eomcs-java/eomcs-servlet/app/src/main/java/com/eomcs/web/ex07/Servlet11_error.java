// 인클루딩(forwarding) - 다른 서블릿의 실행을 포함시키기
package com.eomcs.web.ex07;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ex07/s11_error")
@SuppressWarnings("serial")
public class Servlet11_error extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 이미 이전 서블릿에서 setContentType()을 호출했기 때문에
    // 이 서블릿에서는 할 필요가 없다.
    // => forwarding으로 서블릿을 실행할 때는 여기에서 따로 setContentType()을 호출해야 한다.
    PrintWriter out = response.getWriter();
    out.println("해당 연산자를 지원하지 않습니다.");

    // 이 메서드의 호출이 완료되면 이전 서블릿으로 되돌아 간다.
  }
}

