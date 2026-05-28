package com.eomcs.cleancode.ch11.exam05;

import java.util.List;

public class Main {

  public static void main(String[] args) {
    // Spring 컨텍스트 없이 직접 생성해도 LoggingAspect가 동작한다
    // aspectjweaver 에이전트가 BankImpl 바이트코드에 이미 로깅 코드를 엮었기 때문이다
    Bank bank = new BankImpl();

    bank.setAccounts(List.of(new Account("A-001")));
    bank.getAccounts();
  }
}
