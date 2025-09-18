package com.eomcs.web;

import java.util.Set;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

// 서블릿 컨테이너가 시작될 때 보고를 받고 싶으면,
// => SerlvetContainerIntializer 규칙에 따라 작성해야 한다.
// => 이 클래스의 전체 이름(패키지명을 포함한 클래스명)을 다음 경로의 파일에 등록해야 한다.
//       /META-INF/services/javax.servlet.ServletContainerInitializer 파일
// 

public class ServletContainerInitializerImpl 
  implements ServletContainerInitializer{

  @Override
  public void onStartup(Set<Class<?>> types, ServletContext ctx) throws ServletException {
    System.out.println("ServletContainerInitializerImpl.onStartup()...호출됨!");
    
    // 프로그램 코드로 서블릿 배치하기
    // => 서블릿 객체를 등록한다.
    Dynamic register = ctx.addServlet("hi", new HiServlet());
    
    // => 서블릿에 URL을 붙인다.
    register.addMapping("/hi");
  }
}






