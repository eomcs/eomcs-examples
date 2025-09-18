// HTTP 프로토콜 다루기 - MyHttpServlet2 클래스를 활용하기
package com.eomcs.web.ex05;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ex05/s3")
public class Servlet03 extends MyHttpServlet2 {

  private static final long serialVersionUID = 1L;


  // HTTP GET 요청에 응답하고 싶다면
  // 상속 받은 메서드 중에서 doGet()을 오버라이딩 한다.
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 테스트
    // - http://localhost:8080/web/ex05/test03.html 실행
    //
    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("doGet() => GET 요청입니다.");
  }

  // HTTP POST 요청에 응답하고 싶다면
  // 상속 받은 메서드 중에서 doPost()을 오버라이딩 한다.
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 테스트
    // - http://localhost:8080/java-web/ex05/test03.html 실행
    //
    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("doPost() => POST 요청입니다.");
  }
}

