package com.eomcs.web.ex02;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

// 서블릿 컨테이너가 관리하는 컴포넌트
// => 서블릿, 필터, 리스너
//
// 리스너 만들기
// => 서블릿 컨테이너 또는 서블릿, 세션 등의 객체 상태가 변경되었을 때 보고 받는 옵저버
// => "Observer" 디자인 패턴이 적용된 것이다.
// => ServletContextListener
//    - 웹 애플리케이션을 시작하거나 종료할 때 보고 받고 싶다면 이 인터페이스를 구현하라.
// => ServletRequestListener
//    - 요청이 들어오거나 응답할 때 보고 받고 싶다면 이 인터페이스를 구현하라.
// => HttpSessionListener
//    - 세션이 생성되거나 종료될 때 보고 받고 싶다면 이 인터페이스를 구현하라.
// => XxxListener
//    - 기타 다양한 인터페이스가 있다. 문서를 참고하라.
//
// 리스너 배포하기
//=> DD 파일(web.xml)에 설정하기
//      <listener>
//          <listener-class>com.eomcs.web.ex02.Listener01</listener-class>
//      </listener>
//
//=> 애노테이션으로 설정하기
//      @WebListener
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
public class Listener01 implements ServletContextListener {

  public Listener01() {
    System.out.println("Listener01() 호출됨!");
  }

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    // 웹 애플리케이션이 시작될 때 호출된다.
    System.out.println("Listener01.contextInitialized()");
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    // 웹 애플리케이션이 종료될 때 호출된다.
    System.out.println("Listener01.contextDestroyed()");
  }
}






