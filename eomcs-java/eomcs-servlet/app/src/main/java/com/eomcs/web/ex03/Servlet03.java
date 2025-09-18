// 클라이언트로 출력하기 - HTML 출력하기
package com.eomcs.web.ex03;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/ex03/s3")
public class Servlet03 extends GenericServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {

    // HTML 출력할 때 MIME 타입에 HTML을 지정하지 않으면
    // 웹 브라우저는 일반 텍스트로 간주하여 출력한다.
    res.setContentType("text/html;charset=UTF-8"); // UTF-16 ==> UTF-8
    PrintWriter out = res.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>servlet03</title></head>");
    out.println("<body><h1>안녕하세요</h1></body>");
    out.println("</html>");
  }
}


