// 클라이언트로 출력하기
package com.eomcs.web.ex03;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/ex03/s1") // http://localhost:8080/web/ex03/s1
public class Servlet01 extends GenericServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {

    // 출력 스트림을 꺼내기 전에
    // 출력할 때 사용할 문자표(charset)를 지정하지 않으면
    // 리턴 받은 출력 스트림은 기본 문자표 ISO-8859-1 을 사용한다.
    // 즉 자바의 유니코드 문자를 ISO-8859-1 문자표에 따라 변환하여 출력한다.
    // 자바(Unicode2;UTF-16) ===> 출력문자(ISO-8859-1)
    PrintWriter out = res.getWriter();

    // 다음 영어 유니코드 문자는 ISO-8859-1 문자표에 있기 때문에 제대로 변환된다.
    out.println("Hello!");

    // 그러나 다음 유니코드 문자는 ISO-8859-1 문자표에 없기 때문에
    // 없다는 의미에서 '?' 문자표 바뀌어 출력된다.
    out.println("안녕하세요!");
    out.println("こんにちは");
    out.println("您好");
    out.println("مع السلامة؛ إلى اللقاء!");
  }
}


