package com.eomcs.cleancode.ch03.exam02;

import java.util.ArrayList;
import java.util.List;

public class BadAndGood3 {

  static class User {
    private String name;
    private boolean active;
    User(String name, boolean active) { this.name = name; this.active = active; }
    String getName() { return name; }
    boolean isActive() { return active; }
  }

  // Bad
  // - 주석으로 단계(섹션)가 구분되어 있다 → 이미 여러 책임이 한 함수에 섞인 신호다.
  // - "주석은 코드를 분리하라는 힌트"다.
  static class BadReportGenerator {
    void generateReport(List<User> users) {
      // 1. 필터링
      List<User> activeUsers = new ArrayList<>();
      for (User user : users) {
        if (user.isActive()) {
          activeUsers.add(user);
        }
      }

      // 2. 정렬
      activeUsers.sort((a, b) -> a.getName().compareTo(b.getName()));

      // 3. 출력
      for (User user : activeUsers) {
        System.out.println(user.getName());
      }
    }
  }

  // Good
  // - 주석 없이도 함수 이름이 각 단계의 역할을 설명한다.
  // - generateReport()는 자연어처럼 읽히는 흐름만 표현한다.
  static class GoodReportGenerator {
    void generateReport(List<User> users) {
      List<User> activeUsers = filterActiveUsers(users);
      List<User> sortedUsers = sortUsersByName(activeUsers);
      printUsers(sortedUsers);
    }

    private List<User> filterActiveUsers(List<User> users) {
      List<User> result = new ArrayList<>();
      for (User user : users) {
        if (user.isActive()) {
          result.add(user);
        }
      }
      return result;
    }

    private List<User> sortUsersByName(List<User> users) {
      List<User> sorted = new ArrayList<>(users);
      sorted.sort((a, b) -> a.getName().compareTo(b.getName()));
      return sorted;
    }

    private void printUsers(List<User> users) {
      for (User user : users) {
        System.out.println(user.getName());
      }
    }
  }
}
