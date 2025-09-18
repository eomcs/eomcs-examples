// 포워딩(forwarding) - 서블릿 실행을 위임하기
package com.eomcs.web.ex07;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ex07/s3")
@SuppressWarnings("serial")
public class Servlet03 extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("해당 연산을 수행할 수 없습니다.");

    System.out.println("ex07/Servlet03!!!");
  }
}

