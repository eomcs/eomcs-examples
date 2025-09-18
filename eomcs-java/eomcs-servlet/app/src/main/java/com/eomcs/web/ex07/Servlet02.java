// 포워딩(forwarding) - 서블릿 실행을 위임하기
package com.eomcs.web.ex07;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ex07/s2")
@SuppressWarnings("serial")
public class Servlet02 extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("빼기 계산 결과:");

    String op = request.getParameter("op");
    if (!op.equals("-")) {
      // 자신의 일이 아니라면 다른 서블릿으로 위임할 수 있다.
      // => 요청을 다른 서블릿으로 전달할 수 있다.
      // 요청배달자 = request.getRequestDispatcher(다른 서블릿 URL);
      RequestDispatcher 요청배달자 = request.getRequestDispatcher("/ex07/s3");

      // 이 서블릿이 지금까지 버퍼로 출력한 것은 모두 취소된다.
      // => 엥! 출력된 것이 최소될 수 있나요?
      요청배달자.forward(request, response);

      System.out.println("ex07/Servlet02!!!");
      // 포워딩 한 서블릿을 실행한 후 리턴된다.
      // 단 리턴된 후에 출력하는 것은 모두 무시된다.
      // 따라서 포워딩 후에 리턴되면 하위의 코드를 실행하지 않고
      // 바로 종료하도록 다음과 같이 return 명령을 삽입하라!
      return;
    }

    int a = Integer.parseInt(request.getParameter("a"));
    int b = Integer.parseInt(request.getParameter("b"));

    out.printf("%d - %d = %d\n", a, b, a - b);
  }
}

