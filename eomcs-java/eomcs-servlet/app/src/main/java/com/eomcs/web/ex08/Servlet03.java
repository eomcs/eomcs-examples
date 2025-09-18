// 리다이렉트 - 응답할 때 콘텐트를 보내지 않는다. 바로 다른 페이지를 요청하라고 명령한다.
package com.eomcs.web.ex08;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ex08/s3")
@SuppressWarnings("serial")
public class Servlet03 extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 테스트 방법:
    // => http://localhost:8080/java-web/ex08/s2
    //
    // 리다이렉트
    // => 클라이언트의 요청을 받은 후 콘텐트를 보내는 대신
    //    다른 페이지의 URL을 알려줄 때 사용한다.
    // => 웹 브라우저는 응답 받는 즉시 해당 페이지를 요청한다.
    //    웹 서버로부터 콘텐트를 받지 않았기 때문에 어떤 것도 출력하지 않는다.
    //    바로 다른 페이지로 이동한다.
    // => 리프래시와 달리 서버는 콘텐트(message-body)를 보내지 않는다.
    // => 사용 예:
    //    - 로그인 후 로그인 결과를 출력하지 않고 즉시 메인 화면으로 보내고 싶을 때
    //    - 결제완료 후 결과를 출력하지 않고 즉시 결제 상태 페이지로 보내고 싶을 때
    // => 리다이렉트 HTTP 응답 프로토콜
    //
    //    HTTP/1.1 302 <----- 리다이렉트 응답 상태 코드
    //    Location: s100 <----- 리다이렉트 URL
    //    Content-Type: text/html;charset=UTF-8
    //    Content-Length: 0
    //    Date: Tue, 02 Apr 2019 03:38:45 GMT
    //    빈 줄
    //         <---- 콘텐트를 보내지 않는다. 즉 message-body가 없다.
    //
    System.out.println("/ex08/s3");
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<html><head>");
    out.println("<title>리다이렉트</title>");
    out.println("</head><body>");

    // 버퍼가 꽉차서 클라이언트로 자동 출력되면
    // 리다이렉트가 안먹힌다.
    // 왜? 리다이렉트 명령을 응답헤더로 보내기 때문이다.
    // 이미 클라이언트로 출력을 완료했는데 어떻게 응답헤더를 보내는가?
    // 다음 반복문 주석을 풀라!
    for (int i = 0; i < 1000; i++) {
      out.println("안녕하세요! - /ex08/s3<br>");
    }
    out.println("</body></html>");

    // 클라이언트에게 URL을 알려줄 때 상대 경로를 지정할 수 있다.
    // forward/include 와 달리 '/'는 컨텍스트 루트(웹 애플리케이션 루트)가 아닌
    // 웹 서버 루트를 의미한다.
    response.sendRedirect("s100");

    // 리다이렉트를 하는 순간 이전까지 버퍼로 출력된 내용은 모두 버려진다.
    // 왜? 리다이렉트는 클라이언트로 콘텐트를 보내지 않는다.
    //
    // 만약 출력한 내용이 버퍼를 꽉 채워서 자동으로 응답을 했다면 어떻게 되나요?
    // => 이미 응답했기 때문에 리다이렉트는 동작되지 않는다.
  }
}

// redirect HTTP 응답 프로토콜
// HTTP/1.1 302 <---- 요청한 자원이 다른 URL에 있음을 표시한다.
// Location: s100 <---- 다른 URL의 주소를 알려준다.
// Content-Type: text/html;charset=UTF-8
// Content-Length: 0
// Date: Tue, 07 Apr 2020 07:05:34 GMT
// Keep-Alive: timeout=20
// Connection: keep-alive
//
// <---내용을 보내지 않는다.

