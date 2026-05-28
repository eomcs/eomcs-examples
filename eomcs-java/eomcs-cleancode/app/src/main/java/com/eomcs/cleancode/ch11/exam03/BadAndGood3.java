package com.eomcs.cleancode.ch11.exam03;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// 예제 3: 범용 로깅 프록시로 횡단 관심사를 분리하라 - LoggingProxyHandler
public class BadAndGood3 {

  private BadAndGood3() {}

  static class Account {

    private final String id;

    Account(String id) {
      this.id = id;
    }

    String id() { return id; }
  }

  interface Bank {
    Collection<Account> getAccounts();
    void setAccounts(Collection<Account> accounts);
  }

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

  // Good: 모든 메서드 호출 전후에 로깅을 넣는다
  //   - BankImpl에는 로깅 코드가 없다
  //   - 어떤 인터페이스에도 적용 가능한 범용 핸들러다
  //   - 횡단 관심사를 프록시로 분리했다
  static class LoggingProxyHandler implements InvocationHandler {

    private final Object target;

    LoggingProxyHandler(Object target) {
      this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      System.out.println("before: " + method.getName());
      Object result = method.invoke(target, args);
      System.out.println("after: " + method.getName());
      return result;
    }
  }

  // 조립 예시
  //   Bank bank = (Bank) Proxy.newProxyInstance(
  //       Bank.class.getClassLoader(),
  //       new Class[] { Bank.class },
  //       new LoggingProxyHandler(new BankImpl())
  //   );
  //   bank.setAccounts(List.of(new Account("A-001")));
  //   Collection<Account> accounts = bank.getAccounts();

  public static void main(String[] args) {
    Bank bank = (Bank) Proxy.newProxyInstance(
        Bank.class.getClassLoader(),
        new Class[] {Bank.class},
        new LoggingProxyHandler(new BankImpl())
    );

    bank.setAccounts(List.of(new Account("A-001")));
    Collection<Account> accounts = bank.getAccounts();
    accounts.forEach(a -> System.out.println("account: " + a.id()));
  }
}
