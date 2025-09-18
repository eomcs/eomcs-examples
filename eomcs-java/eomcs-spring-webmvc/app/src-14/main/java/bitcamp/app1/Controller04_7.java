// 요청 핸들러의 아규먼트 - @Cookie
package bitcamp.app1;

import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/c04_7")
public class Controller04_7 {

  // 클라이언트가 보낸 쿠키 꺼내기
  // => @CookieValue(쿠키명) 애노테이션을 request handler의 아규먼트 앞에 붙인다.

  // 테스트:
  // http://.../c04_7/h1
  @GetMapping("h1")
  @ResponseBody
  public void handler1(
      PrintWriter out,
      HttpServletResponse response
      ) {
    // 이 메서드에서 쿠키를 클라이언트로 보낸다.
    try {
      // 쿠키의 값이 ASCII가 아니라면 URL 인코딩 해야만 데이터가 깨지지 않는다.
      // URL 인코딩을 하지 않으면 ? 문자로 변환된다.
      response.addCookie(new Cookie("name1", "AB가각"));
      response.addCookie(new Cookie("name2", URLEncoder.encode("AB가각", "UTF-8")));
      response.addCookie(new Cookie("name3", "HongKildong"));
      response.addCookie(new Cookie("age", "30"));

    } catch (Exception e) {
      e.printStackTrace();
    }

    out.println("send cookie!");
  }

  // 테스트:
  // http://.../c04_7/h2
  @GetMapping(value = "h2", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String handler2(
      @CookieValue(value = "name1", required = false) String name1,
      @CookieValue(value = "name2", defaultValue = "") String name2,
      @CookieValue(value = "name3", defaultValue = "홍길동") String name3,
      @CookieValue(value = "age", defaultValue = "0") int age // String ===> int 자동 변환
      ) throws Exception {

    //
    // 1) URLEncoder.encode("AB가각", "UTF-8")
    // ==> JVM 문자열은 UTF-16 바이트 배열이다.
    //     0041 0042 ac00 ac01
    // ==> UTF-8 바이트로 변환한다.
    //     41 42 ea b0 80 ea b0 81
    // ==> 8비트 데이터가 짤리지 않도록 URL 인코딩으로 7비트화 시킨다.
    //     "AB%EA%B0%80%EA%B0%81"
    //     41 42 25 45 41 25 42 30 25 38 30 25 45 41 25 42 30 25 38 31
    // ==> 웹 브라우저에서는 받은 값을 그대로 저장
    //
    // 2) 쿠키를 다시 서버로 보내기
    // ==> 웹 브라우저는 저장된 값을 그대로 전송
    //     "AB%EA%B0%80%EA%B0%81"
    //     41 42 25 45 41 25 42 30 25 38 30 25 45 41 25 42 30 25 38 31
    // ==> 프론트 컨트롤러가 쿠키 값을 꺼낼 때 자동으로 URL 디코딩을 수행한다.
    //     즉 7비트 문자화된 코드를 값을 원래의 8비트 코드로 복원한다.
    //     41 42 ea b0 80 ea b0 81
    // ==> 디코딩 하여 나온 바이트 배열을 UTF-16으로 만든다.
    //     문제는 바이트 배열을 ISO-8859-1로 간주한다는 것이다.
    //     그래서 UTF-16으로 만들 때 무조건 앞에 00 1바이트를 붙인다.
    //     0041 0042 00ea 00b0 0080 00ea 00b0 0081
    //     그래서 한글이 깨진 것이다.
    //
    // 해결책:
    // => UTF-16을 ISO-8859-1 바이트 배열로 변경한다.
    //    41 42 ea b0 80 ea b0 81
    byte[] originBytes = name2.getBytes("ISO-8859-1");

    // => 다시 바이트 배열을 UTF-16으로 바꾼다.
    //    이때 바이트 배열이 UTF-8로 인코딩된 값임을 알려줘야 한다.
    //    0041 0042 ac00 ac01
    String namex = new String(originBytes, "UTF-8");

    return String.format(//
        "name1=%s\n name2=%s\n name2=%s\n name3=%s\n age=%d\n", //
        name1, name2, namex, name3, age);
  }


}


