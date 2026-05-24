package com.eomcs.cleancode.ch03.exam01;

import java.util.List;

public class BadAndGood2 {

  static class User {
    private String name;
    private boolean active;
    private double balance;
    User(String name, boolean active, double balance) {
      this.name = name; this.active = active; this.balance = balance;
    }
    String getName() { return name; }
    boolean isActive() { return active; }
    double getBalance() { return balance; }
  }

  // Bad
  // - for → if → if → if 로 들여쓰기(depth)가 깊어 읽기 매우 어렵다.
  // - 블록 내부에 조건 로직과 출력 동작이 한 곳에 섞여 있다.
  // - 코드만 봐서는 "활성 사용자 중 잔액 있는 사용자 출력"이라는 의도를 바로 파악하기 어렵다.
  static class BadUserPrinter {
    void processUsers(List<User> users) {
      for (User user : users) {
        if (user != null) {
          if (user.isActive()) {
            if (user.getBalance() > 0) {
              System.out.println(user.getName() + " : " + user.getBalance());
            }
          }
        }
      }
    }
  }

  // Good
  // - 블록 내부는 한 줄(함수 호출 하나)만 존재해 들여쓰기가 얕다.
  // - 복잡한 조건은 isEligibleUser()로, 출력 동작은 printUserBalance()로 분리한다.
  // - 함수 이름만 읽어도 의도를 즉시 파악할 수 있다.
  static class GoodUserPrinter {
    void printEligibleUsers(List<User> users) {
      for (User user : users) {
        if (isEligibleUser(user)) {
          printUserBalance(user);
        }
      }
    }

    private boolean isEligibleUser(User user) {
      return user != null && user.isActive() && user.getBalance() > 0;
    }

    private void printUserBalance(User user) {
      System.out.println(user.getName() + " : " + user.getBalance());
    }
  }
}
