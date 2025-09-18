package bitcamp.app3;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.catalina.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/c01_1")
public class Controller01_1 {

  Controller01Service service;

  public Controller01_1(Controller01Service service) {
    this.service = service;
  }

  @PostMapping("login")
  public Object login(
      String email,
      String password,
      HttpSession session) {

    Member member = service.getMember(email, password);
    if (member != null) {
      session.setAttribute("loginUser", member);
      return new Result("success", member);
    } else {
      return new Result("fail", "이메일 또는 암호가 맞지 않습니다.");
    }
  }

  @GetMapping("user")
  public Object login(
      @LoginUser Member loginUser) {
    return loginUser;
  }

}
