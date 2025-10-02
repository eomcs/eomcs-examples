// # 아이템 8. finalizer와 cleaner 사용을 피하라
// - finalizer는 예측할 수 없고, 상황에 따라 위험할 수 있어 일반적으로 불필요하다.
// - cleaner는 finalizer보다는 덜 위험하지만, 여전히 예측할 수 없고, 느리고, 일반적으로 불필요하다.
// - C++의 소멸자(destructor)와는 다른 개념이다.
// - 즉시 수행된다는 보장이 없다.
// - 객체가 가비지가 된 후 언제 실행될지 알 수 없다.
// - 따라서, finalizer와 cleaner에 의존하는 자원 해제 코드는 위험하다.

package effectivejava.ch02.item8.exam04;

import java.lang.ref.Cleaner;

// AutoCloseable + 안전망(Cleaner 사용) 예:
class MyResource implements AutoCloseable {

  private static final Cleaner cleaner = Cleaner.create();
  private final Cleaner.Cleanable cleanable;

  private boolean closed = false;

  // Cleaner 의 데몬 스레드가 호출할 자원 해제 작업을 정의하는 Runnable
  private static class ResourceCleaner implements Runnable {
    @Override
    public void run() {
      System.err.println("Cleaner: 자원 해제!");
      // 자원 해제 코드를 여기에 넣는다.
    }
  }

  public MyResource() {
    cleanable = cleaner.register(this, new ResourceCleaner());
  }

  public void doSomething() {
    if (closed) {
      // 자원이 해제된 이후에 이 메서드를 호출할 경우를 대비해 예외를 던지게 한다.
      throw new IllegalStateException("이미 close() 호출됨!");
    }
    System.out.println("doSomething()");
  }

  @Override
  public void close() {
    if (!closed) {
      // 자원이 해제 되었음을 표시하기 위해 플래그를 설정한다.
      closed = true;
      System.out.println("close()");

      // Cleaner에서 등록 해제 및 자원 해제
      cleanable.clean();
      // clean()을 호출하는 즉시 Runnable의 run() 메서드가 즉시 실행된다.
      // "멱등성 보장"으로 인해 자원 해제 코드가 여러 번 실행되지 않는다.
      // 왜? Runnable은 오직 한 번만 실행되기 때문이다.
    }
  }
}

public class Test {

  public static void main(String[] args) throws Exception {

    try (MyResource myResource = new MyResource()) {
      myResource.doSomething();
    }

    // [결론]
    // - AutoCloseable 인터페이스를 구현하고, try-with-resources 문을 사용하여 자원을 관리하자.
    // - close()가 호출되지 않는 상황에 대비한 안전망 차원에서 cleaner를 구현할 수는 있다.
    // - cleaner를 구현 할 때,
    //   - 안전망으로서 값어치가 있을 때
    //   - 중요하지 않은 네이티브 자원을 회수할 때
    // - 무조건 cleaner를 구현한다면, 코드의 복잡성만 증가할 뿐이다.
  }
}
