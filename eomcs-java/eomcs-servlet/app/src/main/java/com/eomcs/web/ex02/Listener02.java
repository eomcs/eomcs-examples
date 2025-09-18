package com.eomcs.web.ex02;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
// 리스너 만들기
// => 서블릿 컨테이너 또는 서블릿, 세션 등의 객체 상태가 변경되었을 때 보고 받는 옵저버
// => "Observer" 디자인 패턴이 적용된 것이다.
// => ServletContextListener
//    - 웹 애플리케이션을 시작하거나 종료할 때 보고 받고 싶다면 이 인터페이스를 구현하라.
// => ServletRequestListener
//    - 요청이 들어오거나 종료될 때 보고 받고 싶다면 이 인터페이스를 구현하라.
// => HttpSessionListener
//    - 세션이 생성되거나 종료될 때 보고 받고 싶다면 이 인터페이스를 구현하라.
// => XxxListener
//    - 기타 다양한 인터페이스가 있다. 문서를 참고하라.
//
// 리스너 배포하기
// => DD 파일(web.xml)에 설정하거나 애노테이션으로 설정하면 된다.
//
// 리스너의 용도
// => 서블릿 컨테이너나, 세션 등이 특별한 상태일 때 필요한 작업을 수행한다.
// => ServletContextListener
//    - 웹 애플리케이션을 시작할 때 Spring IoC 컨테이너 준비하기
//    - 웹 애플리케이션을 시작할 때 DB 커넥션 풀 준비하기
//    - 웹 애플리케이션을 종료할 때 DB 커넥션 풀에 들어 있는 모든 연결을 해제하기
// => ServletRequestListener
//    - 요청이 들어 올 때 로그 남기기
//
//
//@WebListener
public class Listener02 implements ServletRequestListener {

  public Listener02() {
    System.out.println("Listener02() 호출됨!");
  }

  @Override
  public void requestInitialized(ServletRequestEvent sre) {
    // 요청이 들어 왔을 때 호출된다.
    System.out.println("Listener02.requestInitialized()");
    HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
    System.out.println("클라이언트 IP: " + request.getRemoteAddr());
    System.out.println("요청 URL: " + request.getServletPath());
  }

  @Override
  public void requestDestroyed(ServletRequestEvent sre) {
    // 요청 처리를 완료할 때 호출된다.
    System.out.println("Listener02.requestDestroyed()");
  }
}






