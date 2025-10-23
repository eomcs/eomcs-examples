// # 아이템 79. 과도한 동기화는 피하라
// - 과도한 동기화는 성능을 떨어뜨리고, 교착 상태에 빠뜨리고, 심지어 예측할 수 없는 동작을 낳기도 한다.
// - 교착상태와 데이터 훼손을 피하려면 동기화 영역 안에서 외계인 메서드를 절대 호출하지 말라.
//   동기화 영역 안에서의 작업은 최소한으로 줄이자.
// - 가변 클래스를 설계할 때는 스스로 동기화해야 할지 고민하라.
// - 합당한 이유가 있을 때만 내부에서 동기화하고, 동기화했는지 여부를 명확히 문서화하라.
//

package effectivejava.ch11.item79.exam03;

// [주제] 외계인 메서드 호출 - 교착상태에 빠진 경우

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ObservableSet<E> extends ForwardingSet<E> {
  public ObservableSet(Set<E> set) {
    super(set);
  }

  private final List<SetObserver<E>> observers = new ArrayList<>();

  public void addObserver(SetObserver<E> observer) {
    synchronized (observers) {
      observers.add(observer);
    }
  }

  public boolean removeObserver(SetObserver<E> observer) {
    synchronized (observers) {
      return observers.remove(observer);
    }
  }

  private void notifyElementAdded(E element) {
    synchronized (observers) {
      for (SetObserver<E> observer : observers) observer.added(this, element);
    }
  }

  @Override
  public boolean add(E element) {
    boolean added = super.add(element);
    if (added) {
      notifyElementAdded(element);
    }
    return added;
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    boolean result = false;
    for (E element : c) {
      result |= add(element);
    }
    return result;
  }
}

public class Test {

  public static void main(String[] args) throws Exception {
    // [한 스레드가 lock을 쥐고 다른 스레드가 그 lock을 기다리는 교착상태 발생한 경우]
    // 23까지 출력한 다음, 옵저버 제거하기
    ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());
    set.addObserver(
        new SetObserver<Integer>() {
          @Override
          public void added(ObservableSet<Integer> s, Integer e) {
            System.out.println(e);
            if (e == 23) {
              ExecutorService exec = Executors.newSingleThreadExecutor();
              try {
                exec.submit(() -> s.removeObserver(this)).get();
                // main 스레드:
                //   - exec.submit() 호출
                //     ExecutorService객체가 실행시킬 Runnable 객체를 제출한다.
                //   - get() 호출
                //     Runnable 객체의 작업이 완료될 때까지 기다린다.
                // ExecutorService의 스레드:
                //   - main 스레드가 제출한 Runnable을 실행하기 위해 스레드가 생성된다.
                //   - main 스레드를 제출한 Runnable 객체를 실행한다.
                //   - removeObserver 메서드를 호출한다.
                //   - observers 리스트에 접근한다.
                //     하지만 observers 리스트는 main 스레드가 호출한
                //     notifyElementAdded 메서드에 의해 lock이 걸린 상태다.
                //   - removeObserver 메서드는 observers 리스트의 lock이 풀릴 때까지 대기한다.
                //
                // main 스레드도 기다리고, ExecutorService의 스레드도 기다리면서
                // 서로가 끝나기만을 기다리는 교착상태(데드락)가 발생한다.

              } catch (ExecutionException | InterruptedException ex) {
                throw new AssertionError(ex);
              } finally {
                exec.shutdown();
              }
            }
          }
        });

    for (int i = 0; i < 100; i++) {
      set.add(i);
    }

    // [외계인 코드의 무서움]
    // - 동기화된 영역 안에서 외계인 메서드를 호출하여 교착상태에 빠지는 경우가 자주 있다.
    // - 이렇게 외계인 코드가 무서운 것이다.
  }
}
