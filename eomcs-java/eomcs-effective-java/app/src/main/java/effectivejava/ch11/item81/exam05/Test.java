// # 아이템 81. wait와 notify보다는 동시성 유틸리티를 애용하라
// - wait와 notify는 올바르게 사용하기가 아주 까다로우니 고수준 동시성 유틸리티를 사용하라.
//   wait와 notify를 직접 사용하는 것을 동시성 '어셈블리 언어'로 프로그래밍하는 것에 비유할 수 있다.
//   반면, java.util.concurrent 패키지는 고수준 언어에 비유할 수 있다.
// - 코드를 새로 작성한다면 wait와 notify를 쓸 이유가 거의 없다.
//   이들을 사용하는 레거시 코드를 유지보수해야 한다면,
//   wait는 항상 표준 관용구에 따라 while문 안에서 호출하도록 하라.
//   일반적으로 notify보다는 notifyAll을 사용해야 한다.
//   혹시라도 notify를 사용한다면 응답 불가 상태에 빠지지 않도록 각별히 주의하라.
//
// [java.util.concurrent 패키지의 동시성 유틸리티들]
// 1) 실행자 프레임워크
// 2) 동시성 컬렉션(concurrent collection)
// 3) 동기화 장치(synchronizer)

package effectivejava.ch11.item81.exam05;

// [주제] wait/notify 사용법
// - 새로운 코드라면 wait/notify 대신 동시성 유틸리티를 사용하라.
// - 다만 레거시 코드를 유지보수해야 한다면 wait/notify를 올바르게 사용하라.
// - wait()
//   - 스레드가 어떤 좋건이 충적되기를 기다리게 할 때 사용한다.
//   - 해당 객체를 잠근 동기화 영역 안에서 호출해야 한다.
//   - 반드시 대기 반복문(wait loop) 관용구를 사용하라.
//   - 반복문 밖에서는 절대로 호출하지 말라.
//     이 반복문은 wait() 호출 전후로 조건이 만족되었는지 검사하는 역할을 한다.
//   - 대기 전에 조건을 검사하여 조건이 이미 충족되었다면 wait()를 건너뛰게 한 것은
//     응답 불가 상태를 예방하는 조치다.
//   - 만약 조건이 이미 충족되었는데 스레드가 notify()/notifyAll() 메서드를 먼저 호출한 후
//     대기 상태로 빠지면 영원히 깨어나지 못하는 응답 불가 상태에 빠질 수 있다.
//   - 대기 후에 조건을 검사하여 조건이 충족되지 않았다면 다시 대기하게 하는 것은
//     안전 실패를 막는 조치다.
//     만약 조건이 충족되지 않았는데 스레드가 동작을 이어가면,
//     lock이 보호하는 불변식을 깨뜨릴 위험이 있다.
// - notify()/notifyAll()
//   - notify()는 스레드 하나만 깨우며, notifyAll()은 대기 중인 모든 스레드를 깨운다.
//   - 일반적으로 notifyAll()을 사용하라.
//     깨어나야 하는 모든 스레드가 깨어남을 보장하니 항상 정확한 결과를 얻을 것이다.
//     깨어난 스레드들은 기다리던 조건이 충족되었는지 확인하여, 충족되지 않았다면 다시 대기할 것이다.
//   - 모든 스레드가 같은 조건을 기다리고, 조건이 한 번 충족될 때마다 단 하나의 스레드만 혜택을 받을 수 있다면,
//     notifyAll() 대신 notify()를 사용해 최적화 할 수 있다.
// - notifyAll()을 권장하는 이유:
//   - 외부로 공개된 객체에 대해 실수로 혹은 악의적으로 notify를 호출하는 상황에 대비해
//     wait()를 반복문 안에서 호출했다.
//   - 마찬가지로 관련 없는 스레드가 실수로 혹은 악의적으로
//     wait()를 호출하는 공격으로부터 보호하기 위해 notifyAll()을 사용하라.
//     그런 스레드가 notify()를 삼켜버린다면 꼭 깨어나야 할 스레드들이 영원히 대기하게 될 수 있다.
//
import java.util.LinkedList;
import java.util.Queue;

class WaitNotify {

  private static final int MAX_CAPACITY = 5;
  private final Object lock = new Object(); // 공유 락 객체
  private final Queue<Integer> queue = new LinkedList<>();

  // 생산자 스레드
  class Producer implements Runnable {
    private int value = 0;

    @Override
    public void run() {
      try {
        while (true) {
          produce();
          Thread.sleep(300);
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    private void produce() throws InterruptedException {
      synchronized (lock) {
        while (queue.size() == MAX_CAPACITY) {
          System.out.println("[Producer] 큐가 가득 참. 대기 중...");
          lock.wait(); // 소비자가 꺼낼 때까지 대기
        }

        System.out.println("[Producer] 생산: " + value);
        queue.add(value++);
        lock.notifyAll(); // 소비자에게 알림
      }
    }
  }

  // 소비자 스레드
  class Consumer implements Runnable {
    @Override
    public void run() {
      try {
        while (true) {
          consume();
          Thread.sleep(1000);
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    private void consume() throws InterruptedException {
      synchronized (lock) {
        while (queue.isEmpty()) {
          System.out.println("  [Consumer] 큐가 비어 있음. 대기 중...");
          lock.wait(); // 생산자가 넣을 때까지 대기
        }

        int val = queue.remove();
        System.out.println("  [Consumer] 소비: " + val);
        lock.notifyAll(); // 생산자에게 알림
      }
    }
  }
}

public class Test {
  public static void main(String[] args) {
    WaitNotify example = new WaitNotify();

    Thread producer = new Thread(example.new Producer());
    Thread consumer = new Thread(example.new Consumer());

    producer.start();
    consumer.start();

    // [조건이 만족되지 않아도 스레드가 깨어날 수 있는 상황]
    // 1) 스레드가 notify()를 호출한 다음 대기 중이던 스레드가 깨어나는 사이에
    //    다른 스레드가 lock을 얻어 그 lock이 보호하는 상태를 변경한다.
    // 2) 다른 스레드가 실수로 혹은 악의적으로 notify()를 호출한다.
    //    공개된 객체를 lock으로 사용해 대기하는 클래스는 이런 위험에 노출된다.
    //    외부에 노출된 객체의 동기화된 메서드 안에서 호출하는 wait은 모두 이 위험에 노출된다.
    // 3) 깨우는 스레드는 지나치게 관대해서, 대기 중인 스레드 중 일부만 조건이 충족되어도
    //    notifyAll()을 호출해 모든 스레드를 깨울 수도 있다.
    // 4) 대기 중인 스레드가 notify() 없이도 깨어나는 경우가 있다.
    //    허위 각성(spurious wakeup)이라 부르는 이 현상은 매우 드물지만
    //    자바 가상 머신에서 발생할 수 있다.
  }
}
