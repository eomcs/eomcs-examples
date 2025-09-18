// load on startup - 서블릿 객체 자동 생성하기(web.xml에 설정하기)
package com.eomcs.web.ex06;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 클라이언트가 실행을 요청하지 않아도 서블릿을 미리 생성하고 싶다면,
// loadOnStartup 프로퍼티 값을 지정하라.
// <servlet>
//   ...
//   <load-on-startup>1</load-on-startup>
// </servlet>
// 미리 생성할 서블릿이 여러 개 있다면, loadOnStartup에 지정한 순서대로 생성한다.
// 언제 서블릿을 미리 생성하는가?
// => 서블릿이 작업할 때 사용할 자원을 준비하는데 시간이 오래 걸리는 경우
//    웹 애플리케이션을 시작시킬 때 미리 서블릿 객체를 준비한다.
//    예) DB 연결, 소켓 연결, 필요한 환경 변수 로딩, 스프링 IoC 컨테이너 준비 등
//
@SuppressWarnings("serial")
public class Servlet02 extends HttpServlet {

  @Override
  public void init() throws ServletException {
    // 클라이언트가 서블릿의 실행을 요청할 때 서블릿 객체가 없으면 생성된다.
    // 서블릿 객체가 생성될 때 이 메서드가 호출된다.
    System.out.println("/ex06/s2 => init()");
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println("/ex06/s2 => service()");
  }
}

