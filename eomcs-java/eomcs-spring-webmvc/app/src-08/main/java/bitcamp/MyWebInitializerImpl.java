package bitcamp;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration.Dynamic;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import com.eomcs.web.MyWebInitializer;

public class MyWebInitializerImpl implements MyWebInitializer {
  @Override
  public void start(ServletContext ctx) {
    System.out.println("MyWebInitializerImpl.. ok!");

    AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
    ac.register(AppConfig.class);
    ac.refresh();

    DispatcherServlet servlet = new DispatcherServlet(ac);
    Dynamic registration = ctx.addServlet("app", servlet);
    registration.setLoadOnStartup(1);
    registration.addMapping("/app/*");
  }
}
