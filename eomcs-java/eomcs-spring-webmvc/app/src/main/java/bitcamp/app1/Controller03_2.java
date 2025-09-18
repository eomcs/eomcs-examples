// request handler를 구분하는 방법 - 요청 헤더 이름으로 구분하기
package bitcamp.app1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/c03_2")
public class Controller03_2 {

  // 테스트 방법:
  // => http://localhost:9999/eomcs-spring-webmvc/html/app1/c03_2.html
  // => 요청 헤더 중에서 특정 이름을 갖는 헤더가 있을 때 호출될 메서드를 지정할 수 있다.
  // => 웹 페이지에서 링크를 클릭하거나 입력 폼에 값을 넣고 등록 버튼을 누르는
  //    일반적인 상황에서는 요청헤더에 임의의 헤더를 추가할 수 없다.
  // => 자바스크립트 등의 프로그래밍으로 임의의 HTTP 요청을 할 때
  //    HTTP 프로토콜에 표준이 아닌 헤더를 추가할 수 있다.
  //    그 헤더를 처리하는 메서드를 정의할 때 사용한다.
  // => 보통 Open API를 개발하는 서비스 회사에서 많이 사용한다.

  // @GetMapping(headers="name")
  @RequestMapping(method = RequestMethod.GET, headers = "name")
  @ResponseBody
  public String handler1() {
    return "handler1";
  }

  // @GetMapping(headers="age")
  @RequestMapping(method = RequestMethod.GET, headers = "age")
  @ResponseBody
  public String handler2() {
    return "handler2";
  }

  @GetMapping(headers = {"age", "name"})
  @ResponseBody
  public String handler3() {
    return "handler3";
  }

  @GetMapping
  @ResponseBody
  public String handler4() {
    return "handler4";
  }
}
