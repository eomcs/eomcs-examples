// # 아이템 8. finalizer와 cleaner 사용을 피하라
// - finalizer는 예측할 수 없고, 상황에 따라 위험할 수 있어 일반적으로 불필요하다.
// - cleaner는 finalizer보다는 덜 위험하지만, 여전히 예측할 수 없고, 느리고, 일반적으로 불필요하다.
// - C++의 소멸자(destructor)와는 다른 개념이다.
// - 즉시 수행된다는 보장이 없다.
// - 객체가 가비지가 된 후 언제 실행될지 알 수 없다.
// - 따라서, finalizer와 cleaner에 의존하는 자원 해제 코드는 위험하다.

package effectivejava.ch02.item8.exam05;

import java.lang.ref.Cleaner;

// AutoCloseable + Cleaner 안전망 예2: 안전망이 동작하는 상황을 확인한다.
class Room implements AutoCloseable {
  private static final Cleaner cleaner = Cleaner.create();

  // State 클래스:
  // - Cleaner의 데몬 스레드가 실행하는 Runnable 구현체이다.
  // - 청소가 필요한 자원을 둔다. 즉 가비지가 되었을 경우 해제시켜야 하는 자원이 있다면, 이 클래스에 둔다.
  // - 절대 Room 인스턴스를 참조해서는 안 된다.
  //   - State에서 Room을 참조할 경우 순환 참조가 생겨 가비지 컬렉션이 되지 않는다.
  //   - State 클래스가 정적 중첩 클래스(static nested class)인 이유이기도 하다.
  //   - non-static 중첩 클래스는 바깥 클래스의 인스턴스를 암묵적으로 참조하기 때문이다.
  //   - 람다 역시 바깥 클래스의 참조를 갖을 수 있기 때문에,
  //     Cleaner의 Runnable 구현체로 사용하지 않는 것이 좋다.
  //     무심코 바깥 클래스의 필드를 사용하는 순간 바깥 클래스의 인스턴스를 참조하는 순환 참조가 생긴다.
  // - 또한, Cleaner가 실행할 Runnable 객체인 State의 역할에 주목하자.
  //   - Room이 가비지가 되었을 때, 해제시켜야 하는 자원이 있다면 이 클래스에 둔다.
  private static class State implements Runnable {
    int numJunkPiles; // 방안의 쓰레기 수

    State(int numJunkPiles) {
      this.numJunkPiles = numJunkPiles;
    }

    // close() 메서드나 cleaner가 호출하는 메서드
    @Override
    public void run() {
      System.out.println("Cleaning room");
      numJunkPiles = 0;
    }
  }

  // 방의 상태. cleanable과 공유한다.
  private final State state;

  // cleanable 객체. 수거 대상이 되면 방을 청소한다.
  private final Cleaner.Cleanable cleanable;

  public Room(int numJunkPiles) {
    state = new State(numJunkPiles);
    cleanable = cleaner.register(this, state);
  }

  @Override
  public void close() {
    cleanable.clean();
  }
}

public class Test {

  public static void main(String[] args) throws Exception {

    // 자원 해제 1: AutoCloseable 이 동작하는 경우
    try (Room room = new Room(7)) {
      System.out.println("명시적으로 자원을 해제시킨다!");
    }

    System.out.println("------------------");

    // 자원 해제 2: Cleaner가 동작하는 경우
    new Room(99);
    System.out.println("명시적으로 자원을 해제시키지 않는다 하더라도!");

    // 가비지 컬렉터가 실행된다면,
    // GC 요청
    System.gc();

    // 잠깐 대기 (GC 동작 기회 부여)
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
    }

    // [결론]
    // - run() 메서드가 호출되는 경우,
    //   - 1) close() 메서드가 호출될 때
    //   - 2) Room 인스턴스가 가비지가 되어 cleaner가 실행될 때
    //
  }
}
