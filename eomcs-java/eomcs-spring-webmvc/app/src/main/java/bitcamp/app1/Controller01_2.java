// 페이지 컨트롤러 만드는 방법 - 한 개의 페이지 컨트롤러에 여러 개의 요청 핸들러 두기
package bitcamp.app1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Controller01_2 {

  // 요청이 들어 왔을 때 호출되는 메서드를 "request handler" 라 부른다.

  @RequestMapping("/c01_2_h1") // 핸들러에서 URL을 지정한다.
  @ResponseBody
  public String handler() {
    return "c01_2_h1";
  }

  @RequestMapping("/c01_2_h2") // 핸들러에서 URL을 지정한다.
  @ResponseBody
  public String handler2() {
    return "c01_2_h2";
  }

  @RequestMapping("/c01_2/h3") // URL을 지정할 때 디렉토리 형식으로 지정할 수 있다.
  @ResponseBody
  public String handler3() {
    return "/c01_2/h3";
  }

  @RequestMapping("/c01_2/h4") // URL을 지정할 때 디렉토리 형식으로 지정할 수 있다.
  @ResponseBody
  public String handler4() {
    return "/c01_2/h4";
  }

  // 한 개의 request handler에 여러 개의 URL을 매핑할 수 있다.
  @RequestMapping({"/c01_2/h5", "/c01_2/h6", "/c01_2/h7"})
  @ResponseBody
  public String handler5() {
    return "/c01_2/h5,h6,h7";
  }
}
