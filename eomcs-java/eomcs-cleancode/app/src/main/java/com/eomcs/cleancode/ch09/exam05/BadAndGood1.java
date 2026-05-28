package com.eomcs.cleancode.ch09.exam05;

// 예제 1: F — Fast (빠르게 실행) - UserService
public class BadAndGood1 {

  private BadAndGood1() {}

  static class User {
    private final String name;

    User(String name) {
      this.name = name;
    }

    String getName() { return name; }
  }

  interface UserRepository {
    User findById(long id);
  }

  // Bad 테스트에서 사용 - 외부 DB 호출을 흉내 낸 느린 구현체
  static class SlowUserRepository implements UserRepository {
    @Override
    public User findById(long id) {
      return new User("kim");
    }
  }

  // Good 테스트에서 사용 - 빠른 인메모리 가짜 구현체
  static class FakeUserRepository implements UserRepository {
    @Override
    public User findById(long id) {
      return new User("kim");
    }
  }

  static class UserService {
    private final UserRepository repository;

    UserService(UserRepository repository) {
      this.repository = repository;
    }

    User findUser(long id) {
      return repository.findById(id);
    }
  }
}
