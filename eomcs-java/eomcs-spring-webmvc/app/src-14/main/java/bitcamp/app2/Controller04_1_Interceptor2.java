package bitcamp.app2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

// 인터셉터 만들기
// => 프론트 컨트롤러와 페이지 컨트롤러 사이에 코드를 삽입하는 기술
// => 프론트 컨트롤러와 뷰 컴포넌트 사이에 코드를 삽입하는 기술
// 
// 인터셉터를 배치하기
// => 프론트 컨트롤러의 IoC 설정 파일에 배치 정보를 추가한다.
// 
public class Controller04_1_Interceptor2 implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    // 페이지 컨트롤러의 핸들러를 호출하기 전에 이 메서드가 먼저 호출된다.
    System.out.println("Interceptor2.preHandle()");
    
    // 다음 인터셉터나 페이지 컨트롤러를 계속 실행하고 싶다면 true를 리턴한다.
    // 여기서 요청 처리를 완료하고 싶다면 false를 리턴한다.
    return true; 
  }
  
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    // 페이지 컨트롤러의 핸들러가 리턴한 후 이 메서드가 호출된다.
    System.out.println("Interceptor2.postHandle()");
  }
  
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    // JSP를 실행한 후 이 메서드가 호출된다.
    System.out.println("Interceptor2.afterCompletion()");
  }
}
