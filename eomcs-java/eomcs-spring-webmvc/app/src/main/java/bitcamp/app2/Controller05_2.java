// JSON 콘텐트 출력하기 - @RestController
package bitcamp.app2;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// => 페이지 컨트롤러를 @RestController로 선언하면,
//    리턴 값은 HttpMessageConverter에 의해 자동으로 변환된다.
// => @ResponseBody를 붙일 필요가 없다.
//
@RequestMapping("/c05_2")
public class Controller05_2 {

  ArrayList<Board> list = new ArrayList<>();

  public Controller05_2() {
    list.add(new Board(1, "제목입니다1", "내용", "홍길동", 10, Date.valueOf("2019-5-1")));
    list.add(new Board(2, "제목입니다2", "내용", "홍길동2", 11, Date.valueOf("2019-5-2")));
    list.add(new Board(3, "제목입니다3", "내용", "홍길동3", 12, Date.valueOf("2019-5-3")));
    list.add(new Board(4, "제목입니다4", "내용", "홍길동4", 13, Date.valueOf("2019-5-4")));
    list.add(new Board(5, "제목입니다5", "내용", "홍길동5", 14, Date.valueOf("2019-5-5")));
    list.add(new Board(6, "제목입니다6", "내용", "홍길동6", 15, Date.valueOf("2019-6-1")));
    list.add(new Board(7, "제목입니다7", "내용", "홍길동7", 16, Date.valueOf("2019-6-1")));
    list.add(new Board(8, "제목입니다8", "내용", "홍길동8", 17, Date.valueOf("2019-6-1")));
    list.add(new Board(9, "제목입니다9", "내용", "홍길동9", 18, Date.valueOf("2019-6-1")));
    list.add(new Board(10, "제목입니다10", "내용", "홍길동10", 19, Date.valueOf("2019-7-1")));
    list.add(new Board(11, "제목입니다11", "내용", "홍길동11", 11, Date.valueOf("2019-8-1")));
    list.add(new Board(12, "제목입니다12", "내용", "홍길동12", 12, Date.valueOf("2019-9-1")));
    list.add(new Board(13, "제목입니다13", "내용", "홍길동13", 13, Date.valueOf("2019-10-1")));
  }

  // 테스트:
  //   http://.../app2/c05_2/h1
  @GetMapping("h1")
  public Object handler1() {
    return this.list; // JSON 형식의 문자열은 자동으로 UTF-8로 인코딩 된다.
  }

  // 테스트:
  //   http://.../app2/c05_2/h2
  @GetMapping(value="h2", produces="text/plain;charset=UTF-8")
  public String handler2() {
    return "안녕하세요!"; // String 타입은 그대로 출력한다.
    // 단 출력 문자열의 인코딩을 지정해야 한글이 깨지지 않는다.
  }

  // 테스트:
  //   http://.../app2/c05_2/h3
  @GetMapping("h3")
  public int handler3() {
    return 100; // primitive 타입의 값도 그대로 출력한다.
  }

  // 테스트:
  //   http://.../app2/c05_2/h4
  @GetMapping("h4")
  public Object handler4() {
    // primitive 타입이나 String 타입의 값을 JSON 형식으로 출력하려면
    // 도메인 객체나 맵 객체 담아 리턴하라!
    HashMap<String,Object> content = new HashMap<>();
    content.put("v1", 100);
    content.put("v2", "Hello");

    return content;
  }
}













