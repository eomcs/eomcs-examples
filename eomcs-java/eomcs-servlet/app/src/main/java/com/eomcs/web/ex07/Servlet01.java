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

@WebServlet("/ex07/s1")
@SuppressWarnings("serial")
public class Servlet01 extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 테스트 방법:
    // => http://localhost:8080/java-web/ex07/s1?a=100&b=200&op=+
    // => http://localhost:8080/java-web/ex07/s1?a=100&b=200&op=-
    // => http://localhost:8080/java-web/ex07/s1?a=100&b=200&op=*
    //
    // 주의!
    // => + 연산을 수행하지 못한다.
    // 왜? 서버에 '+' 문자를 전송하면 URL 디코딩 할 때 '+' 문자가 공백(' ')문자로 변환되기 때문이다.
    // => + 연산자를 파라미터 값으로 보내고 싶다면 직접 URL 인코딩 해야 한다.
    // => 왜? + 문자는 URL에서 한 칸의 공백을 의미한다.
    //    즉 getParamter("op")의 리턴 값이 공백(" ") 이다.
    // => + 문자의 URL 인코딩 값은?
    // %2b
    // => 따라서 + 연산을 파라미터 값으로 보내려면
    // http://localhost:8080/java-web/ex07/s1?a=100&b=200&op=%2b
    //
    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();

    // 명심!
    // => 서블릿의 PrintWriter 객체를 통해 값을 출력하면
    //    클라이언트로 바로 출력되는 것이 아니라,
    //    내부에 마련된 버퍼로 출력된다.
    // => 그럼 출력 결과는 언제?
    //    - service() 메서드 호출이 끝났을 때 버퍼의 내용이 클라이언트로 출력된다.
    // => 만약 버퍼가 꽉 차면, service() 메서드 호출이 끝나기 전에 자동으로 출력된다.
    //
    // 따라서 다음 출력코드는 버퍼로 값을 출력한 것이다.
    // 아직 클라이언트로 보낸 상태가 아니다.
    out.println("더하기 계산 결과:");

    String op = request.getParameter("op");
    if (!op.equals("+")) {
      // 자신의 일이 아니라면 다른 서블릿으로 위임할 수 있다.
      // => 요청을 다른 서블릿으로 전달할 수 있다.
      // 요청배달자 = request.getRequestDispatcher(다른 서블릿 URL);
      RequestDispatcher 요청배달자 = request.getRequestDispatcher("/ex07/s2");

      // 이 서블릿이 지금까지 출력한 것은 모두 취소된다.
      // => 엥! 출력된 것이 최소될 수 있나요?
      // => PrintWriter 객체를 통해 출력하는 내용은 즉시 웹 브라우저로 전달되는 것이 아니다.
      //    내부 출력 버퍼(보통 8KB 크기)에 보관된다.
      // => 서블릿의 service() 메서드 호출이 종료될 때 비로서 버퍼의 내용이
      //    웹 브라우저로 전송된다.
      // => 물론 그 전에 버퍼가 꽉 차면 자동으로 출력된다.
      // => 그래서 다른 서블릿으로 실행을 위임하기 전에
      //    이 서블릿이 출력한 내용을 취소할 수 있는 것이다.
      요청배달자.forward(request, response);

      // 포워딩 한 후 리턴되는지 확인해보자!
      // 다음 출력이 서버 콘솔창에 보인다면 포워딩 후에 리턴됨을 알 수 있다.
      System.out.println("ex07/Servlet01!!!");

      // 그러면 포워딩 서블릿에서 다시 원래 서블릿으로 돌아온 후에
      // 클라이언트로 출력한다면?
      out.println("어떻게 될까?");

      // 리턴된 후에 출력하는 것은 모두 무시된다.
      // 따라서 포워딩 후에 리턴되면 하위의 코드를 실행하지 않고
      // 바로 종료하도록 다음과 같이 return 명령을 삽입하라!
      return;
    }

    int a = Integer.parseInt(request.getParameter("a"));
    int b = Integer.parseInt(request.getParameter("b"));

    out.printf("%d + %d = %d\n", a, b, a + b);
  }
}

