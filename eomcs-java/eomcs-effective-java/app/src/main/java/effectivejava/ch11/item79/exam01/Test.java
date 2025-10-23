// # 아이템 79. 과도한 동기화는 피하라
// - 과도한 동기화는 성능을 떨어뜨리고, 교착 상태에 빠뜨리고, 심지어 예측할 수 없는 동작을 낳기도 한다.
// - 교착상태와 데이터 훼손을 피하려면 동기화 영역 안에서 외계인 메서드를 절대 호출하지 말라.
//   동기화 영역 안에서의 작업은 최소한으로 줄이자.
// - 가변 클래스를 설계할 때는 스스로 동기화해야 할지 고민하라.
// - 합당한 이유가 있을 때만 내부에서 동기화하고, 동기화했는지 여부를 명확히 문서화하라.
//

package effectivejava.ch11.item79.exam01;

// [주제] 외계인 메서드 호출 - 공유 객체의 일관성을 깨뜨리지 않은 경우
// - 동기화 안에서는, 재정의할 수 있는 메서드는 호출하지 말라.
//   클라이언트가 넘겨준 함수 객체를 호출해서도 안된다.
//   (동기화된 영역을 포함한 클래스 관점에서는 이런 메서드는 모두 바깥 세상에서 온 외계인이다.)
// - 외계인 메서드가 하는 일에 따라 동기화된 영역은,
//   예외를 일으키거나, 교착상태에 빠지거나, 데이터를 훼손할 수 있다.
// - 응답 불가와 안전 실패를 피하려면
//   동기화 메서드나 동기화 블록 안에서 제어를 클라이언트에 넘기지 말라.
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

  // GoF의 Observer 패턴 적용
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
    // [외계인 메서드가 일관성을 깨지 않는 경우]
    // 0 ~ 99 까지 추가될 때마다 출력하는 옵저버 등록
    ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());
    set.addObserver((s, e) -> System.out.println(e));
    for (int i = 0; i < 100; i++) set.add(i);
  }
}
