package com.eomcs.cleancode.ch02.exam13;

import java.util.HashMap;
import java.util.Map;

public class BadAndGood4 {

  static class User {
    String id;
    User(String id) { this.id = id; }
  }

  // Bad
  // - DataHelper → Helper는 의미 없는 단어. 역할이 전혀 드러나지 않는다.
  // - process → JSON을 파싱하는지, 변환하는지 내부를 봐야만 알 수 있다.
  static class BadDataHelper {
    Object process(String input) {
      return input;
    }
  }

  // Good
  // - JsonParser → Parser라는 CS 용어로 파싱 역할이 명확하다.
  // - UserCache → Cache라는 CS 용어로 캐시 역할이 명확하다.
  // - 이름만 보고 각 클래스의 구조와 동작을 예측할 수 있다.
  static class GoodJsonParser {
    Object parse(String json) {
      return json;
    }
  }

  static class GoodUserCache {
    private Map<String, User> cache = new HashMap<>();

    User get(String userId) {
      return cache.get(userId);
    }
    void put(String userId, User user) {
      cache.put(userId, user);
    }
  }
}
