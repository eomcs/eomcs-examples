// 서블릿 만들기 - javax.servlet.Servlet 인터페이스 구현
package com.eomcs.web.ex01;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 서블릿 클래스를 만든 후, 서블릿 컨테이너에 등록해야만 사용할 수 있다.
// 등록 방법 1)
//   웹 애플리케이션 배치 파일(web.xml; DD(Deployment Descriptor) 파일)에 서블릿 정보를 등록한다.
//   => WEB-INF/web.xml
//   => DD File: Deployment Descriptor File
//   => 배치 예:
//      <servlet>
//          <servlet-name>서블릿별명</servlet-name>
//          <servlet-class>서블릿 클래스의 전체이름(패키지명 포함)</servlet-class>
//      </servlet>
//
//      <servlet-mapping>
//          <servlet-name>서블릿별명</servlet-name>
//          <url-pattern>클라이언트에서 요청할 때 사용할 URL(/로 시작해야 한다.)</url-pattern>
//      </servlet-mapping>
// 등록 방법 2)
//   서블릿 클래스 선언부에 @WebServlet 애노테이션을 붙인다.
//   => @WebServlet
//      @WebServlet(URL)
//      @WebServlet(value=URL)
//      @WebServlet(urlPatterns={"URL1", "URL2", ...})
// 
// 서블릿 실행 방법
// => http://서버주소:포트번호/웹애플리케이션이름/서블릿URL
// 예) http://localhost:8080/eomcs-java-web/ex01/s01
//
// 서블릿 구동 과정
// 1) 웹 브라우저가 서블릿 실행을 요청한다.
// 2) 서블릿 컨테이너는 해당 URL의 서블릿 객체를 찾는다.
// 3.1) 서블릿 객체를 아직 만들지 않았다면,
// => 서블릿 클래스에 대해 인스턴스를 생성한다.
// => 생성자를 호출한다.
// => init()를 호출한다.
// => service()를 호출한다.
// 3.2) 서블릿 객체가 생성되어 있다면,
// => service()를 호출한다.
//
// 만약 웹 애플리케이션이 종료된다면
// => 생성된 모든 서블릿들의 destroy() 메서드를 호출한다.
//
// 결론!
// => 특별한 옵션을 주지 않으면 클라이언트가 최초로 요청했을 때 서블릿 인스턴스를 생성한다.
// => 그리고 그 서블릿 인스턴스는 클래스 마다 오직 한 개만 생성된다.
// => init(), destroy()은 오직 한 번만 호출된다.
// => service()는 클라이언트가 요청할 때 마다 호출된다.
//
// 주의!
// => 서블릿 인스턴스는 오직 클래스 마다 한 개만 생성된다.
//    그래서 모든 클라이언트가 같은 서블릿 인스턴스를 사용한다.
// => 클라이언트마다 구분되어야 할 데이터는
//    서블릿 인스턴스 변수에 보관해서는 안된다.
// => 왜?
//    인스턴스는 모든 클라이언트가 공유하기 때문이다.
//

@WebServlet("/ex01/first")
public class Servlet01 implements Servlet {

  ServletConfig config;

  public Servlet01() {
    System.out.println("Servlet01()");
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    // 서블릿 객체를 생성한 후 생성자 다음에 이 메서드가 호출된다.
    // => 서블릿을 실행할 때 사용할 자원을 이 메서드에서 준비한다.
    // => 파라미터로 받은 ServletConfig 객체는 인스턴스 변수에 보관해 두었다가 필요할 때 사용한다.
    // => 각각의 서블릿 클래스마다 객체는 한 개만 생성된다.
    // => 따라서 각 서블릿에 대해 init()는 한 번만 호출된다.
    this.config = config;
    System.out.println("Servlet01.init(ServletConfig)");
  }

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {
    HttpServletRequest req2 = (HttpServletRequest)req;
    HttpServletResponse res2 = (HttpServletResponse)res;
    // 클라이언트가 이 서블릿의 실행을 요청할 때마다 호출된다.
    // 클라이언트가 요청한 작업을 수행한다.
    System.out.println("Servlet01.service(ServletRequest,ServletResponse)");
  }

  @Override
  public void destroy() {
    // 웹 애플리케이션을 종료할 때(서버 종료 포함) 호출된다.
    // => 이 서블릿이 만든 자원을 해제하는 코드를 이 메서드에 둔다.
    System.out.println("Servlet01.destroy()");
  }

  @Override
  public ServletConfig getServletConfig() {
    // 서블릿에서 작업을 수행하는 중에 서블릿 관련 설정 정보를 꺼낼 때 이 메서드를 호출한다.
    // 이 메서드가 리턴하는 값이 ServletConfig 객체인데
    // 이 객체를 이용하면 서블릿 설정 정보를 알 수 있다.
    // 보통 init()의 파라미터 값을 리턴한다.
    System.out.println("Servlet01.getServletConfig()");
    return this.config;
  }

  @Override
  public String getServletInfo() {
    // 서블릿 컨테이너가 관리자 화면을 출력할 때 이 메서드를 호출한다.
    // 이 메서드의 리턴 값은 서블릿을 소개하는 간단한 문자열이다.
    System.out.println("Servlet01.getServletInfo()");
    return "Servlet01";
  }

}


