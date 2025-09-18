package com.eomcs.web.ex12;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.annotation.WebListener;

@WebListener
public class Listener01 implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    System.out.println("웹애플리케이션 시작!!");
    System.out.println("서블릿 배치!");

    // 1) 서블릿 컨테이너에 등록할 서블릿 객체를 준비한다.
    Servlet03 servlet = new Servlet03();

    // 2) 서블릿 정보를 관리하는 객체를 꺼낸다.
    ServletContext sc = sce.getServletContext();

    // 3) ServletContext 객체를 통해 서블릿 객체를 등록한다.
    Dynamic 서블릿설정정보 = sc.addServlet("ex12.s03", servlet);

    // 4) 등록된 서블릿의 배치 정보를 설정한다.
    서블릿설정정보.addMapping("/ex12/s03");

  }
}
