package com.eomcs.web.session;

import java.util.HashMap;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller2 {

  @RequestMapping("/session/step1")
  public Object step1(String name, HttpSession session) {
    // 세션에 값 저장
    session.setAttribute("name", name);
    return "step1() 실행!";
  }

  @RequestMapping("/session/step2")
  public Object step2(int age, HttpSession session) {
    session.setAttribute("age", age);
    return "step2() 실행!";
  }

  @RequestMapping("/session/step3")
  public Object step3(boolean working, HttpSession session) {
    session.setAttribute("working", working);
    return "step3() 실행!";
  }

  @RequestMapping("/session/step4")
  public Object step4(HttpSession session) {
    HashMap<String,Object> map = new HashMap<>();
    map.put("name", session.getAttribute("name"));
    map.put("age", session.getAttribute("age"));
    map.put("working", session.getAttribute("working"));

    return map;
  }

}
