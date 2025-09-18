package bitcamp.app3;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class Controller01Service {

  Map<String,Member> members = new HashMap<>();

  public Controller01Service() {
    members.put("user1@test.com", new Member(101, "user1@test.com", "1111"));
    members.put("user2@test.com", new Member(102, "user2@test.com", "1111"));
    members.put("user3@test.com", new Member(103, "user3@test.com", "1111"));
    members.put("user4@test.com", new Member(104, "user4@test.com", "1111"));
    members.put("user5@test.com", new Member(105, "user5@test.com", "1111"));
  }

  public Member getMember(String email, String password) {
    return members.get(email);
  }

}
