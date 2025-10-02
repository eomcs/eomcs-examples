// # 아이템 8. finalizer와 cleaner 사용을 피하라
// - finalizer는 예측할 수 없고, 상황에 따라 위험할 수 있어 일반적으로 불필요하다.
// - cleaner는 finalizer보다는 덜 위험하지만, 여전히 예측할 수 없고, 느리고, 일반적으로 불필요하다.
// - C++의 소멸자(destructor)와는 다른 개념이다.
// - 즉시 수행된다는 보장이 없다.
// - 객체가 가비지가 된 후 언제 실행될지 알 수 없다.
// - 따라서, finalizer와 cleaner에 의존하는 자원 해제 코드는 위험하다.

package effectivejava.ch02.item8.exam01;

// finalizer 사용 예:
class MyResource {

  public void doSomething() {
    System.out.println("doSomething()");
  }

  // finalize() 메서드는 가비지 컬렉터가 객체를 회수하기 직전에 호출된다.
  // - JVM을 종료하기 전에 가비지 컬렉터가 실행되지 않을 수 있다.
  // - 따라서 finalize() 메서드에 자원 해제 코드를 넣는 것은 위험하다.
  // - 또한, 가비지 컬렉터가 객체를 회수하는 시점을 예측할 수 없다.
  // - 즉시 수행된다는 보장이 없다.
  // - Java 9부터 deprecated 되었다.
  @Override
  protected void finalize() throws Throwable {
    // 자원을 해제한다.
    System.out.println("자원을 해제하였다!");
  }
}

public class Test {

  public static void main(String[] args) throws Exception {

    MyResource resource = new MyResource();
    resource.doSomething();

    resource = null; // 더 이상 사용하지 않는다는 표시

    // GC 요청
    System.gc();

    // 잠깐 대기 (GC 동작 기회 부여)
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
    }

    // finalize() 메서드가 실행되었는지 확인한다.

    // [결론]
    // - GC가 어떤 객체를 가비지라고 판단하면,
    //   - 1) 그 객체를 finalizer 큐에 넣는다.
    //   - 2) finalizer 스레드가 큐에서 객체를 꺼내어 finalize() 메서드를 호출한다.
    //   - 3) GC는 객체를 Heap 메모리에서 해제한다.
    // - 문제는 finalizer 스레드의 우선 순위가 낮아서 실행될 기회를 얻지 못할 수 있다.
    // - 즉 JVM을 종료하기 전에 finalize() 메서드가 실행되지 않을 수 있다.
    // - finalize() 메서드가 실행되지 않으면, JVM 종료 후에도 자원이 해제되지 않는다.
    // - 이런 이유로 finalize() 메서드에 자원 해제 코드를 넣는 것은 위험하다.
  }
}
