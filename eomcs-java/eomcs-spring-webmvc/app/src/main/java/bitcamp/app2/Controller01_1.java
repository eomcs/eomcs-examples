// 기본 View Resolver 사용하기
package bitcamp.app2;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/c01_1")
public class Controller01_1 {

  // 테스트:
  // http://localhost:9999/eomcs-spring-webmvc/app2/c01_1/h1
  @GetMapping("h1")
  // @ResponseBody // 뷰 이름을 리턴 할 때는 이 애노테이션을 붙이면 안된다.
  public String handler1(Model model) {

    model.addAttribute("name", "홍길동");
    model.addAttribute("age", 20);

    return "/jsp/c01_1.jsp";
    // 기본 ViewResolver는 리턴 값으로 URL을 받아
    // 웹 애플리케이션 디렉토리에서 JSP를 찾는다.
    // 웹 애프리케이션이 경로가 /eomcs-spring-webmvc 라면,
    // JSP 경로는 다음과 같다.
    // ==> /eomcs-spring-webmvc/jsp/c01_1.jsp
    //
    // InternalResourceViewResolver로 교체한 다음의 JSP URL은?
    // => /WEB-INF/jsp2//jsp/c01_1.jsp.jsp
  }

  // 테스트:
  // http://localhost:9999/eomcs-spring-webmvc/app2/c01_1/h2
  @GetMapping("h2")
  public void handler2(Model model) {
    model.addAttribute("name", "홍길동2");
    model.addAttribute("age", 30);

    // 기본 ViewRosolver를 사용할 때는
    // 뷰 이름을 리턴하지 않으면 오류 발생!
    //
    // InternalResourceViewResolver로 교체한 다음은?
    // => 리턴 값이 없으면 요청 URL(/c01_1/h2)을 리턴 값으로 사용한다.
    // => 따라서 ViewResolver가 계산한 최종 URL은
    // /WEB-INF/jsp2/c01_1/h2.jsp
    //

  }

  // 테스트:
  // http://localhost:9999/eomcs-spring-webmvc/app2/c01_1/h3
  @GetMapping("h3")
  public String handler3(Map<String, Object> map) {

    map.put("name", "홍길동3");
    map.put("age", 40);

    return "/WEB-INF/jsp/c01_1.jsp";
    // MVC 모델에서는 JSP는 뷰 콤포넌트로서 출력이라는 역할을 담당한다.
    // 출력할 데이터를 준비하는 일은 페이지 컨트롤러가 담당한다.
    // 그래서 JSP를 실행할 때는 항상 페이지 컨트롤러를 통해 실행해야 한다.
    // 페이지 컨트롤러가 하는 일이 없어도 프로그래밍의 일관성을 위해
    // 직접 JSP을 요청하지 않고, 페이지 컨트롤러를 통해 요청해야 한다.
    //
    // 그런데 웹 디렉토리에 JSP를 두면 클라이언트에서 JSP를 직접 요청할 수 있다.
    // 페이지 컨트롤러를 경유하지 않은 상태에서 실행해봐야 소용없지만,
    // 그래도 요청은 할 수 있다.
    // 이런 의미 없는 요청을 막는 방법으로,
    // JSP 파일을 /WEB-INF 폴더 아래에 두는 것을 권장한다.
    //
    // 웹 브라우저에서 다음 URL의 JSP를 요청해보라!
    // 1) http://localhost:8888/bitcamp-java-spring-webmvc/jsp/c01_1.jsp
    // => 클라이언트가 요청할 수 있다.
    // 2) http://localhost:8888/bitcamp-java-spring-webmvc/WEB-INF/jsp/c01_1.jsp
    // => 클라이언트가 요청할 수 없다.
    // => /WEB-INF 폴더에 있는 자원들은 클라이언트에서 직접 요청할 수 없다.
    // => 그래서 잘못된 요청을 막을 수 있다.
    // 실무에서는 이 방법을 사용한다.
  }

}


