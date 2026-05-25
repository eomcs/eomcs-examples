package com.eomcs.cleancode.ch05.exam01;

import java.util.List;

// 예제 4-1: 수직 거리 - 변수는 사용되는 곳 가까이에 선언하라 (Vertical Distance - Variables)
public class BadAndGood4 {

  private BadAndGood4() {}

  static class User {
    private boolean active;
    User(boolean active) { this.active = active; }
    boolean isActive() { return active; }
  }

  // Bad: 변수가 사용되는 위치보다 훨씬 일찍 선언되었다.
  // - title은 루프가 끝난 뒤에야 사용되는데 메서드 첫 줄에 선언되었다.
  // - 읽는 사람이 변수의 목적을 기억하며 코드를 따라가야 한다.
  static class BadReport {
    public void printReport(List<User> users) {
      int activeUserCount = 0;
      String title = "Active Users";

      for (User user : users) {
        if (user.isActive()) {
          activeUserCount++;
        }
      }

      System.out.println(title);
      System.out.println(activeUserCount);
    }
  }

  // Good: 변수는 사용되는 곳 바로 직전에 선언한다.
  // - activeUserCount 계산을 별도 메서드로 분리하고 title은 출력 직전에 선언한다.
  // - 더 나아가 title 변수 자체를 제거하여 인라인으로 처리한다.
  static class GoodReport {
    public void printReport(List<User> users) {
      System.out.println("Active Users");
      System.out.println(countActiveUsers(users));
    }

    private int countActiveUsers(List<User> users) {
      int count = 0;
      for (User user : users) {
        if (user.isActive()) {
          count++;
        }
      }
      return count;
    }
  }
}
