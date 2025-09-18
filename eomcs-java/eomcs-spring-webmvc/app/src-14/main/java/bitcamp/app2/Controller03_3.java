// 세션 다루기 - 세션의 값을 무효화시키는 방법
package bitcamp.app2;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/c03_3")

// 세션에 보관된 값 중에서 현재 페이지 컨트롤러에서 사용할 값을 지정한다.
// 또한 세션에 보관할 값이기도 하다.
@SessionAttributes({"name", "name2"})
public class Controller03_3 {

  // 테스트:
  // http://.../app2/c03_3/h1
  @GetMapping(value = "h1", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String handler1(HttpSession session) {
    return String.format("name=%s, age=%s, name2=%s, age2=%s",
        session.getAttribute("name"),
        session.getAttribute("age"),
        session.getAttribute("name2"),
        session.getAttribute("age2"));
  }

  // 테스트:
  // http://.../app2/c03_3/h2
  @GetMapping(value = "h2", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String handler2(HttpSession session) {
    // 세션을 완전히 무효화시키기
    session.invalidate();
    // 용도:
    // => 페이지 컨트롤러에 상관없이 모든 세션 값을 삭제할 때 사용하라.
    // 세션 자체를 무효화시켜 다음에 요청이 들어 왔을 때 새로 세션을 만들게 한다.
    //
    return "session.invalidate()";
  }

  // 테스트:
  // http://.../app2/c03_3/h3
  @GetMapping(value = "h3", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String handler3(SessionStatus status) {
    // 현재 페이지 컨트롤러의 @SessionAttributes 에 지정된 값만 무효화시키기
    status.setComplete();
    // 용도:
    // => 보통 페이지 컨트롤러는 서로 관련된 작업을 처리하는 요청 핸들러를 정의한다.
    //    예) BoardController : add(), detail(), list(), update(), delete()
    // => 또는 트랜잭션과 관련된 작업을 처리하는 요청 핸들러를 두기도 한다.
    //    예) BookOrderController: 장바구니담기(), 주문하기(), 결제하기()
    // => 이렇게 특정 작업에 관계된 요청 핸들러가 작업하는 동안
    //    공유할 데이터가 있다면 세션에 보관하면 편할 것이다.
    //    작업이 완료되면 그 작업을 처리하는 동안 세션에 보관했던 데이터는 삭제해야 한다.
    //    세션의 모든 데이터가 아니라
    //    현재 페이지 컨트롤러가 보관한 데이터만 삭제하고 싶을 때
    //    바로 이 방식으로 처리하면 된다.
    // => 즉 세션을 그대로 유지한채로 이 페이지 컨트롤러에서
    // @SessionAttributes로 지정한 값만 무효화시킬 때 사용한다.
    return "status.setComplete()";
  }

  @GetMapping(value="h4", produces="text/plain;charset=UTF-8")
  @ResponseBody
  public String handler4(
      @ModelAttribute("name") String name,
      @ModelAttribute("age") String age,
      @ModelAttribute("name2") String name2,
      @ModelAttribute("age2") String age2) {

    return String.format("name=%s, age=%s, name2=%s, age2=%s",
        name, age, name2, age2);
  }

}


