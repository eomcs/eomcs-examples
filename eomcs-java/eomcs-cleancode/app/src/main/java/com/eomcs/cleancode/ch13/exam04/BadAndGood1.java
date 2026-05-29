package com.eomcs.cleancode.ch13.exam04;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// 예제 1: 공유 Map - HashMap은 thread-safe하지 않다
public class BadAndGood1 {

  private BadAndGood1() {}

  static class UserSession {

    private final String userId;

    UserSession(String userId) {
      this.userId = userId;
    }

    String getUserId() {
      return userId;
    }
  }

  // Bad: HashMap은 thread-safe하지 않다
  //   - 여러 요청 스레드가 동시에 put, get, remove를 호출하면 문제가 생길 수 있다
  //   - 데이터가 깨지거나 예측하기 어려운 버그가 발생할 수 있다
  static class BadUserSessionStore {

    private final Map<String, UserSession> sessions = new HashMap<>();

    public void add(String token, UserSession session) {
      sessions.put(token, session);
    }

    public UserSession find(String token) {
      return sessions.get(token);
    }

    public void remove(String token) {
      sessions.remove(token);
    }
  }

  // Good: ConcurrentHashMap은 멀티스레드 환경에 맞게 설계되어 있다
  //   - 여러 스레드가 동시에 읽고 쓸 수 있다
  //   - 직접 synchronized를 붙이는 것보다 의도가 명확하다
  static class UserSessionStore {

    private final ConcurrentMap<String, UserSession> sessions = new ConcurrentHashMap<>();

    public void add(String token, UserSession session) {
      sessions.put(token, session);
    }

    public UserSession find(String token) {
      return sessions.get(token);
    }

    public void remove(String token) {
      sessions.remove(token);
    }
  }
}
