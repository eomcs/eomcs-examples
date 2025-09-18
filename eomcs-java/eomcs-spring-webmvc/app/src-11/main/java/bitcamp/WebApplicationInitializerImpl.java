package bitcamp;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// WebApplicationInitializer 구현체를 통해 DispatcherServlet을 등록하는 두 번째 방법
// => 인터페이스를 직접 구현하는 대신에 그 인터페이스를 미리 구현한
//    AbstractAnnotationConfigDispatcherServletInitializer 클래스를 상속 받기
//    - 이 클래스는 미리 AnnotationConfigWebApplicationContext IoC 컨테이너를 준비했다.
//    - 따라서 IoC 컨테이너를 따로 설정할 필요가 없다.
//    - 또한 DispatcherServlet을 등록하는 코드가 이미 작성되어 있기 때문에
//      따로 등록할 필요가 없다.
//    - 즉 인터페이스를 직접 구현하는 것 보다 편하다.
//
public class WebApplicationInitializerImpl
extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    System.out.println("==> getRootConfigClasses()");
    // ContextLoaderListener의 IoC 컨테이너가 사용할 Java Config 클래스를 리턴한다.
    return null;
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    System.out.println("==> getServletConfigClasses()");
    // DispatcherServlet의 IoC 컨테이너가 사용할 Java config 클래스를 리턴한다.
    return new Class<?>[] {AppConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    System.out.println("==> getServletMappings()");
    // DispatcherServlet을 등록할 때 이 메서드를 호출한다.
    // 이 메서드의 리턴 값이 리턴 값이 URL 패턴으로 사용된다.
    return new String[] {"/app/*"};
  }

  @Override
  protected String getServletName() {
    System.out.println("==> getServletName()");
    // DispatcherServlet을 등록할 때 이 메서드를 호출한다.
    // 이 메서드의 리턴 값이 서블릿의 이름으로 사용된다.
    // 이 메서드를 오버라이딩 하지 않으면 기본 이름("dispatcher")이 사용된다.
    // => 한 개만 등록할 것이라면 오버라이딩 하지 않아도 되지만,
    //    여러 개의 DispatcherServlet을 등록할 것이라면 오버라이딩 하여
    //    반드시 이름을 다르게 해야 한다.
    return "app";
  }

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    // 이 메서드가 호출될 때 간단한 메시지를 출력하기 위해 오버라이딩 하였다.
    // 따라서 원래의 메서드를 반드시 호출해줘야 한다.
    System.out.println("WebApplicationInitializerImpl.onStartup()...호출됨2!");
    super.onStartup(servletContext);
  }
}


