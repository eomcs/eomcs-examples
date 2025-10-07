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

package effectivejava.ch04.item18.exam01;

// [주제] 상속이 위험한 이유 1 - 부모 클래스의 내부 구현이 하위 클래스에 영향을 미친다.
// - 상위 클래스가 어떻게 구현되느냐에 따라 하위 클래스의 동작에 이상이 생길 수 있다.
// - 상위 클래스의 설계자가 확장을 충분히 고려하고 문서화를 제대로 해두지 않으면,
//   하위 클래스는 깨지기 쉽다.

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

// HashSet을 확장해 삽입된 원소의 개수를 세는 InstrumentedHashSet
class InstrumentedHashSet<E> extends HashSet<E> {
  private int addCount = 0;

  public InstrumentedHashSet() {}

  public InstrumentedHashSet(int initCap, float loadFactor) {
    super(initCap, loadFactor);
  }

  // 원소를 추가할 때 마다 카운트를 증가시킨다.
  @Override
  public boolean add(E e) {
    addCount++;
    return super.add(e);
  }

  // 원소를 증가할 때 마다 카운트를 증가시킨다.
  @Override
  public boolean addAll(Collection<? extends E> c) {
    addCount += c.size();
    return super.addAll(c);
  }

  public int getAddCount() {
    return addCount;
  }
}

public class Test {

  public static void main(String[] args) throws Exception {
    InstrumentedHashSet<String> s = new InstrumentedHashSet<>();
    s.addAll(List.of("Snap", "Crackle", "Pop"));
    System.out.println(s.getAddCount());
    // - 기대한 값은 3인데 실제 값은 6이다!

    // [문제의 원인]
    // - 하위 클래스 제작자는 부모 클래스의 addAll()이 내부적으로 add()를 호출(self-use)한다는 것을 몰랐다.
    //   (왜? 문서화가 안되어 있었기 때문이다.)
    // - 따라서 addAll()을 호출하면 호출 체인에 따라 하위 클래스에서 재정의한 add()가 호출된다.
    //   하위 클래스에서 재정의한 add()는 addCount를 증가시키다.
    //   결국 카운트를 중복 증가시키는 결과가 나온 것이다.
    // - 이렇게 상속은 상위 클래스의 내부 구현 세부사항에 의존한다.

    // [유사한 사례]
    // - JDK 1.0 시절(컬렉션 프레임워크 등장 이전)에 Hashtable과 Vector 가 있었다.
    //   이 둘은 공통 인터페이스(Collection, Map 등)도 없었고, 제네릭 타입도 없었다.
    // - 이후 JDK 1.2에서 컬렉션 프레임워크가 도입되면서
    //   기존 클래스를 완전히 없애는 대신, 기존 API와의 호환성을 위해 편입했다.
    //   즉 Hashtable은 Map을 구현하고, Vector는 List를 구현했다.
    //   여기에 추상 클래스의 상속까지 결합되면서 다음과 같은 호출 체인이 만들어졌다.
    //     Hashtable -> Dictionary -> AbstractMap -> Map
    //     Vector -> AbstractList -> AbstractCollection -> Collection
    // - 원래부터 Vector와 Hashtable을 상속 받은 하위 클래스는
    //   "self-use problem"이라는 상속의 부작용을 피할 수 없는 구조였는데,
    //   컬렉션 프레임워크에 편입되면서 이 호출 경로가 훨씬 복잡해졌다.
    // - 이것은 Vector나 Hashtable 설계자들의 의도와 다르게 동작하는 문제를 발생시켰다.
    //
  }
}
