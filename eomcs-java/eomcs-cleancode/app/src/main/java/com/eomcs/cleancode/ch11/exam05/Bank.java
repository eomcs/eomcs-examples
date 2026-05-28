package com.eomcs.cleancode.ch11.exam05;

import java.util.Collection;

public interface Bank {
  Collection<Account> getAccounts();
  void setAccounts(Collection<Account> accounts);
}
