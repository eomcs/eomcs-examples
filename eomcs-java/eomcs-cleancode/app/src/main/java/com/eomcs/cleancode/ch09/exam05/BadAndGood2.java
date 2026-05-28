package com.eomcs.cleancode.ch09.exam05;

import java.util.HashMap;
import java.util.Map;

// 예제 2: I — Independent (독립적) - UserService
public class BadAndGood2 {

  private BadAndGood2() {}

  static class User {
    private final String name;

    User(String name) {
      this.name = name;
    }

    String getName() { return name; }
  }

  static class InMemoryUserRepository {
    private final Map<String, User> store = new HashMap<>();

    void save(User user) {
      store.put(user.getName(), user);
    }

    User findByName(String name) {
      return store.get(name);
    }
  }

  static class UserService {
    private final InMemoryUserRepository repository;

    UserService(InMemoryUserRepository repository) {
      this.repository = repository;
    }

    void create(String name) {
      repository.save(new User(name));
    }

    User find(String name) {
      return repository.findByName(name);
    }
  }
}
