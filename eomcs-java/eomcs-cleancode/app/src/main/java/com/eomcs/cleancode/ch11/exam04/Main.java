package com.eomcs.cleancode.ch11.exam04;

import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

  public static void main(String[] args) {
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(AppConfig.class);

    Bank bank = context.getBean(Bank.class);

    bank.setAccounts(List.of(new Account("A-001")));
    bank.getAccounts();

    context.close();
  }
}
