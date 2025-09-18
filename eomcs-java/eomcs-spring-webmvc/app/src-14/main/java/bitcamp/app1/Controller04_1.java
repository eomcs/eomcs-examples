// 요청 핸들러의 아규먼트 - 프론트 컨트롤러로부터 받을 수 있는 파라미터 값
package bitcamp.app1;

import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/c04_1")
public class Controller04_1 {

  // 프론트 컨트롤러(DispatcherServlet)로부터 받고 싶은 값이 있다면
  // 요청 핸들러를 정의할 때 받고 싶은 타입의 파라미터를 선언하라!
  // 그러면 프론트 컨트롤러가 메서드를 호출할 때 해당 타입의 값을 넘겨줄 것이다.

  // ServletContext는 의존 객체로 주입 받아야 한다.
  // 요청 핸들러에서 파라미터로 받을 수 없다.
  @Autowired
  ServletContext sc;

  // 요청 핸들러에서 받을 수 있는 타입의 아규먼트를 선언해 보자!
  @GetMapping("h1")
  @ResponseBody
  public void handler1(
      // ServletContext sc,
      // ServletContext는 파라미터로 받을 수 없다. 예외 발생!
      // 의존 객체로 주입 받아야 한다.
      ServletRequest request,
      ServletResponse response,
      HttpServletRequest request2,
      HttpServletResponse response2,
      HttpSession session,
      Map<String, Object> map, // JSP에 전달할 값을 담는 임시 보관소
      Model model, // Map과 같다. 둘 중 한 개만 받으면 된다.
      PrintWriter out // 클라이언트에게 콘텐트를 보낼 때 사용할 출력 스트림
      ) {

    out.printf("ServletContext: %b\n", sc != null);
    out.printf("ServletRequest: %b\n", request != null);
    out.printf("ServletResponse: %b\n", response != null);
    out.printf("HttpServletRequest: %b\n", request2 != null);
    out.printf("HttpServletResponse: %b\n", response2 != null);
    out.printf("HttpSession: %b\n", session != null);
    out.printf("Map: %b\n", map != null);
    out.printf("Model: %b\n", model != null);
    out.printf("ServletRequest == HttpServletRequest : %b\n", request == request2);
    out.printf("ServletResponse == HttpServletResponse : %b\n", response == response2);
  }
}


