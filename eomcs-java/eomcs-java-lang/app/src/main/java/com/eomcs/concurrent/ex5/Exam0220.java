// 멀티 스레딩(비동기 프로그래밍)의 문제점 - 사례 2의 문제점 해결
package com.eomcs.concurrent.ex5;

public class Exam0220 {

  static class Account {
    String accountId;
    long balance;

    public Account(String accountId, long balance) {
      this.accountId = accountId;
      this.balance = balance;
    }

    // 한 번에 한 스레드 만이 호출하도록 접근을 제한하고 싶다면
    // 메서드 전체를 동기화 블록으로 선언하라!
    // 어떻게? 
    // 메서드 앞에 synchronized를 붙인다.
    // => 여러 스레드가 동시에 접근했을 때 문제가 발생하지 않는 코드에 대해 
    //    synchronized 사용한다면 실행 속도가 떨어질 것이다.
    // => 왜? 
    //    한 번에 한 스레드만 순차적으로 접근하기 때문이다.
    //
    // 이렇게 크리티컬 섹션에 동시에 접근하지 못하게 하는 기법
    // => "뮤텍스(mutex)" 또는 "세마포어(1)(semaphore)"라 부른다.
    // 
    // 자바에서 뮤텍스를 구현하는 방법,
    // => 크리티컬 섹션에 해당하는 메서드나 코드 블록에 
    //    sychronized 키워드를 붙여
    //    한 번에 한 스레드만 진입할 수 있도록 lock을 건다.
    // 
    // 참고!
    // => 여러 스레드가 동시에 실행해도 문제가 없는 코드 블록을
    //    "스레드 안전(thread safe)"라 부른다.
    //
    //
    synchronized public long withdraw(long money) {
      long b = this.balance;

      delay();  // CPU를 뺏길 기회를 제공

      b -= money;

      delay();  // CPU를 뺏길 기회를 제공

      if (b < 0)
        return 0;

      delay();  // CPU를 뺏길 기회를 제공

      this.balance = b;

      delay();  // CPU를 뺏길 기회를 제공

      return money;
    }

    private void delay() {
      int delayCount = (int)(Math.random() * 1000);
      for (int i = 0; i < delayCount; i++)
        Math.asin(45.765); // CPU를 뺏길 기회를 제공
    }
  }

  static class ATM extends Thread {
    Account account;

    public ATM(String name, Account account) {
      super(name);
      this.account = account;
    }

    @Override
    public void run() {
      long money = 0;
      long sum = 0;

      while (true) {
        money = account.withdraw(100);
        if (money <= 0)
          break;
        sum += money;
      }
      System.out.printf("%s에서 찾은 돈: %d원\n", this.getName(), sum);
    }
  }

  public static void main(String[] args) {
    Account account = new Account("111-11-1111-111", 100_0000);

    ATM 강남 = new ATM("강남", account);
    ATM 서초 = new ATM("서초", account);
    ATM 부산 = new ATM("부산", account);
    ATM 대전 = new ATM("대전", account);
    ATM 광주 = new ATM("광주", account);

    강남.start();
    서초.start();
    부산.start();
    대전.start();
    광주.start();

    // 비동기 문제 해결책?
    // => 한 번에 한 스레드 만이 크리티컬 섹션을 실행하도록 접근을 제한하면 된다.
    // 
    // 주의!
    // => 동시에 여러 스레드가 같은 메모리에 접근하더라도 
    //    값을 변경하는 것이 아니라 단순히 값을 조회하는 경우에는 
    //    멀티 스레드 문제가 발생하지 않는다.
    //
    // 세마포어(n); semaphore
    // => 크리티컬 섹션에 진입할 수 있는 스레드의 수를 지정한다.
    // => 자바에서는 세마포어를 지원하지 않는다.
    // => 개발자가 직접 구현해야 한다.
    // => 생활 예)
    //    - 화장실에 들어가는 사람들의 수
    //    - 영화 관람권 예매
    //    - 기차 좌석 예매
    //
    // 뮤텍스; mutex(mutual exclusion, 상호배제)
    // => 한 번에 오직 한 개의 스레드만이 크리티컬 섹션에 접근할 수 있다.
    // => 생활 예) 
    //    - 선풍기 풍량세기 선택
    //    - 라디오 채널 및 TV 채널 선택
    // => semaphore(1)과 같다.
    // => 자바는 synchronized 키워드를 통해 뮤텍스를 사용할 수 있다.
  }
}


// 용어정리!
// 스레드 안전(thread safe)
// - 여러 스레드가 동시에 실행하더라도 아무런 문제가 되지 않는 코드를 말한다.
// - 여러 스레드가 같은 메모리에 접근하더라도 
//   읽기만 한다면 아무런 문제가 되지 않는다.
//
// 세마포어(semaphore)
// - critical section에 접근하는 스레드의 수를 제어하는 기법
// - 보통 다음의 형식으로 표시한다.
//          semaphore(n)
//   n은 개수를 의미한다.
//   예) semaphore(3) : 동시에 3개의 스레드가 접근할 수 있다는 의미다.
// - 자바는 2개 이상의 접근을 허용하지 않는다. 오직 한 개만 가능하다.
//
// 뮤텍스(mutual exclusion; MUTEX)
// - critical section에 오직 한 개의 스레드만이 접근하는 것.
// - semaphore(1) 과 같다.
// - 자바에서 synchronized 는 해당 블록(critical section)을 
//   뮤텍스로 선언한다.


