package com.eomcs.cleancode.ch08.exam04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

// Jackson 학습 테스트: 라이브러리 동작을 기록하고 업그레이드 회귀를 방지한다.
//
// 이 테스트는 두 가지 역할을 한다:
//   1. Jackson API 사용법을 탐색하고 문서화한다.
//   2. Jackson 버전을 올렸을 때 기존 동작이 유지되는지 확인한다.
//      → 테스트가 실패하면 업그레이드로 인한 동작 변경을 즉시 발견할 수 있다.
class JacksonLearningTest {

  static class User {
    private long id;
    private String name;

    User() {}
    User(long id, String name) { this.id = id; this.name = name; }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
  }

  private final ObjectMapper mapper = new ObjectMapper();

  // 예제 1 - README 핵심 예제:
  // Jackson 기본 설정에서 알 수 없는 필드가 있으면 예외가 발생한다.
  // → 버전 업그레이드 후 이 동작이 바뀌면 테스트 실패로 즉시 알 수 있다.
  @Test
  void unknownField가_있으면_예외가_발생한다() {
    String json = """
        {"id":1,"name":"Kim","unknown":"value"}
        """;

    assertThrows(JsonProcessingException.class, () ->
        mapper.readValue(json, User.class)
    );
  }

  // json을 객체로 변환하는 기본 동작을 기록한다.
  // → 버전 업그레이드 후 필드 매핑 규칙이 바뀌면 이 테스트가 실패한다.
  @Test
  void json을_User로_변환한다() throws Exception {
    String json = """
        {"id":1,"name":"Kim"}
        """;

    User user = mapper.readValue(json, User.class);

    assertEquals(1L, user.getId());
    assertEquals("Kim", user.getName());
  }

  // 객체를 json으로 변환하는 기본 동작을 기록한다.
  // → 버전 업그레이드 후 직렬화 형식이 바뀌면 이 테스트가 실패한다.
  @Test
  void User를_json으로_변환한다() throws Exception {
    User user = new User(1L, "Kim");

    String json = mapper.writeValueAsString(user);

    assertEquals("{\"id\":1,\"name\":\"Kim\"}", json);
  }

  // json에 없는 필드는 기본값(null 또는 0)으로 채워진다.
  // → 버전 업그레이드 후 기본값 처리 방식이 바뀌면 이 테스트가 실패한다.
  @Test
  void json에_없는_필드는_기본값으로_채워진다() throws Exception {
    String json = """
        {"id":1}
        """;

    User user = mapper.readValue(json, User.class);

    assertEquals(1L, user.getId());
    assertNull(user.getName()); // name 필드가 없으면 null이 된다
  }

  // json이 빈 객체이면 모든 필드가 기본값으로 채워진다.
  // → 버전 업그레이드 후 이 동작이 바뀌면 이 테스트가 실패한다.
  @Test
  void 빈_json_객체는_기본값으로_채워진다() throws Exception {
    String json = "{}";

    User user = mapper.readValue(json, User.class);

    assertNotNull(user);
    assertEquals(0L, user.getId());
    assertNull(user.getName());
  }
}
