package bitcamp.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class App3WebApplicationInitializer extends
    AbstractAnnotationConfigDispatcherServletInitializer {

  private static Log log = LogFactory.getLog(App3WebApplicationInitializer.class);

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return null;
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[] {App3Config.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/app3/*"};
  }

  @Override
  protected String getServletName() {
    return "app3";
  }
}