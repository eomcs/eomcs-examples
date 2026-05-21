package com.eomcs.cleancode.ch02.exam08;

public class BadAndGood3 {

  // Bad
  // - Data, Info, Object 는 의미 없는 단어 → 클래스 역할을 전혀 표현하지 못한다.
  // - 세 클래스의 차이를 이름만으로 구분할 수 없다.
  static class AccountData {
    int id;
    String ownerName;
    double balance;
  }

  static class AccountInfo {
    int id;
    String ownerName;
    double balance;
  }

  static class AccountObject {
    int id;
    String ownerName;
    double balance;
  }

  // Good
  // - Account → 계좌 그 자체를 나타내는 핵심 도메인 클래스.
  // - AccountSummary → 잔액과 소유자 같은 요약 정보만 담는다.
  // - AccountDetails → 거래 내역을 포함한 상세 정보를 제공한다.
  // - 실제 의미를 반영하고 클래스 간 차이가 명확하다.
  static class Account {
    int id;
    String ownerName;
    double balance;
  }

  static class AccountSummary {
    int id;
    String ownerName;
    double balance;

    String toSummaryLine() {
      return String.format("[%d] %s: %.2f", id, ownerName, balance);
    }
  }

  static class AccountDetails {
    int id;
    String ownerName;
    double balance;
    String[] recentTransactions;

    void printTransactions() {
      for (String tx : recentTransactions) {
        System.out.println(tx);
      }
    }
  }
}
