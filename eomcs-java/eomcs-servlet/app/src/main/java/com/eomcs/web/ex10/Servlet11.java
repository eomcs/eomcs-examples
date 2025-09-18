// 쿠키(cookie) - 유효기간 설정하기
package com.eomcs.web.ex10;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ex10/s11")
@SuppressWarnings("serial")
public class Servlet11 extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 테스트 방법:
    // => http://localhost:8080/java-web/ex10/s11
    //

    // 쿠키 유효기간
    // => 유효기간을 설정하면 웹브라우저는 그 기간 동안 보관하고 있다가
    //    웹서버에게 쿠키를 보낸다.
    // => HTTP 응답 프로토콜
    //
    // HTTP/1.1 200
    // Set-Cookie: v1=aaa
    // Set-Cookie: v2=bbb; Max-Age=30; Expires=Wed, 08-Apr-2020 02:41:43 GMT
    // Set-Cookie: v3=ccc; Max-Age=60; Expires=Wed, 08-Apr-2020 02:42:13 GMT
    // Content-Type: text/plain;charset=UTF-8
    // Content-Length: 36
    // Date: Wed, 08 Apr 2020 02:41:13 GMT
    // Keep-Alive: timeout=20
    // Connection: keep-alive

    // 유효기간을 설정하지 않으면 웹브라우저가 실행되는 동안에만 웹서버에게 쿠키를 보낸다.
    // => 웹 브라우저는 메모리에 쿠키를 보관한다.
    Cookie c1 = new Cookie("v1", "aaa");

    // 유효기간을 설정하면 웹브라우저를 종료해도 삭제되지 않는다.
    // 단 유효기간이 지나면 웹서버에 보내지 않고 삭제한다.
    // => 웹 브라우저는 로컬 디스크에 쿠키를 보관한다.
    Cookie c2 = new Cookie("v2", "bbb");
    c2.setMaxAge(30); // 쿠키를 보낸 이후 30초 동안만 유효

    Cookie c3 = new Cookie("v3", "ccc");
    c3.setMaxAge(60); // 쿠키를 보낸 이후 60초 동안만 유효

    // 쿠키를 응답 헤더에 포함시키기
    response.addCookie(c1);
    response.addCookie(c2);
    response.addCookie(c3);

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("/ex10/s11 - 쿠키 보냈습니다.");
  }
}


