// 요청 핸들러의 리턴 값 - view URL 리턴하기, 리다이렉트, forward/include
package bitcamp.app1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.JstlView;

@Controller
@RequestMapping("/c05_2")
public class Controller05_2 {

  // 테스트:
  // http://localhost:9999/eomcs-spring-webmvc/app1/c05_2/h1
  @GetMapping("h1")
  public String handler1() {
    // 메서드 선언부에 @ResponseBody를 붙이지 않으면
    // 프론트 컨트롤러는 view URL로 간주한다.
    // => 리턴 URL의 '/'는 웹 애플리케이션 루트를 의미한다.
    return "/jsp/c05_2.jsp";
  }

  // 테스트:
  // http://localhost:9999/eomcs-spring-webmvc/app1/c05_2/h2
  @GetMapping("h2")
  public String handler2() {
    // MVC 패턴에서는 항상 Controller에 의해 View가 통제되어야 한다.
    // Controller를 경유하지 않고 View를 실행하게 해서는 안된다.
    // 그래야 View에 대해 일관성 있는 제어가 가능하다.
    // 문제는 jsp 파일을 웹 애플리케이션의 일반 폴더에 두게 되면
    // 다음과 같이 클라이언트에서 직접 실행을 요청할 수 있다.
    // http://localhost:9999/eomcs-spring-webmvc/jsp/c05_2.jsp
    //
    // 이것을 막으려면, 다음과 같이 WEB-INF 폴더 밑에 JSP 파일을 두어라.
    // /WEB-INF 폴더에 있는 파일은 클라이언트에서 직접 실행을 요청할 수 없다.
    return "/WEB-INF/jsp/c05_2.jsp";
  }

  // 테스트:
  // http://localhost:9999/eomcs-spring-webmvc/app1/c05_2/h3
  @GetMapping("h3")
  public View handler3() {
    return new JstlView("/WEB-INF/jsp/c05_2.jsp");
  }

  // 테스트:
  // http://localhost:9999/eomcs-spring-webmvc/app1/c05_2/h4
  @GetMapping("h4")
  public ModelAndView handler4() {
    System.out.println("===> /app1/c05_2/h4");
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/WEB-INF/jsp/c05_2.jsp");
    return mv;
  }

  // 테스트:
  // http://localhost:9999/eomcs-spring-webmvc/app1/c05_2/h5
  @GetMapping("h5")
  public String handler5() {
    // 리다이렉트를 지정할 때는 URL 앞에 "redirect:" 접두어를 붙인다.
    // 즉 HTTP 응답이 다음과 같다.
    // HTTP/1.1 302
    // Location: h4
    // Content-Language: ko-KR
    // Content-Length: 0
    // Date: Fri, 19 Apr 2019 07:57:00 GMT

    return "redirect:h4";
  }

  // 테스트:
  // http://localhost:9999/eomcs-spring-webmvc/app1/c05_2/h6
  @GetMapping("h6")
  public String handler6() {
    // 포워드를 지정할 때는 URL 앞에 "forward:" 접두어를 붙인다.
    // 인클루드를 지정할 때는 URL 앞에 "include:" 접두어를 붙인다.
    return "forward:h4";
  }
}


