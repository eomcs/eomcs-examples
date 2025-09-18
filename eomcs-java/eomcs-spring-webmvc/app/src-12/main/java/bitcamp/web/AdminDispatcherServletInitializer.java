package bitcamp.web;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

public class AdminDispatcherServletInitializer extends AbstractDispatcherServletInitializer {

  @Override
  protected WebApplicationContext createRootApplicationContext() {
    // ContextLoaderListener 가 사용할 IoC 컨테이너를 리턴한다.
    // AppDispatcherServletInitializer가 이미 리턴했기 때문에
    // 이 객체는 그냥 null을 리턴한다.
    return null;
  }

  @Override
  protected WebApplicationContext createServletApplicationContext() {
    // DispatcherServlet이 사용할 IoC 컨테이너를 리턴한다.
    XmlWebApplicationContext iocContainer = new XmlWebApplicationContext();
    iocContainer.setConfigLocation("/WEB-INF/admin-servlet.xml");
    return iocContainer;
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/admin/*"};
  }

  @Override
  protected String getServletName() {
    return "admin";
  }
}






