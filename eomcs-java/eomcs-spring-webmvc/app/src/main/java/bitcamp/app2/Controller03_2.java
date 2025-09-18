// 세션 다루기 - @SessionAttributes, @ModelAttribute
package bitcamp.app2;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller 
@RequestMapping("/c03_2")

// request handler 가 뷰 컴포넌트(jsp)에 전달하는 값 중에서 
// 세션에 보관할 값의 이름을 지정하면 
// 프론트 컨트롤러는 그 값을 HttpSession 객체에도 보관해 둔다.
// 또한 @ModelAttribute에서 지정한 이름의 값을 세션에서 꺼낼 때도 사용한다.
// 즉 @SessionAttributes 에 이름을 지정해 두지 않으면 
// 세션에 해당 값이 들어 있어도 
// @ModelAttribute가 붙은 아규먼트에 값을 넣어주지 않는다.
@SessionAttributes({"name2", "age2"})

public class Controller03_2 {

  // 테스트:
  //   http://.../app2/c03_2/h1
  @GetMapping(value="h1", produces="text/plain;charset=UTF-8")
  @ResponseBody
  public String handler1(Model model) {

    // Model 객체에 값을 담으면 프론트 컨트롤러는 ServletRequest 보관소에 값을 옮긴다.
    // 만약 @SessionAttributes 에서 지정한 이름의 값이라면
    // HttpSession 객체에도 보관된다.
    model.addAttribute("name2", "임꺽정");
    model.addAttribute("age2", "30");

    // @SessionAttributes에 등록되지 않은 이름의 값은 세션에 보관되지 않는다.
    model.addAttribute("tel2", "1111-2222");

    return "세션에 값 보관했음!";

  }

  // 테스트:
  //   http://.../app2/c03_2/h2
  @GetMapping(value="h2", produces="text/plain;charset=UTF-8")
  @ResponseBody
  public String handler2(HttpSession session) {
    return String.format("name2=%s, age2=%s, tel2=%s", 
        session.getAttribute("name2"),
        session.getAttribute("age2"),
        session.getAttribute("tel2"));
  }

  // 테스트:
  //   http://.../app2/c03_2/h3
  @GetMapping(value="h3", produces="text/plain;charset=UTF-8")
  @ResponseBody
  public String handler3(
      // @ModelAttribute 에 지정된 이름이 @SessionAttributes에 있는 경우 
      // => 세션에 해당 값이 있으면 아규먼트에 넣어 준다.
      // => 세션에 해당 값이 없으면 예외가 발생한다.
      @ModelAttribute("name2") String name2,
      @ModelAttribute("age2") String age2,

      // @ModelAttribute 에 지정된 이름이 @SessionAttributes에 없는 경우 
      // => 요청 파라미터에 tel2 값이 있다면 그 값을 넣어준다.
      // => 요청 파라미터에 값이 없으면 아규먼트에 빈 문자열을 넣어 준다.
      // => @SessionAttributes에 등록한 이름이 아니기 때문에 세션에서 값을 꺼내지 않는다.
      @ModelAttribute("tel2") String tel2) {

    return String.format("name2=%s, age2=%s, tel2=%s", 
        name2, age2, tel2);
  }

  // 테스트:
  //   1) http://.../app2/c03_1/h1 을 실행하여 name과 age 값을 세션에 보관한다.
  //   2) http://.../app2/c03_2/h1 을 실행하여 name2와 age2 값을 세션에 보관한다.
  //   3) http://.../app2/c03_2/h4 을 실행하여 세션에 보관된 값을 꺼낸다.
  @GetMapping(value="h4", produces="text/plain;charset=UTF-8")
  @ResponseBody
  public String handler4(
      // c03_1/h1 에서 세션에 저장한 값 꺼내기
      // => 꺼내지 못한다. name과 age에는 빈 문자열이 저장된다.
      // => 왜?
      //    @ModelAttribute는 현재 페이지 컨트롤러의 @SessionAttributes에 
      //    지정된 이름에 한해서만 세션에서 값을 꺼내기 때문이다.
      @ModelAttribute("name") String name,
      @ModelAttribute("age") String age,

      // 현재 컨트롤러에서 세션에 저장한 값 꺼내기
      @ModelAttribute("name2") String name2,
      @ModelAttribute("age2") String age2) {

    return String.format("name=%s, age=%s, name2=%s, age2=%s", 
        name, age, name2, age2);
  }


}













