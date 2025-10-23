// # 아이템 78. 공유 중인 가변 데이터는 동기화해 사용하라
// - 여러 스레드가 가변 데이터를 공유한다면 그 데이터를 읽고 쓰는 동작은 반드시 동기화해야 한다.
//   동기화하지 않으면 한 스레드가 수행한 변경을 다른 스레드가 보지 못할 수 있다.
// - 공유되는 가변 데이터를 동기화하는데 실패하면,
//   응답 불가 상태에 빠지거나 안전 실패로 이어질 수 있다.
//   이는 디버깅 난이도가 가장 높은 문제에 속한다.
//   간헐적이거나 특정 타이밍에만 발생할 수 있고, VM에 따라 현상이 달라지기도 한다.
// - 베타적 실행은 필요 없고 스레드끼리의 통신만 필요하다면,
//   volatile 한정자만으로 동기화할 수 있다. 다만 올바로 사용하기가 까다롭다.
//
//

package effectivejava.ch11.item78.exam02;

// [주제] synchronized 키워드를 사용한 동기화 - 스레드간 통신(memory visibility/communication)
// - 한 스레드가 만든 변화를 다른 스레드가 보도록 보장한다.
//   즉 스레드 간 통신(channel) 역할 수행
// - 자바의 메모리 모델(JMM)에서는
//   각 스레드가 자신만의 CPU 캐시를 가진다.
//   따라서 자신만의 메모리 복사본(local copy)를 둘 수 있다.
//   이 때문에 한 스레드가 값을 수정해도, 다른 스레드가 그 변경을 즉시 보지 못할 수 있다.
// - synchronized 키워드를 사용해 동기화하면,
//   synchronized 블록 진입 시: 캐시된 값을 무효화하고 공유 메모리에서 최신 값을 읽어온다.
//   synchronized 블록 종료 시: 변경된 값을 공유 메모리에 즉시 반영한다.
//

// 동기화 전:
class StopFlag1 {
  private boolean stop = false;

  public void run() {
    while (!stop) {
      // 상황1: CPU 캐시(local cache)로 인해 stop 값이 영원히 false처럼 보일 수 있음!
      //   - 메서드를 호출하는 순간 stop 필드 값을 각 스레드가 자신만의 CPU 캐시에
      //   - stop 필드 값을 복사해 둘 수 있다.
      // 상황2: JIT 컴파일러의 최적화로 무한 루프에 빠질 수 있음!
      //   - JIT 컴파일러는 성능을 위해 stop 값을 반복문 외부로 빼내어,
      //   - 사실상 while (true) {} 으로 최적화할 수 있다.
      //   - 최적화 전:
      //       while (!stop) {
      //         ...
      //       }
      //   - 최적화 후:
      //       if (!stop) {
      //         while (true) {
      //           ...
      //         }
      //       }
      // 결론: 어떤 스레드가 stop 필드 값을 바꾸더라도 다른 스레드는 그 변경을 보지 못한다.
      //
    }
  }

  public void stop() {
    stop = true;
    // 한 스레드가 stop 필드를 true로 바꾸더라도
    // run()을 호출 중인 다른 스레드가 이 변경을 보지 못한다.
  }
}

// 부분 동기화 후:
class StopFlag2 {
  private boolean stop = false;

  // 동기화 안함: 여기서 문제가 발생!
  public void run() {
    while (!stop) {
      // 안타깝지만, 다른 스레드가 변경한 stop 필드의 값이 공유 메모리에 반영되더라도,
      // 이 메서드의 stop 값은 여전히 옛값(false)이다.
      // 왜?
      // 변경되기 전 stop 필드 값이 CPU 캐시나 레지스터에 저장되어 있고,
      // 반복문이 실행되는 동안 그 값을 계속 사용하기 때문이다.
      //
    }
  }

  // 동기화 수행
  public synchronized void stop() {
    // 1) synchronized 블록 진입
    //    다른 스레드가 공유 변수에 쓴 값들을 공유 메모리에서 CPU 캐시나 레지스터로 읽어 온다.
    stop = true;
    // 2) 로컬 CPU 캐시에 true를 저장한다.
    //    아직 다른 스레드는 이 변경을 보지는 못한다.
    // 3) synchronized 블록이 해제(메서드 종료)
    //    JVM은 변경된 값을 CPU 캐시에서 메인 메모리로 즉시 반영한다.
    //    이제 다른 스레드가 이 변경을 볼 수 있다.
  }
}

// [해설]
// 스레드A와 스레드B가 있다고 하자.
//   - 스레드 A: stop() 호출 중
//   - 스레드 B: run() 호출 중
// 스레드 A가 stop() 호출 완료했다고 하자.(synchronized가 해제 시점)
//   - 모든 변경을 공유 메모리에 반영(flush)
//     즉, 로컬 CPU에 캐시에 있는 stop 필드의 값이 메인 메모리에 기록됨
//   - 모든 lock 해제(release monitor)
//     이 lock에 의존하는 다른 스레드의 동기화 연산이 "happens-after" 관계를 얻을 수 있게 됨.
//     단, run() 메서드는 동기화되지 않았으므로,
//     stop 필드의 값은 옛값(false) 그대로 이다.
//

// 완전한 동기화 후:
class StopFlag3 {
  private boolean stop = false;

  // 공유 필드를 사용하는 메서드도 동기화 처리
  public void run() {
    while (!stop) {
      synchronized (this) {
        // synchronized 블록 진입:
        // - 다른 스레드가 공유 변수에 쓴 값들을 공유 메모리에서 CPU 캐시나 레지스터로 읽어 온다.
        // - 즉, stop 필드의 최신 값을 읽어 온다.
        //
        if (stop) break;
      }
    }
  }

  public synchronized void stop() {
    stop = true;
  }
}

// volatile 사용한 예:
class StopFlag4 {
  private volatile boolean stop = false;

  public void run() {
    int a;
    while (!stop) {
      // volatile 필드에서 값을 읽으면,
      // 그 값은 항상 메인 메모리에서 읽어온다.
      // volatile 필드의 값은 캐시하지 않는다.
    }
  }

  public void stop() {
    stop = true;
    // volatile 필드에 값을 쓰면,
    // 그 값은 즉시 메인 메모리에 반영된다.
  }
}

// [volatile의 특징]
// - 가시성(Visibility)을 보장
//   volatile 필드는 원자적(atomic)으로 읽고 쓸 수 있다.
//   즉 메인 메모리에서 직접 읽고 메인 메모리로 직접 직접 쓴다.
//   캐시 불일치 문제를 해결해 준다.
// - 재배치 금지(Ordering)
//   volatile 변수에 대한 read/write 앞뒤로는 명령어 재배치가 일어나지 않는다.
//   즉, JIT이나 CPU가 코드 순서를 바꾸지 않는다.
//   while (!stop) {} 을 while (true) {} 로 바꾸는 최적화가 일어나지 않는다.
// - 단, 배타적 실행(mutual exclusion)은 보장하지 않는다.
//   위 예제처럼 단순 플래그인 경우는 volatile로 충분하지만,
//   복잡한 상태 전환이 필요한 경우에는 synchronized를 사용해야 한다.

public class Test {
  public static void main(String[] args) throws Exception {}
}
