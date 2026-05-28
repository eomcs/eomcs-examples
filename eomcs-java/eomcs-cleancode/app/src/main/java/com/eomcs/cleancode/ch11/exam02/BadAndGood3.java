package com.eomcs.cleancode.ch11.exam02;

import java.util.HashMap;
import java.util.Map;

// 예제 3: 횡단 관심사 - UserService / CachedUserReader (캐시 분리)
public class BadAndGood3 {

  private BadAndGood3() {}

  static class User {

    private final long id;
    private final String name;

    User(long id, String name) {
      this.id = id;
      this.name = name;
    }

    long id() { return id; }
    String name() { return name; }
  }

  interface UserRepository {
    User findById(long id);
  }

  interface Cache {
    User get(String key);
    void put(String key, User user);
  }

  // 테스트/예제용 간단한 캐시 구현
  static class InMemoryCache implements Cache {

    private final Map<String, User> store = new HashMap<>();

    @Override
    public User get(String key) {
      return store.get(key);
    }

    @Override
    public void put(String key, User user) {
      store.put(key, user);
    }
  }

  // Bad: 사용자 조회 로직과 캐싱 로직이 섞여 있다
  //   - 다른 조회 메서드에도 캐싱 코드가 반복된다
  //   - 캐시 키 정책이 바뀌면 여러 서비스가 영향을 받는다
  //   - 캐시를 제거하거나 교체하려면 서비스 코드를 수정해야 한다
  static class BadUserService {

    private final UserRepository repository;
    private final Cache cache;

    BadUserService(UserRepository repository, Cache cache) {
      this.repository = repository;
      this.cache = cache;
    }

    public User findById(long id) {
      String key = "user:" + id;

      User cached = cache.get(key); // 캐싱 로직
      if (cached != null) {
        return cached;
      }

      User user = repository.findById(id); // 조회 로직
      cache.put(key, user);               // 캐싱 로직

      return user;
    }
  }

  // Good: DB 조회와 캐싱이 분리된다
  //   - 핵심 조회 로직은 캐시 정책을 모른다
  //   - 캐싱을 제거하거나 추가하기 쉽다
  //   - 횡단 관심사를 별도 객체로 감쌌다

  interface UserReader {
    User findById(long id);
  }

  // Good: DB 조회만 담당한다
  static class DatabaseUserReader implements UserReader {

    private final UserRepository repository;

    DatabaseUserReader(UserRepository repository) {
      this.repository = repository;
    }

    @Override
    public User findById(long id) {
      return repository.findById(id);
    }
  }

  // Good: 캐싱만 담당하는 데코레이터
  //   - 조합으로 캐싱 여부를 결정한다
  //   - CachedUserReader(new DatabaseUserReader(repository), cache)
  static class CachedUserReader implements UserReader {

    private final UserReader userReader;
    private final Cache cache;

    CachedUserReader(UserReader userReader, Cache cache) {
      this.userReader = userReader;
      this.cache = cache;
    }

    @Override
    public User findById(long id) {
      String key = "user:" + id;

      User cached = cache.get(key);
      if (cached != null) {
        return cached;
      }

      User user = userReader.findById(id);
      cache.put(key, user);

      return user;
    }
  }

  // 조립 예시
  //   UserReader userReader =
  //       new CachedUserReader(
  //           new DatabaseUserReader(repository),
  //           cache
  //       );
  //   User user = userReader.findById(1L);
}
