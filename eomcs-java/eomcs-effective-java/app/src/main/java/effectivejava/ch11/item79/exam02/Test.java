// # 아이템 79. 과도한 동기화는 피하라
// - 과도한 동기화는 성능을 떨어뜨리고, 교착 상태에 빠뜨리고, 심지어 예측할 수 없는 동작을 낳기도 한다.
// - 교착상태와 데이터 훼손을 피하려면 동기화 영역 안에서 외계인 메서드를 절대 호출하지 말라.
//   동기화 영역 안에서의 작업은 최소한으로 줄이자.
// - 가변 클래스를 설계할 때는 스스로 동기화해야 할지 고민하라.
// - 합당한 이유가 있을 때만 내부에서 동기화하고, 동기화했는지 여부를 명확히 문서화하라.
//

package effectivejava.ch11.item79.exam02;

// [주제] 외계인 메서드 호출 - lock 재진입과 안전 실패
//

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    // [lock 재진입 후 안전 실패를 일으킨 경우]
    // 23까지 출력한 다음, 옵저버 제거하기
    ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());
    set.addObserver(
        new SetObserver<Integer>() {
          @Override
          public void added(ObservableSet<Integer> s, Integer e) {
            System.out.println(e);
            if (e == 23) {
              s.removeObserver(this); // List를 순회하는 도중에 remove() 호출하면 예외 발생
              // - ConcurrentModificationException 예외 발생!
            }
          }
        });

    for (int i = 0; i < 100; i++) {
      set.add(i);
    }

    // [이유]
    // for (0 ~ 99)
    //   -> set.add(i)
    //      -> notifyElementAdded(i)
    //         -> synchronized (observers) : lock 획득
    //            -> for (SetObserver<E> observer : observers)
    //               -> observer.added(this, element)
    //                  -> if (e == 23) s.removeObserver(this);
    //                     -> synchronized (observers) : lock 재진입 (문제 없음)
    //                        -> observers.remove(observer)  <--- 여기서 예외 발생!
    //                           리스트를 순회하는 도중에 리스트를 수정하는 메서드를 호출하면
    //                           ConcurrentModificationException 예외가 발생한다.
    //

    // [lock 재진입]
    // - notifyElementAdded 메서드에서 observers 리스트에 대해 lock을 획득한 상태에서
    //   removeObserver 메서드를 호출할 때 다시 같은 lock을 획득하려고 시도한다.
    //   문제는 없다. main 스레드가 이미 획득한 lock이기 때문이다.
    // - 위 예제는 lock에 걸려 발생한 예외가 아니다.
    //   리스트 순회 중에 리스트를 수정해서 발생한 예외다.
    //   이렇게 재진입 가능 lock은 교착상태를 피하는 데는 도움이 되지만,
    //   안전 실패(데이터 훼손)로 변모시킬 수 있다.
  }
}
