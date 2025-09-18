package com.eomcs.web.javascript;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Ex01Controller {

  @RequestMapping("/javascript/ex01/exam05_1")
  public Object exam05_1() throws Exception {
    Thread.sleep(10000); // 10초 동안 기다렸다가 작업을 재개한다.
    return "console.log('okok')"; // 자바스크립트 코드 조각을 리턴한다.
  }

  @RequestMapping("/javascript/ex01/exam05_2")
  public Object exam05_2() {
    return "console.log('nono')"; // 자바스크립트 코드 조각을 리턴한다.
  }

  @RequestMapping("/javascript/ex01/exam05_x")
  public Object exam05_x() throws Exception {
    Thread.sleep(10000);
    return "50,000"; // 데이터를 리턴한다.
  }
}
