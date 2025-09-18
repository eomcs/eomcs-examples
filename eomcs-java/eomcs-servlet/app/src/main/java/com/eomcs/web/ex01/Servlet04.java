// 서블릿 만들기 - doGet(), doPost() 등
package com.eomcs.web.ex01;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ex01/s4")
public class Servlet04 extends HttpServlet {

  private static final long serialVersionUID = 1L;

  // HTTP 요청 프로토콜에서 특정 명령에 대해 처리하고 싶다면,
  // service(HttpServletRequest,HttpServletResponse)를 오버라이딩 하는 대신에
  // doGet(), doPost(), doHead() 등을 오버라이딩 하라.
  // 호출과정:
  // => 웹브라우저
  //   => 톰캣 서버
  //     => Servlet04.service(ServletRequest, ServletResponse) 호출
  //       => Serlvet04.service(HttpServletRequest, HttpServletResponse) 호출
  //         => HTTP 프로토콜의 명령을 분석하여 그 명령을 처리할 메서드를 호출한다.
  //           => GET 명령: Servlet04.doGet(HttpServletRequest, HttpServletResponse) 호출
  //           => POST 명령: Servlet04.doPost(HttpServletRequest, HttpServletResponse) 호출
  //           => HEAD 명령: Servlet04.doHead(HttpServletRequest, HttpServletResponse) 호출
  //           등 
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    System.out.println("Servlet04.doGet(ServletRequest,ServletResponse)");
  }
}







