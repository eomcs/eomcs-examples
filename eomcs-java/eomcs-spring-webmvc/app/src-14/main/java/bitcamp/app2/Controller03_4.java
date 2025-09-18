// 세션 다루기 - @SessionAttributes를 사용하는 예
package bitcamp.app2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/c03_4")
@SessionAttributes({"name", "age", "tel"})
public class Controller03_4 {

  // 테스트:
  // http://.../app2/c03_4/step0
  @GetMapping(value = "step0", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String step0(HttpSession session) {
    // 프론트 컨트롤러에게 HttpSession을 요구하면
    // 기존에 만든게 있다면 그 객체를 넘겨 줄 것이고,
    // 없다면 새로 만들어 넘겨 줄 것이다.
    // 어찌 되었든 이 요청 핸들러를 실행하는 순간 HttpSession 객체가 존재하게 된다.
    // 보통 로그인 과정에서 HttpSession 객체가 준비될 것이고,
    // 그 전에 JSP를 실행하는 과정에서 HttpSession 객체가 생성될 것이다.
    // 따라서 이 메서드처럼 일부러 HttpSession 객체를 만들게 할 필요는 없다.
    // 다만 @SessionAttributes와 @ModelAttribute를 테스트하기 위해
    // 예제를 실행하는 과정에서 HttpSession 객체를 미리 준비할 필요가 있기 때문에
    // 예제 테스트를 위해 이 메서드를 만든 것이다.
    // 이 메서드를 먼저 실행하여 HttpSession 객체를 준비한 후에 step1,2,3,4를 테스트 하라!
    return "세션을 준비했음!";
  }

  // 테스트:
  // http://.../app2/c03_4/step1?name=hong
  @GetMapping(value = "step1", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String step1(String name, Model model) {
    model.addAttribute("name", name);
    return "이름 저장했음!";
  }

  // 테스트:
  // http://.../app2/c03_4/step2?age=20
  @GetMapping(value = "step2", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String step2(int age, Model model) {
    model.addAttribute("age", age);
    return "나이 저장했음!";
  }

  // 테스트:
  // http://.../app2/c03_4/step3?tel=1111
  @GetMapping(value = "step3", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String step3(String tel, Model model) {
    model.addAttribute("tel", tel);
    return "전화번호 저장했음!";
  }

  // 테스트:
  // http://.../app2/c03_4/step4
  @GetMapping(value = "step4", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String step4(
          @ModelAttribute("name") String name,
          @ModelAttribute("age") int age,
          @ModelAttribute("tel") String tel,
          SessionStatus status) {

    // 이 페이지 컨트롤러가 작업을 하는 동안 세션에 임시 보관했던 값들은
    // 예를 들어, "DB에 저장한 후" 세션에서 제거한다.
    status.setComplete();

    return String.format("이름=%s, 나이=%d, 전화=%s 를 DB에 저장했음!\n", name, age, tel);
  }
}


