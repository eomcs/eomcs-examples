// # 아이템 79. 과도한 동기화는 피하라
// - 과도한 동기화는 성능을 떨어뜨리고, 교착 상태에 빠뜨리고, 심지어 예측할 수 없는 동작을 낳기도 한다.
// - 교착상태와 데이터 훼손을 피하려면 동기화 영역 안에서 외계인 메서드를 절대 호출하지 말라.
//   동기화 영역 안에서의 작업은 최소한으로 줄이자.
// - 가변 클래스를 설계할 때는 스스로 동기화해야 할지 고민하라.
// - 합당한 이유가 있을 때만 내부에서 동기화하고, 동기화했는지 여부를 명확히 문서화하라.
//

package effectivejava.ch11.item79.exam05;

// [주제] 외계인 메서드 호출 - 자바 동시성 컬렉션 라이브러리 사용하기
// CopyOnWriteArrayList:
// - ArrayList를 구현한 클래스이다.
// - 내부를 변경하는 작업은 항상 깨끗한 복사본을 만들어 수행하도록 구현했다.
// - 내부의 배열은 절대 수정되지 않으니 순회할 때 락이 필요 없어 매우 빠르다.
// - 다른 용도로 쓰인다면 끔찍이 느리겠지만,
//   수정할 일은 드물고 순회만 빈번히 일어나는 관찰자 리스트 용도로는 최적이다.

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ObservableSet<E> extends ForwardingSet<E> {
  public ObservableSet(Set<E> set) {
    super(set);
  }

  // ArrayList 대신 스레드 안전하고 관찰 가능한 집합 사용하기 - CopyOnWriteArrayList
  private final List<SetObserver<E>> observers = new CopyOnWriteArrayList<>();

  public void addObserver(SetObserver<E> observer) {
    // 동기화 코드가 필요 없다.
    // CopyOnWriteArrayList가 내부에서 알아서 동기화를 처리해준다.
    observers.add(observer);
    // 기존 배열을 복사해서 항목을 추가한 새 배열을 만든다.
    // 기존 배열 대신 새 배열을 참조한다.
    // add() 직전까지 존재하던 Iterator는 여전히 기존 배열을 참조하고 있으므로,
    // 구조 변경 예외(ConcurrentModificationException)가 발생하지 않는다.
  }

  public boolean removeObserver(SetObserver<E> observer) {
    // 동기화 코드가 필요 없다.
    // CopyOnWriteArrayList가 내부에서 알아서 동기화를 처리해준다.
    return observers.remove(observer);
    // 기존 배열을 복사해서 항목을 제거한 새 배열을 만든다.
    // 기존 배열 대신 새 배열을 참조한다.
    // remove() 직전까지 존재하던 Iterator는 여전히 기존 배열을 참조하고 있으므로,
    // 구조 변경 예외(ConcurrentModificationException)가 발생하지 않는다.
  }

  private void notifyElementAdded(E element) {
    // 동기화 코드가 필요 없다.
    // 내부 배열은 절대 수정되지 않으니 순회할 때 락이 필요 없다.
    for (SetObserver<E> observer : observers) {
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

    // [정리]
    // - CopyOnWriteArrayList 같은 동시성을 전문으로 관리해주는 컬렉션을 사용하면,
    //   동기화 코드를 작성하는 번거로움을 피할 수 있다.
    // - 동시성 컬렉션은 성능 특성이 다르므로, 용도에 맞게 사용해야 한다.
    //   특히 CopyOnWriteArrayList는 수정 작업을 할 때마다 내부 배열을 복사하므로,
    //   수정 작업이 빈번한 상황에서는 성능이 심각하게 저하될 수 있다.
    //   반면, 수정 작업이 드물고 순회 작업이 빈번한 상황에서는 매우 뛰어난 성능을 발휘한다.

    // [동기화 영역을 다루는 기본 규칙]
    // - 동기화 영역에서는 가능한 한 일을 적게 하는 것이다.
    //   락을 얻고, 공유 데이터를 검사하고, 필요하면 수정하고, 락을 놓는다.
    // - 오래 걸리는 작업이라면 동기화 영역 바깥으로 옮기는 방법을 찾아라.
    //

    // [성능을 떨어뜨리는 주요 동기화 비용]
    // 1) 경쟁하느라 낭비하는 시간이 더 문제다.
    //    락을 얻는 데 드는 CPU 시간은 전체 비용에 비하면 미비하다.
    //    병렬로 실행할 기회를 잃고, 모든 코어가 메모리를 일관되게 보기 위한 지연시간이 진짜 비용이다.
    // 3) 가상 머신이 동기화 코드의 최적화를 제한하는 것도 비용 증가의 원인이다.

    // [가변 클래스와 동기화]
    // 1) 동기화를 전혀 하지 말고,
    //    그 클래스를 동시에 사용해야 하는 클래스가 외부에서 알아서 동기화하게 하라.
    //    예:
    //    - java.util 컬렉션 클래스들
    //    - StringBuilder
    //    - ThreadLocalRandom
    // 2) 동기화를 내부에서 수행해 스레드 안전한 클래스로 만들라.
    //    단, 클라이언트가 외부에서 객체 전체에 lock을 거는 것보다
    //    동시성을 월등히 개선할 수 있을 때만 이 방법을 사용하라.
    //    예:
    //    - java.util.concurrent 컬렉션 클래스들
    //    - StringBuffer
    //    - Random
    // 3) 선택하기 어렵다면 동기화하지 말고, 문서에 "스레드 안전하지 않다"고 명시하라.
    //

    // [다양한 동기화 기법]
    // - 락 분할(lock splitting),
    // - 락 스트라이핑(lock striping),
    // - 비차단 동시성 제어(nonblocking concurrency control) 등
  }
}
