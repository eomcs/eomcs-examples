// 기본 View Resolver 교체 - InternalResourceViewResolver 사용하기
package bitcamp.app2;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/c01_2")
public class Controller01_2 {

  // 테스트:
  // http://localhost:9999/eomcs-spring-webmvc/app2/c01_2/h1
  @GetMapping("h1")
  public String handler1(Map<String, Object> map) {

    map.put("name", "홍길동");
    map.put("age", 20);

    //
    // ViewResolver?
    // => 실행할 뷰를 찾는 일을 한다.
    // => 페이지 컨트롤러가 리턴한 뷰 이름에 해당하는 뷰 콤포넌트를 찾는 역할.
    // => ResourceBundleViewResolver
    //    - *.properties 에서 뷰 이름에 해당하는 콤포넌트의 URL을 찾는다.
    // => InternalResouceViewResolver
    //    - 미리 지정된 접두사, 접미사를 사용하여 뷰이름으로 콤포넌트의 URL을 완성한다.
    //
    // View?
    // => 뷰를 실행하는 일을 한다.
    // => 템플릿 엔진을 실행하여 실제 클라이언트로 보낼 콘텐트를 생성한다.
    // => FreeMarker, JSP/JSTL, Tiles, RSS/Atom, PDF, Excel 등의
    //    엔진을 이용하여 콘텐트를 생성하는 뷰가 있다.
    //
    // ViewResolver 교체
    // => InternalResourceViewResolver를 사용하여
    //    JSP URL의 접두어와 접미사를 미리 설정해 둘 수 있어 URL을 지정하기 편리하다.
    // => 교체 방법은 XML에서 설정하는 방법과 Java Config로 설정하는 방법이 있다.
    //    자세한 것은 App2Config 클래스를 참고하라!
    //
    // ViewResolver 실행 과정?
    // => 페이지 컨트롤러는 클라이언트가 요청한 작업을 실행한 후
    //    그 결과를 출력할 뷰의 이름을 리턴한다.
    // => 프론트 컨트롤러는 request handler가 리턴한 URL을
    //    view resolver에게 전달한다.
    // => view resolver는 자신의 정책에 맞춰서 뷰 URL을 준비한다.
    // => InternalResourceViewResolver의 경우
    //    request handler가 리턴한 URL 앞, 뒤에
    //    접두사와 접미사를 붙여 JSP를 찾는다.
    //    예를 들어 다음 URL을 리턴하면,
    //      "c01_1/h1"
    //    최종 JSP URL은,
    //      "/WEB-INF/jsp2/c01_2/h1.jsp"
    //    이다.
    // => 프론트 컨트롤러는 ViewResolver가 준비한 URL을 가지고
    //    View 객체를 통해 해당 URL의 자원을 실행한다.
    //
    return "c01_2/h1"; // => /WEB-INF/jsp2/c01_1/h1.jsp
  }

  // 테스트:
  // http://localhost:9999/eomcs-spring-webmvc/app2/c01_1/h2
  @GetMapping("h2")
  public void handler2(Model model) {

    model.addAttribute("name", "홍길동2");
    model.addAttribute("age", 30);

    // InternalResourceViewResolver를 사용하는 경우,
    // request handler가 뷰 이름을 리턴하지 않으면
    // request handler의 URL을 뷰 이름으로 사용한다.
    // 즉 이 핸들러의 URL은 "/c01_2/h2" 이기 때문에 뷰 이름도 "/c01_2/h2"가 된다.
    // InternalResourceViewResolver는 바로 이 URL을 사용하여 다음과 같이 최종 URL을 만든다.
    // => "/WEB-INF/jsp2/" + "/c01_2/h2" + ".jsp"
    // => "/WEB-INF/jsp2/c01_2/h2.jsp"
    //
    // 실무에서는 이 방법을 많이 사용한다.
  }

  // 테스트:
  // http://localhost:9999/eomcs-spring-webmvc/app2/c01_2/h3
  @GetMapping("h3")
  public Map<String, Object> handler3() {

    HashMap<String, Object> map = new HashMap<>();
    map.put("name", "홍길동3");
    map.put("age", 40);

    // Map 객체에 값을 담아 리턴하면
    // 프론트 컨트롤러는 Map 객체에 보관되어 있는 값들을 ServletRequest 보관소로 옮긴다.
    // 그리고 view URL은 request handler의 URL을 사용한다.
    // => "/WEB-INF/jsp2" + "/c01_2/h3" + ".jsp" = "/WEB-INF/jsp2/c01_2/h3.jsp
    return map;
  }

  // 테스트:
  // http://localhost:9999/eomcs-spring-webmvc/app2/c01_2/h4
  @GetMapping("h4")
  public ModelAndView handler4() {

    ModelAndView mv = new ModelAndView();
    mv.addObject("name", "홍길동3");
    mv.addObject("age", 40);
    mv.setViewName("c01_2/h4");

    // ModelAndView 객체에 값과 뷰 이름을 담아 리턴하면
    // 프론트 컨트롤러는 ModelAndView 객체에 보관되어 있는 값들을
    // ServletRequest 보관소로 옮기고,
    // 설정된 뷰 이름을 ViewResolver에게 넘긴다.
    //
    return mv;
  }

  // 테스트:
  // http://localhost:9999/eomcs-spring-webmvc/app2/c01_2/h5
  @GetMapping("h5")
  public ModelAndView handler5() {

    ModelAndView mv = new ModelAndView();
    mv.addObject("name", "홍길동3");
    mv.addObject("age", 40);

    // ModelAndView 객체에 값과 뷰 이름을 담아 리턴하면
    // 프론트 컨트롤러는 ModelAndView 객체에 보관되어 있는 값들을
    // ServletRequest 보관소로 옮기고,
    // 뷰 이름을 지정하지 않으면 request handler의 path를 ViewResolver에게 넘긴다.
    // => /c01_2/h5
    // InternalResourceViewResolver 는 위 URL을 다음과 같이 바꾼다.
    // => /WEB-INF/jsp2/c01_2/h5.jsp
    // 그런 후에 해당 URL의 JSP를 실행한다.
    return mv;
  }

}


