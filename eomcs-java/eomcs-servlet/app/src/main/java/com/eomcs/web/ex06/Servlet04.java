// 서블릿 초기화 파라미터 - web.xml에서 설정하기
package com.eomcs.web.ex06;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

// 서블릿이 사용할 값을 DD 설정으로 지정할 수 있다.
// => web.xml 에 지정하였다.
@SuppressWarnings("serial")
public class Servlet04 extends HttpServlet {

  @Override
  public void init() throws ServletException {
    System.out.println("ex06/s4 => init()");

    ServletConfig config = this.getServletConfig();

    System.out.printf("driver=%s\n", config.getInitParameter("jdbc.driver"));
    System.out.printf("url=%s\n", config.getInitParameter("jdbc.url"));
    System.out.printf("username=%s\n", config.getInitParameter("jdbc.username"));
    System.out.printf("password=%s\n", config.getInitParameter("jdbc.password"));
  }
}

