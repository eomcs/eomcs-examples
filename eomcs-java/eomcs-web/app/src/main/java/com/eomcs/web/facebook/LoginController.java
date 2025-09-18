package com.eomcs.web.facebook;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class LoginController {

  @RequestMapping("/facebook/login1")
  public Object login1() {
    return "test..ok!";
  }

  @RequestMapping("/facebook/login2")
  public Object login2(String accessToken) throws Exception {
    // 페이스북 서버에 접속하여 사용자 정보를 요구한다.
    System.out.println(accessToken);

    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.getForObject(
        "https://graph.facebook.com/v13.0/me?access_token={value1}&fields={value2}", // 요청할 URL 
        String.class, // 서버에서 받은 결과의 타입 
        accessToken, // URL의 첫 번째 자리에 들어갈 값
        "id,name,email,gender" // 페이스북 측에 요청하는 로그인 사용자 정보
        );

    return StringEscapeUtils.unescapeJava(result); 
  }
}
