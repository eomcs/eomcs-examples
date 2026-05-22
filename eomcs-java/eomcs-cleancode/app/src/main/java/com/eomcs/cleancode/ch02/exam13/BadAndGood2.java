package com.eomcs.cleancode.ch02.exam13;

import java.util.Comparator;
import java.util.List;

public class BadAndGood2 {

  static class User {
    String name;
    User(String name) { this.name = name; }
  }

  // Bad
  // - DataHandler → 무엇을 처리하는 클래스인지 알 수 없다.
  // - arrangeUsers → 정렬인지 필터링인지 구현 의도가 숨겨진다.
  static class BadDataHandler {
    List<User> arrangeUsers(List<User> users) {
      users.sort(Comparator.comparing(u -> u.name));
      return users;
    }
  }

  // Good
  // - UserSorter → 정렬 역할을 담당하는 클래스임을 즉시 알 수 있다.
  // - sortByName → sort라는 알고리즘 용어와 기준(name)까지 명확하다.
  static class GoodUserSorter {
    List<User> sortByName(List<User> users) {
      users.sort(Comparator.comparing(u -> u.name));
      return users;
    }
  }
}
