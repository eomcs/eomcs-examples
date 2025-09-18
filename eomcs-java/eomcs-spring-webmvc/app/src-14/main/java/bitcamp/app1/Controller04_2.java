// 요청 핸들러의 아규먼트 - @RequestParam
package bitcamp.app1;

import java.io.PrintWriter;
import javax.servlet.ServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/c04_2")
public class Controller04_2 {

  // 클라이언트가 보낸 파라미터 값을 바로 받을 수 있다.

  // => 요청 핸들러의 파라미터로 선언하면 된다.
  //    단 파라미터 앞에 @RequestParam 애노테이션을 붙인다.
  //    그리고 클라이언트가 보낸 파라미터 이름을 지정한다.
  // 테스트:
  // => http://localhost:9999/eomcs-spring-webmvc/app1/c04_2/h1?name=kim
  @GetMapping("h1")
  @ResponseBody
  public void handler1(
      PrintWriter out,
      ServletRequest request,
      @RequestParam(value = "name") String name1,
      @RequestParam(name = "name") String name2, // value와 name은 같은 일을 한다.
      @RequestParam("name") String name3, // value 이름을 생략할 수 있다.
      /* @RequestParam("name") */ String name // 요청 파라미터 이름과 메서드 파라미터(아규먼트)의 이름이 같다면
      // 애노테이션을 생략해도 된다.
      ) {

    out.printf("name=%s\n", request.getParameter("name"));
    out.printf("name=%s\n", name1);
    out.printf("name=%s\n", name2);
    out.printf("name=%s\n", name3);
    out.printf("name=%s\n", name);
  }

  // 테스트:
  // http://.../app1/c04_2/h2?name1=kim&name2=park
  @GetMapping("h2")
  @ResponseBody
  public void handler2(
      PrintWriter out,

      @RequestParam("name1") String name1, // 애노테이션을 붙이면 필수 항목으로 간주한다.
      // 따라서 파라미터 값이 없으면 예외가 발생한다.

      String name2, // 애노테이션을 붙이지 않으면 선택 항목으로 간주한다.
      // 따라서 파라미터 값이 없으면 null을 받는다.

      @RequestParam(value = "name3", required = false) String name3,
      // required 프로퍼티를 false로 설정하면 선택 항목으로 간주한다.

      @RequestParam(value = "name4", defaultValue = "ohora") String name4
      // 기본 값을 지정하면 파라미터 값이 없어도 된다.
      ) {

    out.printf("name1=%s\n", name1);
    out.printf("name2=%s\n", name2);
    out.printf("name3=%s\n", name3);
    out.printf("name4=%s\n", name4);
  }
}


