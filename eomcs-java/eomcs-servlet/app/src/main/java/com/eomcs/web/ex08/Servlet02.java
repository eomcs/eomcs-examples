// 리프래시 II - 클라이언트에게 다른 URL을 요청하라는 명령
package com.eomcs.web.ex08;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ex08/s2")
@SuppressWarnings("serial")
public class Servlet02 extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 테스트 방법:
    // => http://localhost:8080/java-web/ex08/s2
    //
    // 리프래시
    // => 서버로부터 응답을 받고 내용을 출력한 후 특정 URL을 자동으로 요청하도록 만들 수 있다.
    // => 보통 웹 페이지를 자동으로 이동시키고 싶을 때 사용한다.
    // => 예 
    //    예1: 로그인 후 메인페이지로 자동 이동
    //    예2: 메일을 전송한 후 메일 목록 페이지로 자동 이동
    //    예3: 게시글 등록한 후 게시글 목록으로 자동 이동
    //    예4: 결제 완료 후 결제 상태 페이지로 자동 이동
    //

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<html><head>");

    // HTML을 출력하는 경우
    // 응답 헤더가 아니라 HTML 헤더에 리프래시 명령을 설정할 수 있다.
    out.println("<meta http-equiv='Refresh' content='3;url=s100'>");

    out.println("</head><body>");
    out.println("<h1>안녕하세요! - /ex08/s2</h1>");
    out.println("</body></html>");
  }
}

// HTTP 응답 프로토콜 예:
//
//HTTP/1.1 200 
//Content-Type: text/html;charset=UTF-8 
//Content-Length: 119 
//Date: Mon, 16 Sep 2019 03:36:11 GMT
//
//<html><head> 
//<meta http-equiv='Refresh' content='3;url=s100'> 
//</head><body>
//<h1>안녕하세요! - /ex08/s2</h1>
//</body></html>

