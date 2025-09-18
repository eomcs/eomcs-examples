package bitcamp.web;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

public class AppDispatcherServletInitializer extends AbstractDispatcherServletInitializer {

  @Override
  protected WebApplicationContext createRootApplicationContext() {
    // ContextLoaderListener 가 사용할 IoC 컨테이너를 리턴한다.
    XmlWebApplicationContext iocContainer = new XmlWebApplicationContext();
    iocContainer.setConfigLocation("/WEB-INF/config/app-context.xml");
    return iocContainer;
  }

  @Override
  protected WebApplicationContext createServletApplicationContext() {
    // DispatcherServlet이 사용할 IoC 컨테이너를 리턴한다.
    XmlWebApplicationContext iocContainer = new XmlWebApplicationContext();
    iocContainer.setConfigLocation("/WEB-INF/app-servlet.xml");
    return iocContainer;
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/app/*"};
  }

  @Override
  protected String getServletName() {
    return "app";
  }
}






