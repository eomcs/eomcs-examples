package com.eomcs.web.javascript;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Ex10Controller {

  @RequestMapping("/javascript/ex10/exam01")
  public Object exam01() throws Exception {
    Thread.sleep(5000); 
    return "exam01...ok!"; 
  }
}
