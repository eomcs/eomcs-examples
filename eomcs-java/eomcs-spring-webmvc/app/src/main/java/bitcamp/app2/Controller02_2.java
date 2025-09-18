// URL 에서 값 추출하기 - @MatrixVariable
package bitcamp.app2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/c02_2")
public class Controller02_2 {

  // 테스트:
  // http://.../app2/c02_2?name=kim&age=20
  @GetMapping
  @ResponseBody
  public String handler1(String name, int age) {
    // Query String 으로 값 받기
    return String.format("name=%s, age=%d", name, age);
  }

  // 테스트:
  // http://.../app2/c02_2/name=kim;age=20
  @GetMapping(value = "{value}", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String handler2(//
      @PathVariable("value") String value,
      // value 값 중에서 name 항목의 값을 받고 싶을 때 @MatrixVariable 을 사용한다.
      // 단 value의 형식은 "이름=값;이름=값;이름=값" 형태여야 한다.
      // @MatrixVariable("name") String name,
      // @MatrixVariable("age") int age

      // 매트릭스 변수명을 생략하면 아규먼트의 이름을 사용한다.
      @MatrixVariable String name,
      @MatrixVariable int age
      ) {

    // @MatrixVariable 애노테이션을 사용하려면
    // IoC 컨테이너에서 이 애노테이션을 활성화시키는 설정을 추가해야 한다.
    // 1) XML 설정
    //    => <mvc:annotation-driven enable-matrix-variables="true"/>
    // 2) Java Config 설정
    //    => @EnableWebMvc 애노테이션을 활성화시킨다.
    //    => WebMvcConfigurer 구현체를 정의한다.
    //    => UrlPathHelper 객체의 removeSemicolonContent 프로퍼티 값을 false로 설정한다.

    // 테스트1
    // http://.../app2/c02_2/name=kim;age=20
    // => @PathVariable("value") : name=kim <== 첫 번째 세미콜론의 값만 가져온다.
    // => @MatrixVariable("name") : kim
    // => @MatrixVariable("age") : 20
    //
    // 테스트2
    // http://.../app2/c02_2/user;name=kim;age=20
    // => @PathVariable("value") : user <== 첫 번째 세미콜론의 값만 가져온다.
    // => @MatrixVariable("name") : kim
    // => @MatrixVariable("age") : 20
    return String.format("value:%s \n name:%s, age:%d", value, name, age);
  }

  // 테스트:
  // http://.../app2/c02_2/name=teamA;qty=5/title=work1;state=1
  @GetMapping(value = "{team}/{task}", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String handler3(
      // 여러 개의 패스 변수가 있을 때 값을 꺼내는 방법
      @MatrixVariable String name,
      @MatrixVariable int qty,
      @MatrixVariable String title,
      @MatrixVariable int state
      ) {

    return String.format("team: %s(%d)\n task: %s, %d", name, qty, title, state);
  }

  // http://.../app2/c02_2/h4/name=teamA;qty=5/name=work1;qty=1
  @GetMapping(value = "h4/{team}/{task}", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String handler4(
      // 여러 개의 패스 변수가 있을 때 값을 꺼내는 방법
      // => 만약 항목의 이름이 같다면?
      @MatrixVariable(name = "name", pathVar = "team") String name1,
      @MatrixVariable(name = "qty", pathVar = "team") int qty1,
      @MatrixVariable(name = "name", pathVar = "task") String name2,
      @MatrixVariable(name = "qty", pathVar = "task") int qty2) {

    return String.format("team: %s(%d)\n task: %s, %d", name1, qty1, name2, qty2);
  }
}


