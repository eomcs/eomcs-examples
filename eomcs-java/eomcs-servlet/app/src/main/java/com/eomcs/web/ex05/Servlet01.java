// HTTP 프로토콜 다루기 - HttpServletRequest와 GET/POST
package com.eomcs.web.ex05;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ex05/s1")
public class Servlet01 extends GenericServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {

    // 테스트
    // - http://localhost:8080/web/ex05/test01.html 실행
    //

    // HTTP 프로토콜로 통신을 하는 서블릿 컨테이너는
    // service() 메서드를 호출할 때
    // ServletRequest의 파라미터 값으로 HttpServletRequest를 만들어 전달한다.
    // ServletResponse의 파라미터 값으로 HttpServletResponse를 만들어 전달한다.
    // 따라서 service() 메서드의 파라미터 값은 원래
    // HttpServletRequest와 HttpServletResponse이다.
    // 이들 객체에는 HTTP 프로토콜을 다루는 메서드가 추가되어 있다.
    // 따라서 HTTP 프로토콜을 다루고 싶다면 파라미터 값을
    // 원래의 타입으로 변환하라!
    HttpServletRequest httpReq = (HttpServletRequest) req;
    HttpServletResponse httpRes = (HttpServletResponse) res;

    res.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = res.getWriter();

    // HttpServletRequest에는 HTTP 프로토콜의 요청 방식을 리턴하는 메서드가 있다.
    // => HttpServletRequest.getMethod()
    //
    if (httpReq.getMethod().equals("GET")) {
      out.println("GET 요청입니다.");

    } else if (httpReq.getMethod().equals("POST")) {
      out.println("POST 요청입니다.");

    } else {
      out.println("이 서블릿이 다루지 못하는 요청 방식입니다.");
    }

  }
}

