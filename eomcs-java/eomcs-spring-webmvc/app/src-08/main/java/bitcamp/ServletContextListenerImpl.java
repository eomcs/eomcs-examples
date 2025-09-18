package bitcamp;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    // 웹 애플리케이션이 시작될 때 호출된다.
    // - ServletContainerIntializer 구현체가 있다면 그 객체의 메서드가 먼저 호출된다.
    System.out.println("ServletContextListenerImpl.contextInitialized()");
  }
}
