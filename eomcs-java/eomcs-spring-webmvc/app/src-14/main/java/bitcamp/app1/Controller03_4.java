// request handler를 구분하는 방법 - Content-Type 헤더의 값에 따라 구분하기
package bitcamp.app1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/c03_4")
public class Controller03_4 {

  // Content-Type 요청 헤더
  // => HTTP 클라이언트가 보내는 데이터의 콘텐트 타입이다.
  // => 프론트 컨트롤러는 보내는 데이터의 타입에 따라 처리를 구분할 수 있다.

  // 테스트 방법:
  // => http://localhost:9999/eomcs-spring-webmvc/html/app1/c03_4.html
  // => 클라이언트가 POST 요청으로 데이터를 보낼 때 기본 형식은 다음과 같다.
  //      application/x-www-form-urlencoded
  // => <form> 태그에서 enctype 속성에 "mulpart/form-data"를 지정하면
  //    해당 형식으로 서버에 값을 보낸다.
  // => 자바스크립트를 사용하여 개발자가 임의의 형식으로 값을 보낼 수 있다.
  //
  // 클라이언트가 POST로 요청할 때 보내는 데이터의 유형에 따라 호출될 메서드를 구분할 때 사용한다.

  // 다음 메서드는 application/x-www-form-urlencoded 형식의 데이터를 소비한다.
  // => 즉 클라이언트의 HTTP 요청에서 Content-Type 헤더의 값이 위와 같을 때
  //    이 메서드를 호출하라는 의미다.
  @PostMapping(consumes = "application/x-www-form-urlencoded")
  @ResponseBody
  public String handler1() {
    return "handler1";
  }

  // 다음 메서드는 multipart/form-data 형식의 데이터를 소비한다.
  @PostMapping(consumes = "multipart/form-data")
  @ResponseBody
  public String handler2() {
    return "handler2";
  }

  // 다음 메서드는 text/csv 형식의 데이터를 소비한다.
  @PostMapping(consumes = "text/csv")
  @ResponseBody
  public String handler3() {
    return "handler3";
  }

  // 다음 메서드는 application/json 형식의 데이터를 소비한다.
  @PostMapping(consumes = "application/json")
  @ResponseBody
  public String handler4() {
    return "handler4";
  }

  // 다음 메서드는 Content-Type 헤더가 없을 때 호출된다.
  @RequestMapping
  @ResponseBody
  public String handler5() {
    return "handler5";
  }
}
