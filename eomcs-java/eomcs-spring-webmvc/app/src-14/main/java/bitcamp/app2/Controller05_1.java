// JSON 콘텐트 출력하기
package bitcamp.app2;

import java.sql.Date;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;

@Controller
@RequestMapping("/c05_1")
public class Controller05_1 {

  ArrayList<Board> list = new ArrayList<>();

  public Controller05_1() {
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

  // 1) JSP에서 JSON 형식의 콘텐트 출력하기
  // 직접 테스트:
  //   http://.../app2/c05_1/h1
  // HTML에서 AJAX 요청 테스트:
  //   http://.../html/app2/c05_1.html
  //
  @GetMapping("h1")
  public void handler1(Model model) {
    model.addAttribute("list", this.list);
  }

  // 2) Google Gson 라이브러리를 사용하여 JSON 형식의 콘텐트 출력하기
  // => mvnrepository.com에서 gson 검색하여 라이브러리 정보를 가져온다.
  // => build.gradle 파일에 추가한다.
  // => '$gradle eclipse' 실행한다.
  // => 이클립스에서 프로젝트를 리프래시 한다.
  // 테스트:
  //   http://.../app2/c05_1/h2
  @GetMapping(value="h2", produces="text/plain;charset=UTF-8")
  @ResponseBody
  public String handler2() {
    return new Gson().toJson(this.list);
  }


  // 3) Google Gson 라이브러리를 사용하여 JSON 형식의 콘텐트 출력하기 II
  // => 페이지 컨트롤러의 리턴 값이 String이 아니면
  //    프론트 컨트롤러는
  //    Google의 Gson 라이브러리나 Jackson 라이브러리를 사용하여
  //    자동으로 JSON 형식의 문자열로 만들어 클라이언트로 출력한다.
  // => 단 Gson 또는 Jackson 라이브러리가 있어야 한다.
  //    둘 다 있다면 Jackson 라이브러리가 기본으로 사용된다.
  // => build.gradle 파일에서 gson 또는 jackson 라이브러리를 추가하는 부분의
  //    주석을 참고하라!
  // 테스트:
  //   http://.../app2/c05_1/h3
  @GetMapping("h3")
  @ResponseBody
  public Object handler3() {
    return this.list; // JSON 형식의 문자열은 자동으로 UTF-8로 인코딩 된다.
  }

}













