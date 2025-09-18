// 인클루딩(including) - 다른 서블릿의 작업을 포함시키기
package com.eomcs.web.ex07;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ex07/s11")
@SuppressWarnings("serial")
public class Servlet11 extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 테스트 방법:
    // => http://localhost:8080/java-web/ex07/s11?a=100&b=200&op=%2b
    // => http://localhost:8080/java-web/ex07/s11?a=100&b=200&op=-
    // => http://localhost:8080/java-web/ex07/s11?a=100&b=200&op=*
    //
    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("계산 결과:");
    out.println("---------------------------------------");
    String op = request.getParameter("op");

    RequestDispatcher 요청배달자 = null;

    if (op.equals("+")) {
      요청배달자 = request.getRequestDispatcher("/ex07/s11_plus");
    } else if (op.contentEquals("-")) {
      요청배달자 = request.getRequestDispatcher("/ex07/s11_minus");
    } else {
      요청배달자 = request.getRequestDispatcher("/ex07/s11_error");
    }

    // 다른 서블릿을 실행시킨다.
    // => forward()는 다른 서블릿으로 위임할 때 현재 서블릿의 출력을 취소한다.
    // => include()는 다른 서블릿으로 실행을 위임하더라도
    //    현재 서블릿의 실행 결과를 유지한다.
    // => 인클루드의 경우 현재 서블릿에서 setContentType()을 설정해야 한다.
    // => 포워드는 현재 서블릿에서 설정한 setContentType()이 무시된다.
    요청배달자.include(request, response);

    // including 서블릿을 실행한 후에 리턴되면
    // 현재 서블릿은 계속해서 출력할 수 있다.
    // => forwarding 서블릿을 실행한 후에서 리턴되어도
    //    현재 서블릿이 출력할 수 없었다.
    //    정확히 하면 출력한 것이 모두 무시되었다.

    out.println("---------------------------------------");
  }
}

