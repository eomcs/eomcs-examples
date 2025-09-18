// JSON 콘텐트 입력받기 - @RestController
package bitcamp.app2;

import java.net.URLDecoder;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/c05_3")
public class Controller05_3 {

  // 1) 요청 파라미터 값을 낱개로 입력 받기
  // 테스트
  //   http://.../app2/c05_3/h1?no=1&title=ok&writer=kim&viewCount=100
  @RequestMapping(value="h1", produces="text/plain;charset=UTF-8")
  public Object handler1(
      int no,
      String title,
      String writer,
      int viewCount) {

    return String.format("%d,%s,%s,%d", no, title, writer, viewCount);
  }

  // 2) 요청 파라미터 값을 객체로 입력 받기
  // 테스트
  //   http://.../app2/c05_3/h2?no=1&title=ok&writer=kim&viewCount=100
  @RequestMapping(value="h2", produces="text/plain;charset=UTF-8")
  public Object handler2(Board board) {
    return board.toString();
  }

  // 3) JSON 형식의 요청 파라미터 값을 통째로 문자열로 받기
  // 테스트
  //   http://.../html/app2/c05_3.html
  @RequestMapping(value="h3", produces="text/plain;charset=UTF-8")
  public Object handler3(@RequestBody String content) throws Exception {
    System.out.println(content);
    System.out.println(URLDecoder.decode(content, "UTF-8"));
    return "OK!";
  }

  // 4) JSON 형식의 요청 파라미터 값을 맵 객체로 받기
  // => HttpMessageConverter 구현체(예:MappingJackson2HttpMessageConverter)가
  //    클라이언트가 보낸 데이터를 Map 객체에 담아준다.
  // => 이 기능을 쓰고 싶다면 Gson 또는 Jackson 라이브러리를 반드시 포함해야 한다.
  //    그래야 스프링의 DispatcherServlet에서 찾는다.
  // 테스트
  //   http://.../html/app2/c05_3.html
  @RequestMapping(value="h4", produces="text/plain;charset=UTF-8")
  public Object handler4(@RequestBody Map<String,Object> content) throws Exception {
    System.out.println(content);
    return "OK!";
  }

  // 5) JSON 형식의 요청 파라미터 값을 도메인 객체로 받기
  // => HttpMessageConverter 구현체(예: MappingJackson2HttpMessageConverter)가
  //    클라이언트가 보낸 데이터를 도메인 객체(예: Board, Member, Lesson 등)에 담아준다.
  // => Json 데이터의 프로퍼티 명과 도메인 객체의 프로퍼티 명이 일치해야 한다.
  //
  // 테스트
  //   http://.../html/app2/c05_3.html
  @RequestMapping(value="h5", produces="text/plain;charset=UTF-8")
  public Object handler5(@RequestBody Board content) throws Exception {
    System.out.println(content);

    // 주의!
    // => 클라이언트에서 보낸 날짜 데이터의 문자열 형식이 yyyy-MM-dd 형태여야 한다.
    //    그래야 java.util.Date 타입의 값으로 변환해 준다.
    //    예) 2019-05-01 ===> java.util.Date 객체 변환 성공!
    // => 만약 이 형태가 아니면 변환할 수 없어 실행 오류가 발생한다.
    //    예) 05/01/2020 또는 2020-5-1 ===> 변환 오류!
    //
    // @JsonFormat 애노테이션 사용
    // => 이 애노테이션은 MappingJackson2HttpMessageConverter를 위한 것이다.
    //    GsonHttpMessageConverter는 이 애노테이션을 인식하지 않는다.
    // => 도메인 객체의 프로퍼티에 이 애노테이션을 붙이면
    //    2019-05-01 이나 2019-5-1 모두 처리할 수 있다.
    // => 뿐만 아니라, 도메인 객체를 JSON 문자열로 변환할 때도
    //    해당 형식으로 변환된다.
    //

    return "OK!";
  }
}













