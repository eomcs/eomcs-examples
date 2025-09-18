package com.eomcs.web.javascript;

import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class Ex07Controller {

  // 외부 사이트로 HTTP 요청을 수행하는 도구
  RestTemplate restTemplate;

  public Ex07Controller(RestTemplateBuilder restTemplateBuilder) {
    restTemplate = restTemplateBuilder.build();
  }

  @RequestMapping("/javascript/ex07/test1")
  public void test1() {
    // view url을 지정하지 않으면 요청 URL을 view url로 사용한다.
  }

  @RequestMapping("/javascript/ex07/test2")
  public String test2(HttpServletResponse response) {
    // 모든 클라이언트에 대해
    // 이 URL의 AJAX 요청을 허락한다는 의미다. 
    response.setHeader("Access-Control-Allow-Origin", "*");

    // HttpServletResponse를 사용한다면 view URL을 명시적으로 지정해야 한다.
    return "/javascript/ex07/test2";
  }

  @ResponseBody
  @RequestMapping("/javascript/ex07/test3")
  public String test3(String url) {
    return restTemplate.getForObject(url, String.class);
  }

  @RequestMapping("/javascript/ex07/test4")
  public void test4(String name, int age, Model model) {
    model.addAttribute("name", name);
    model.addAttribute("age", age);
  }

  @RequestMapping("/javascript/ex07/test5")
  public void test5() throws Exception {
    Thread.sleep(2000);
  }

  @RequestMapping("/javascript/ex07/test6")
  public void test6(int a, int b, Model model) throws Exception {
    model.addAttribute("result", a + b);
  }

}






