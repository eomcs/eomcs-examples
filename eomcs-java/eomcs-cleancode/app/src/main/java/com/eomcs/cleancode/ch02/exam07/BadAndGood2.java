package com.eomcs.cleancode.ch02.exam07;

import java.util.List;

public class BadAndGood2 {

  // Bad
  // - l, x, x[0] 이 무엇인지 계속 추측해야 한다.
  void bad() {
    List<int[]> l = getData();

    for (int[] x : l) {
      if (x[0] == 1) {
        process(x);
      }
    }
  }

  // Good
  // - users, user, user.isActive() → 의미를 기억하지 않아도 코드가 설명된다.
  void good() {
    List<User> users = getUsers();

    for (User user : users) {
      if (user.isActive()) {
        process(user);
      }
    }
  }

  // ---- 컴파일을 위한 더미 클래스 및 메서드 ----
  class User {
    boolean isActive() {
      return true;
    }
  }

  List<int[]> getData() {
    return null;
  }

  List<User> getUsers() {
    return List.of();
  }

  void process(User user) {}

  void process(int[] x) {}
}
