// # 아이템 18. 상속보다는 컴포지션을 사용하라
// - 상속은 코드를 재사용하는 강력한 수단이지만, 남용하면 오히려 해가 된다.
// - 상위 클래스와 하위 클래스를 모두 같은 프로그래머가 통제하는 패키지 안에서라면 상속도 안전한 방법이다.
// - 확장할 목적으로 설계되었고 문서화도 잘 된 클래스도 안전하다.
// - 상속은 상위 클래스와 하위 클래스가 순수한 is-a 관계일 때만 사용하라.
//   하위 클래스의 패키지가 상위 클래스와 다르고, 상위 클래스가 확장을 고려해 설계되지 않았다면 여전히 문제가 될 수 있다.
// - 상속의 취약점을 피하는 방법은 컴포지션과 전달을 사용하는 것이다.
//
// [상속이 위험한 경우] 다른 패키지의 구체(concrete) 클래스를 상속할 때
// 1) 내부 구현을 알 수 없음
//    부모 클래스의 private 필드, protected 메서드, 호출 순서를 파악할 수 없다.
// 2) 업데이트 시 깨짐
//    부모 클래스 제작자가 내부 구현을 변경하면 하위 클래스가 예기치 않게 오작동할 수 있다.
// 3) 테스트 불가능
//    부모의 동작을 모르면 자식이 어떤 상황에서 깨질지 테스트로 포착하기 어렵다.
// 4) 의도되지 않은 호출 순서
//    부모가 자기 메서드 안에서 다른 메서드를 호출할 수 있는데,
//    그게 하위 클래스가 오버라이드한 메서드일 수 있다.
//    "self-use problem"이라 부른다.
// - 상속은 "계약"이 아니라 "구현"을 재사용하는 방식이다.
//   즉 상속은 부모 클래스의 내부 구현 세부사항까지 함께 끌어안는 것이기에 문제가 된다.
//
//

package effectivejava.ch04.item18.exam04;

// [주제] 상속의 위험을 피하는 방법 - 컴포지션 구조로 바꾼다.
// - 기존 클래스를 확장하는 대신에 새 클래스를 만들고 기존 클래스의 인스턴스를 참조한다.
// - 기존 클래스가 새 클래스의 구성요소로 쓰인다는 뜻에서 이러한 설계를 "컴포지션(composition)"이라 부른다.

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

// 컴포지션 방식으로 기능 확장하기
class InstrumentedSet<E> implements Set<E> {
  private final Set<E> s;
  private int addCount = 0;

  public InstrumentedSet(Set<E> s) {
    this.s = s;
  }

  // 전달 메서드(forwarding method):
  // - 새 클래스의 인스턴스 메서드들은 기존 클래스의 해당 메서드를 호출해 그 결과를 반환한다.
  public void clear() {
    s.clear();
  }

  public boolean contains(Object o) {
    return s.contains(o);
  }

  public boolean isEmpty() {
    return s.isEmpty();
  }

  public int size() {
    return s.size();
  }

  public Iterator<E> iterator() {
    return s.iterator();
  }

  public boolean remove(Object o) {
    return s.remove(o);
  }

  public boolean containsAll(Collection<?> c) {
    return s.containsAll(c);
  }

  public boolean removeAll(Collection<?> c) {
    return s.removeAll(c);
  }

  public boolean retainAll(Collection<?> c) {
    return s.retainAll(c);
  }

  public Object[] toArray() {
    return s.toArray();
  }

  public <T> T[] toArray(T[] a) {
    return s.toArray(a);
  }

  // 기존 기능에 새 기능을 덧붙인다.
  @Override
  public boolean add(E e) {
    addCount++;
    return s.add(e);
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    addCount += c.size();
    return s.addAll(c);
    // addAll()은 재정의한 add()를 호출하지 않는다.
    // 왜? 이 클래스는 s가 가리키는 객체의 하위 클래스가 아니기 때문이다.
  }

  public int getAddCount() {
    return addCount;
  }
}

public class Test {

  public static void main(String[] args) throws Exception {
    InstrumentedSet<String> s = new InstrumentedSet<>(new HashSet<>());
    s.addAll(List.of("Snap", "Crackle", "Pop"));
    System.out.println(s.getAddCount());

    // [컴포지션(composition)과 전달(forwarding)]
    // - 새 클래스(InstrumentedSet)를 만들고 기존 클래스(HashSet)의 인스턴스를 참조한다.
    // - 새 클래스의 인스턴스 메서드들은 기존 클래스의 해당 메서드를 호출해 그 결과를 반환한다.
    // - 새로운 클래스는 기존 클래스의 내부 구현 방식의 영향에서 벗어난다.
    //   기존 클래스에서 새 메서드가 추가되더라도 전혀 영향받지 않는다.
    // - 컴포지션과 전달의 조합은 넓은 의미로 "위임(delegation)이라" 부른다.
    //   엄밀히 따지면 위임은 내부 객체에 자기 자신의 참조를 넘겨주는 경우를 말한다.
    //   예) ArrayList가 내부 객체인 Iterator에게 값을 조회하는 일을 맡길 때,
    //      Iterator에게 ArrayList 자신의 참조를 넘겨준다.

    // [래퍼(wrapper) 클래스와 데코레이션 패턴(decoration pattern)]
    // - 다른 인스턴스를 감싼다고 해서 InstrumentedSet 클래스를
    //   "래퍼(wrapper) 클래스"라 부르기도 한다.
    //   래퍼 클래스가 하위 클래스 보다 견고하고 강력하다.
    // - 다른 Set에 기능을 덧씌운다는 뜻에서 "데코레이션 패턴(decoration pattern)"이라고 한다.
    //   (GoF의 디자인 패턴 책에 소개되었다.)

    // [성능 영향]
    // - 전달 메서드(forwarding method)로 인해 느려질까,
    //   래퍼 클래스(wrapper class) 사용으로 메모리를 더 쓸까 걱정할 필요 없다.
    //   실전에서는 둘 다 별다른 영향이 없다고 밝혀졌다.
    // - 실제 병목을 일으키는 부분은,
    //   데이터베이스 쿼리, 네트워크 I/O, 알고리즘 복잡도, 불필요한 객체 생성 등이다.
    //   전달 메서드가 성능 병목이 되는 경우는 거의 없다.

    // [상속과 컴포지션]
    // - 상속은 "is-a" 관계, 컴포지션은 "has-a" 관계다.
    // - "is-a" 관계를 확신할 수 없다면, 상속을 사용하지 말라.
    //   컴포지션과 전달이 더 안전하고 유연한 대안이다.

    // ["is-a" 관계가 아닌 상황에서 상속을 잘못 사용한 사례]
    // - Stack 클래스는 Vector가 아님에도 Vector를 상속했다.
    // - Properties 클래스는 Hashtable이 아님에도 Hashtable을 상속했다.
  }
}
