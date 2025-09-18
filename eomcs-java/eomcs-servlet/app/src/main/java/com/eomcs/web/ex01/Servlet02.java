// 서블릿 만들기 - javax.servlet.GenericServlet 추상 클래스 상속
package com.eomcs.web.ex01;

import java.io.IOException;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

// GenericServlet 추상 클래스
// => javax.servlet.Servlet 인터페이스를 구현하였다.
// => service() 메서드만 남겨두고 나머지 메서드들은 모두 구현하였다.
// => 따라서 이 클래스를 상속 받는 서브 클래스는 service() 만 구현하면 된다.
//
// @WebServlet 애노테이션
// => web.xml 에 서블릿 정보를 설정하는 대신에 
//    이 애노테이션을 사용하여 서블릿을 설정할 수 있다.
// => 이 애노테이션을 활성화시키려면 
//    web.xml의 <web-app> 태그에 다음 속성을 추가해야 한다.
//      metadata-complete="false"
//    속성의 값은 false 여야 한다.
//
//@WebServlet(urlPatterns={"/ex01/s2","/ex01/ss2","/ex01/sss2"})
//@WebServlet(urlPatterns={"/ex01/s2"})
//@WebServlet(urlPatterns="/ex01/s2")
//@WebServlet(value="/ex01/s2")
@WebServlet("/ex01/s2")
public class Servlet02 extends GenericServlet {

  // GenericServlet 추상 클래스는 java.io.Serialize 인터페이스를 구현하였다.
  // => serialVersionUID 변수 값을 설정해야 한다.
  private static final long serialVersionUID = 1L;

  public Servlet02() {
    System.out.println("Servlet02()");
  }

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {
    System.out.println("Servlet02.service(ServletRequest,ServletResponse)");
  }
}


