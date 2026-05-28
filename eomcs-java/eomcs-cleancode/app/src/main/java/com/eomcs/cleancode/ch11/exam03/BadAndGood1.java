package com.eomcs.cleancode.ch11.exam03;

import java.util.ArrayList;
import java.util.Collection;

// 예제 1: POJO로 단순하게 유지되는 도메인 객체 - Bank / BankImpl
public class BadAndGood1 {

  private BadAndGood1() {}

  static class Account {

    private final String id;

    Account(String id) {
      this.id = id;
    }

    String id() { return id; }
  }

  // 핵심 인터페이스
  interface Bank {
    Collection<Account> getAccounts();
    void setAccounts(Collection<Account> accounts);
  }

  // Good: BankImpl은 계좌 목록 관리만 담당한다
  //   - DB 저장, 로깅, 트랜잭션 코드가 없다
  //   - 도메인 객체가 단순하다
  //   - 단독으로 테스트하기 쉽다
  static class BankImpl implements Bank {

    private Collection<Account> accounts = new ArrayList<>();

    @Override
    public Collection<Account> getAccounts() {
      return accounts;
    }

    @Override
    public void setAccounts(Collection<Account> accounts) {
      this.accounts = new ArrayList<>(accounts);
    }
  }
}
