package com.eomcs.cleancode.ch11.exam04;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// Spring AOP가 Bank 메서드 호출을 가로채 로깅 어드바이스를 적용하는지 검증한다.
class BankTest {

  // 캡처 버퍼와 원본 콘솔 양쪽에 동시에 쓰는 스트림
  static class TeeOutputStream extends OutputStream {

    private final OutputStream first;
    private final OutputStream second;

    TeeOutputStream(OutputStream first, OutputStream second) {
      this.first = first;
      this.second = second;
    }

    @Override
    public void write(int b) throws IOException {
      first.write(b);
      second.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      first.write(b, off, len);
      second.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
      first.flush();
      second.flush();
    }
  }

  AnnotationConfigApplicationContext context;
  Bank bank;
  ByteArrayOutputStream out;
  PrintStream originalOut;

  @BeforeEach
  void setUp() {
    context = new AnnotationConfigApplicationContext(AppConfig.class);
    bank = context.getBean(Bank.class);

    out = new ByteArrayOutputStream();
    originalOut = System.out;
    System.setOut(new PrintStream(new TeeOutputStream(originalOut, out)));
  }

  @AfterEach
  void tearDown() {
    System.setOut(originalOut);
    context.close();
  }

  @Test
  void setAccounts_호출_전후에_로그가_출력된다() {
    bank.setAccounts(List.of(new Account("A-001")));

    String log = out.toString();
    assertTrue(log.contains("before: setAccounts"));
    assertTrue(log.contains("after: setAccounts"));
  }

  @Test
  void getAccounts_호출_전후에_로그가_출력된다() {
    bank.getAccounts();

    String log = out.toString();
    assertTrue(log.contains("before: getAccounts"));
    assertTrue(log.contains("after: getAccounts"));
  }

  @Test
  void setAccounts로_저장한_계좌를_getAccounts로_조회할_수_있다() {
    bank.setAccounts(List.of(new Account("A-001"), new Account("A-002")));

    Collection<Account> accounts = bank.getAccounts();

    assertEquals(2, accounts.size());
  }
}
