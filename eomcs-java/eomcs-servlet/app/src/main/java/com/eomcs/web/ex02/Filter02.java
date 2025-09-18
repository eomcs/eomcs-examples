package com.eomcs.web.ex02;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

// 서블릿 컨테이너가 관리하는 컴포넌트
// => 서블릿, 필터, 리스너
//
// 필터 만들기
// => javax.servlet.Filter 인터페이스 규칙에 따라 작성한다.
//
// 필터 배포하기
// => DD 파일(web.xml)에 설정하거나 애노테이션으로 설정하면 된다.
// => 다음과 같이 애노테이션으로 할 수도 있다.
//
// 필터 실행 순서
// => 필터의 실행 순서를 임의로 조정할 수 없다.
// => 필터를 정의할 때 순서에 의존하는 방식으로 프로그래밍 하지 말라.
// => 필터의 실행 순서에 상관없이 각 필터가 독립적으로 동작하도록 작성하라.

//@WebFilter("/ex02/a/*")
public class Filter02 implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // 필터 객체를 생성한 후 제일 처음으로 호출된다.
    // 필터가 사용할 자원을 이 메서드에서 준비한다.
    System.out.println("Filter02.init()");
  }

  @Override
  public void destroy() {
    // 웹 애플리케이션이 종료될 때 호출된다.
    // init()에서 준비한 자원을 해제한다.
    System.out.println("Filter02.destroy()");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    // 요청이 들어 올 때 마다 호출된다.
    // => 단 필터를 설정할 때 지정된 URL의 요청에만 호출된다.
    // => 서블릿이 실행되기 전에 필터가 먼저 실행된다.
    // => 서블릿을 실행한 후 다시 필터로 리턴한다.
    System.out.println("Filter02.doFilter() : 시작");

    // 다음 필터를 실행하거나 요청한 서블릿을 실행하려면 다음 코드를 반드시 실행해야 한다.
    chain.doFilter(request, response);

    // 체인에 연결된 필터나 서블릿이 모두 실행된 다음에 다시 이 필터로 리턴될 것이다.
    System.out.println("Filter02.doFilter() : 종료");
  }
}


