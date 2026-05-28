package com.eomcs.cleancode.ch11.exam03;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// 예제 2: JDK 동적 프록시로 영속성 관심사를 분리하라 - BankProxyHandler
public class BadAndGood2 {

  private BadAndGood2() {}

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

  // Bad: 비즈니스 로직과 영속성 코드가 섞여 있다
  //   - DB 정책이 바뀌면 BankImpl이 바뀐다
  //   - 테스트하기 어렵다
  //   - 여러 도메인 객체에 같은 코드가 반복될 수 있다
  static class BadBankImpl implements Bank {

    private Collection<Account> accounts = new ArrayList<>();

    @Override
    public Collection<Account> getAccounts() {
      System.out.println("load accounts from database"); // 영속성 코드
      return accounts;
    }

    @Override
    public void setAccounts(Collection<Account> accounts) {
      this.accounts = new ArrayList<>(accounts);
      System.out.println("save accounts to database"); // 영속성 코드
    }
  }

  // Good: BankImpl은 순수 비즈니스 객체로 남는다
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

  // Good: 프록시가 DB 처리 같은 부가 관심사를 담당한다
  //   - BankImpl에는 영속성 코드가 없다
  //   - 클라이언트는 Bank 인터페이스만 사용한다
  //   - 실제 객체와 프록시의 차이를 몰라도 된다
  static class BankProxyHandler implements InvocationHandler {

    private final Bank target;

    BankProxyHandler(Bank target) {
      this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

      if (method.getName().equals("getAccounts")) {
        System.out.println("load accounts from database");
        return method.invoke(target, args);
      }

      if (method.getName().equals("setAccounts")) {
        Object result = method.invoke(target, args);
        System.out.println("save accounts to database");
        return result;
      }

      return method.invoke(target, args);
    }
  }

  // 조립 예시
  //   Bank bank = (Bank) Proxy.newProxyInstance(
  //       Bank.class.getClassLoader(),
  //       new Class[] { Bank.class },
  //       new BankProxyHandler(new BankImpl())
  //   );
  //   bank.setAccounts(List.of(new Account("A-001")));
  //   Collection<Account> accounts = bank.getAccounts();

  @SuppressWarnings("unused")
  static Bank createProxy() {
    return (Bank) Proxy.newProxyInstance(
        Bank.class.getClassLoader(),
        new Class[] {Bank.class},
        new BankProxyHandler(new BankImpl())
    );
  }

  public static void main(String[] args) {
    Bank bank = createProxy();
    bank.setAccounts(List.of(new Account("A-001")));
    Collection<Account> accounts = bank.getAccounts();
    accounts.forEach(a -> System.out.println("account: " + a.id()));
  }
}
