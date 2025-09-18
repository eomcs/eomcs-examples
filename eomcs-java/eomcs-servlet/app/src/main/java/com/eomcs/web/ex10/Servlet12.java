// 쿠키(cookie) 읽기 - 유효기간이 설정된 쿠키 읽기
package com.eomcs.web.ex10;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ex10/s12")
@SuppressWarnings("serial")
public class Servlet12 extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 테스트 방법:
    // => http://localhost:8080/java-web/ex10/s12
    //

    // 클라이언트 보낸 쿠키 읽기
    // => 요청 헤더에 포함된 쿠키를 읽는다.
    // => HTTP 요청 프로토콜
    /*
     * GET /java-web/ex10/s2 HTTP/1.1 Host: localhost:8080 Connection: keep-alive ... Cookie:
     * name=hong; age=20; working=true; name2=홍길동; name3=%ED%99%8D%EA%B8%B8%EB%8F%99
     */

    // 쿠키 꺼내기
    // => 쿠키를 이름으로 한 개씩 추출할 수 없다.
    // => 한 번에 배열로 받아야 한다.
    // => 요청 헤더에 쿠키가 한 개라도 없으면 리턴 되는 것은 null이다.
    Cookie[] cookies = request.getCookies();

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();

    if (cookies != null) {
      for (Cookie c : cookies) {
        out.printf("%s=%s\n", c.getName(), c.getValue());
      }
    }

  }
}


