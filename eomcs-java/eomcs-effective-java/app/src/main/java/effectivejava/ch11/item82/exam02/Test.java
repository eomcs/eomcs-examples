// # 아이템 82. 스레드 안전성 수준을 문서화하라
// - 모든 클래스가 자신의 스레드 안전성 정보를 명확히 문서화해야 한다.
// - 정확한 언어로 명확히 설명하거나 스레드 안전성 애너테이션을 사용할 수 있다.
// - synchronized 한정자는 문서화와 관련이 없다.
//   메서드 선언에 synchronized 한정자를 선언할지는 구현 이슈일 뿐 API에 속하지 않는다.
// - 조건부 스레드 안전 클래스는 메서드를 어떤 순서로 호출할 때 외부 동기화가 요구되고,
//   그때 어떤 락을 얻어야 하는지도 알려줘야 한다.
// - 무조건적인 스레드 안전 클래스를 작성할 때는 synchronized 메서드가 아닌
//   비공개 lock 객체를 사용하라.
//   이렇게 해야 클라이언트나 하위 클래스에서 공기화 메커니즘을 깨뜨리는 걸 예방할 수 있고,
//   필요하다면 다음에 더 정교한 동시성을 제어 메커니즘으로 재구현할 여지가 생긴다.
// - 멀티스레드 환경에서도 API를 안전하게 사용하게 하려면
//   클래스가 지원하는 스레드 안정성 수준을 정확히 명시해야 한다.

package effectivejava.ch11.item82.exam02;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

// [주제] JSR-305 애너테이션 - GuardedBy 사용법

// @GuardedBy의 사용 예
class GuardedByExample {

  @GuardedBy("this")
  private int count1; // this로 보호

  private final Object lock = new Object();

  @GuardedBy("lock")
  private int count2; // lock 객체로 보호

  @GuardedBy("BankAccount.class")
  private static int staticCount; // 클래스 락으로 보호
}

// @GuardedBy의 사용 예 II
@ThreadSafe
class BankAccount {
  private final Object balanceLock = new Object();

  @GuardedBy("balanceLock") // balanceLock으로 보호됨을 명시
  private long balance;

  public void deposit(long amount) {
    synchronized (balanceLock) {
      balance += amount;
    }
  }

  public void withdraw(long amount) {
    synchronized (balanceLock) {
      balance -= amount;
    }
  }

  // 잘못된 예 - 경고!
  public long getBalance() {
    return balance; // balanceLock 없이 접근 - 위험!
  }
}

public class Test {
  public static void main(String[] args) {
    BankAccount account = new BankAccount();
    account.deposit(1000);
    account.withdraw(500);
    System.out.println("잔액: " + account.getBalance());
  }
}
