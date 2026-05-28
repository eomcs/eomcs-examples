package com.eomcs.cleancode.ch11.exam04;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.stereotype.Service;

// 계좌 관리만 담당한다 — 로깅, 트랜잭션 같은 관심사는 없다
@Service
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
