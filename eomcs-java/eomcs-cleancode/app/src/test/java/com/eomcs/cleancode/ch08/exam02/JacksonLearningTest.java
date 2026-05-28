package com.eomcs.cleancode.ch08.exam02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

// 학습 테스트 예제 3: Jackson ObjectMapper 동작 방식을 운영 코드가 아닌 테스트로 먼저 탐색한다.
// - 없는 필드, 알 수 없는 필드, 매핑 규칙을 테스트로 확인한다.
// - 라이브러리 업그레이드 시 기존 기대 동작을 이 테스트로 검증한다.
class JacksonLearningTest {

  static class User {
    private long id;
    private String name;

    // Jackson 역직렬화를 위해 기본 생성자와 setter가 필요하다
    User() {}

    User(long id, String name) {
      this.id = id;
      this.name = name;
    }

    public long getId() {
      return id;
    }

    public void setId(long id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  void json을_User로_변환한다() throws Exception {
    String json =
        """
        {"id":1,"name":"Kim"}
        """;

    User user = mapper.readValue(json, User.class);

    assertEquals(1L, user.getId());
    assertEquals("Kim", user.getName());
  }

  @Test
  void User를_json으로_변환한다() throws Exception {
    User user = new User(1L, "Kim");

    String json = mapper.writeValueAsString(user);

    assertEquals("{\"id\":1,\"name\":\"Kim\"}", json);
  }

  @Test
  void 알수없는_필드가_있으면_예외가_발생한다() {
    String json =
        """
        {"id":1,"name":"Kim","unknown":"value"}
        """;

    // Jackson 기본 설정에서 알 수 없는 필드는 JsonProcessingException을 발생시킨다
    assertThrows(JsonProcessingException.class, () -> mapper.readValue(json, User.class));
  }

  @Test
  void 필드가_없으면_기본값으로_채워진다() throws Exception {
    String json =
        """
        {"id":1}
        """;

    User user = mapper.readValue(json, User.class);

    assertEquals(1L, user.getId());
    assertEquals(null, user.getName()); // 없는 필드는 null로 설정된다
  }
}
