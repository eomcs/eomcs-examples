// # 아이템 79. 과도한 동기화는 피하라
// - 과도한 동기화는 성능을 떨어뜨리고, 교착 상태에 빠뜨리고, 심지어 예측할 수 없는 동작을 낳기도 한다.
// - 교착상태와 데이터 훼손을 피하려면 동기화 영역 안에서 외계인 메서드를 절대 호출하지 말라.
//   동기화 영역 안에서의 작업은 최소한으로 줄이자.
// - 가변 클래스를 설계할 때는 스스로 동기화해야 할지 고민하라.
// - 합당한 이유가 있을 때만 내부에서 동기화하고, 동기화했는지 여부를 명확히 문서화하라.
//

package effectivejava.ch11.item79.exam04;

// [주제] 외계인 메서드 호출 - 동기화 블록 바깥으로 빼낸 경우
//

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
    // observers 리스트를 순회하는 중에 observers 리스트가 변경되는 것을 허용하기 위해
    // observers 리스트의 스냅샷을 만든다.
    List<SetObserver<E>> snapshot = null;

    synchronized (observers) {
      snapshot = new ArrayList<>(observers);
    }

    // 외계인 메서드 호출을 동기화 블록 바깥으로 뺌
    for (SetObserver<E> observer : snapshot) {
      // observers 리스트를 순회하는 것이 아니라,
      // 스냅샷 리스트를 순회하기 때문에 observers 리스트가 변경되더라도 문제가 없다.
      observer.added(this, element);
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
    // [외계인 메서드 호출을 동기화 블록에서 빼내어 교착상태 해결하기]
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
                // observer.added() 메서드는 동기화 영역 밖에서 호출되고 있어서
                // 교착상태에 빠지지 않는다.
                // observers 리스트의 스냅샷을 순회하고 있기 때문에,
                // observers 리스트가 변경하더라도 문제가 없다.
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

    // [동기화 영역과 외계인 메서드]
    // - notifyElementAdded() 처럼 동기화 영역 바깥에서 호출되는
    //   외계인 메서드(예: observer.added())를 열린 호출(open call)이라고 부른다.
    // - 외계인 메서드는 얼마나 오래 실행될지 알 수 없는데,
    //   동기화 영역 안에서 호출된다면
    //   그동안 다른 스레드는 보호된 자원을 사용하지 못하고 대기해야만 한다.
    // - 따라서 열린 호출은 실패 방지 효과외에도 동시성 효율을 크게 개선해준다.
    //

  }
}
