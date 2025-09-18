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
// => DD 파일(web.xml)에 설정하기
//      <!-- 필터 등록 -->
//      <filter>
//          <filter-name>f01</filter-name>
//          <filter-class>com.eomcs.web.ex02.Filter01</filter-class>
//      </filter>
//
//      <!-- 필터를 적용할 URL 설정 -->
//      <filter-mapping>
//          <filter-name>f01</filter-name>
//          <url-pattern>/ex02/*</url-pattern>
//      </filter-mapping>
// => 애노테이션으로 설정하기
//      @WebFilter(URL)
//
// 필터의 용도
// => 서블릿을 실행하기 전후에 필요한 작업을 수행
// => 서블릿 실행 전
// - 웹브라우저가 보낸 암호화된 파라미터 값을 서블릿으로 전달하기 전에 암호 해제하기
// - 웹브라우저가 보낸 압축된 데이터를 서블릿으로 전달하기 전에 압축 해제하기
// - 서블릿의 실행을 요청할 권한이 있는지 검사하기
// - 로그인 사용자인지 검사하기
// - 로그 남기기
// => 서블릿 실행 후
// - 클라이언트로 보낼 데이터를 압축하기
// - 클라이언트로 보낼 데이터를 암호화시키기
//
//@WebFilter("/ex02/*")
public class Filter01 implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // 웹 애플리케이션을 시작할 때 필터 객체를 생성한다.
    // 이 메서드는 필터 객체를 생성한 후 자동으로 호출된다.
    // 필터가 사용할 자원을 이 메서드에서 준비한다.
    // => 웹 애플리케이션을 시작할 때 필터는 자동 생성된다.
    System.out.println("Filter01.init()");
  }

  @Override
  public void destroy() {
    // 웹 애플리케이션을 종료할 때 호출된다.
    // init()에서 준비한 자원을 해제한다.
    System.out.println("Filter01.destroy()");
  }

  @Override
  public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain)
          throws IOException, ServletException {
    // 요청이 들어 올 때 마다 호출된다.
    // => 단 필터를 설정할 때 지정된 URL의 요청에만 호출된다.
    // => 서블릿이 실행되기 전에 필터가 먼저 실행된다.
    // => 서블릿을 실행한 후 다시 필터로 리턴한다.
    System.out.println("Filter01.doFilter() : 시작");

    // 다음 필터를 실행한다.
    // 만약 다음 필터가 없으면,
    // 요청한 서블릿의 service() 메서드를 호출한다.
    // service() 메서드 호출이 끝나면 리턴된다.
    chain.doFilter(request, response);

    // 체인에 연결된 필터나 서블릿이 모두 실행된 다음에
    // 다시 이 필터로 리턴될 것이다.
    System.out.println("Filter01.doFilter() : 종료");
  }
}


