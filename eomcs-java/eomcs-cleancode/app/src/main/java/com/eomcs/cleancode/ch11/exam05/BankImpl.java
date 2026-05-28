package com.eomcs.cleancode.ch11.exam05;

import java.util.ArrayList;
import java.util.Collection;

// Spring 없이 순수 POJO — AspectJ LTW가 클래스 로드 시점에 바이트코드를 직접 위빙한다
public class BankImpl implements Bank {

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
