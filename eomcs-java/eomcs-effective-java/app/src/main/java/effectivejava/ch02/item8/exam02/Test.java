// # 아이템 8. finalizer와 cleaner 사용을 피하라
// - finalizer는 예측할 수 없고, 상황에 따라 위험할 수 있어 일반적으로 불필요하다.
// - cleaner는 finalizer보다는 덜 위험하지만, 여전히 예측할 수 없고, 느리고, 일반적으로 불필요하다.
// - C++의 소멸자(destructor)와는 다른 개념이다.
// - 즉시 수행된다는 보장이 없다.
// - 객체가 가비지가 된 후 언제 실행될지 알 수 없다.
// - 따라서, finalizer와 cleaner에 의존하는 자원 해제 코드는 위험하다.

package effectivejava.ch02.item8.exam02;

import java.lang.ref.Cleaner;

// cleaner 사용 예:
class MyResource {

  // Cleaner:
  // - 자바에서 객체가 더 이상 참조되지 않을 때,
  //   자동으로 정리(cleanup) 작업을 수행할 수 있도록 도와주는 유틸리티이다.
  // - 자원 해제 작업을 등록하고 관리하는 데 사용한다.
  // - 내부적으로 cleanup 작업을 실행할 하나의 전용 데몬 스레드를 실행한다.
  private static final Cleaner cleaner = Cleaner.create();

  //  Cleaner에 등록된 정리 작업(Runnable)을 관리하기 위한 참조
  // - 등록된 cleanup 작업을 나중에 수동으로 실행할 수 있도록 참조를 저장하는 필드이다.
  private final Cleaner.Cleanable cleanable;

  // 자원 해제 작업을 정의하는 Runnable
  // - Cleaner의 데몬 스레드는 ReferenceQueue를 감시하다가,
  //   가비지가 되면 이 Runnable의 run() 메서드를 호출한다.
  private static class ResourceCleaner implements Runnable {
    @Override
    public void run() {
      System.err.println("Cleaner: 자원 해제!");
      // 자원 해제 코드를 여기에 넣는다.
      // 예: 파일 닫기, 네트워크 연결 해제, 메모리 해제 등
    }
  }

  // 생성자에서
  public MyResource() {
    // Cleaner에 자원 해제 일을 할 객체를 등록한다.
    // 리턴 값은 "등록한 자원 해제 객체(ResourceCleaner)"를 실행시킬 때 사용할 참조이다.
    // 리턴된 참조를 이용하여 수동으로 자원 해제 객체를 실행시킬 수 있다.
    cleanable = cleaner.register(this, new ResourceCleaner());
  }

  public void doSomething() {
    System.out.println("doSomething()");
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

    // 스레드가 Runnable 객체인 ResourceCleaner을 실행했는지 확인한다.

    // [결론]
    // - Cleaner의 데몬 스레드 또한 백그라운드에서 수행되며
    //   가비지 컬렉터의 통제하에 있기 때문에
    //   언제 실행될지 예측할 수 없고, 즉시 수행된다는 보장이 없다.
    // - 그래도 finalizer 대기열에서 스레드가 호출해 주기를 기다리는 상황보다는 낫다.
    // - finalize() 대체 수단으로 Java 9부터 도입되었다.
    //

    // Cleaner가 Finalizer보다는 낫다고 평가하는 이유?
    //
    // [Finalizer 방식의 문제점]
    // - 비결정적 실행 시점
    //   - GC가 객체를 “수거 가능”으로 표시한 뒤에만 finalize()가 호출된다.
    //   - 언제 실행될지 보장되지 않고, 심지어 아예 실행되지 않을 수도 있다.
    // - 성능 문제
    //   - Finalizer 큐를 처리하는 단일 스레드가 막히면 다른 객체들의 finalize() 실행도 지연된다.
    //   - 따라서 대기열이 쌓여 메모리 누수로 이어질 수 있다.
    // - 보안 문제
    //   - finalize() 안에서 객체를 다시 다른 곳에 등록해 “부활(resurrection)”시킬 수 있다.
    //     - finalize() 메서드 안에서 this의 값을 다른 전역 변수에 할당해 버리면 해당 객체는 부활하는 것이다.
    //   - 이러면 “이미 소멸 예정인 객체”가 다시 살아나 예측 불가능한 동작을 유발한다.
    // - GC 부하 증가
    //   - Finalizer가 붙은 객체는 두 번 GC 사이클을 거쳐야 회수된다.
    //     - 1차: 수거 대상 표시 → finalize 큐 등록,
    //     - 2차: finalize 실행 후 다시 참조 없으면 실제 수거
    //
    // [Cleaner 방식의 장점]
    // - 명시적·안전한 API
    //   - Cleaner.register(obj, runnable) 형태로 객체와 정리 작업을 연결한다.
    //   - 실행되는 것은 사용자가 등록한 별도 Runnable이며, 객체 자체의 메서드를 다시 실행하는 게 아님 → 객체 “부활” 위험 없음.
    // - 멱등성 보장
    //   - Cleanable.clean()을 직접 호출할 수도 있고, GC 후 자동 호출될 수도 있다.
    //   - 둘 중 무엇이 먼저 실행되더라도 Runnable은 단 한 번만 실행된다.
    // - try-with-resources와 병행 가능
    //   - AutoCloseable로 명시적 자원 해제를 지원하면서,
    //   - 혹시 놓친 경우를 대비해 Cleaner를 백업 안전망(safety net) 으로 쓸 수 있다.
    //   - Finalizer는 이런 보조적 사용에 적합하지 않다.
    // - 성능 및 단순성
    //   - Finalizer처럼 객체를 부활시키지 않으므로, 수거 과정이 단순하고 효율적이다.
    //     - cleaner는 Runnable 객체이기에 this가 가리키는 것은 MyResource 객체가 아니다. 부활시킬 수 없다.
    //   - GC와 Finalizer의 복잡한 상호작용이 줄어든다.
  }
}
