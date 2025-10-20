// # 아이템 55. 옵셔널 반환은 신중히 하라
// - 값을 반환하지 못할 가능성이 있고, 호출할 때마다 반환값이 없을 가능성을 염두에 둬하는 경우,
//   옵셔널을 반환하는 것이 유용할 수 있다.
// - 옵셔널 반환에는 성능 저하가 뛰따르니,
//   성능에 민감한 메서드라면 null을 반환하거나 예외를 던지는 편이 나을 수 있다.
// - 옵셔널을 반환값 이외의 용도로 쓰는 경우는 매우 드물다.
//

package effectivejava.ch08.item55.exam01;

// [주제] 리턴 값 다루기 - 예외/null 리턴 vs 옵셔널 리턴
// 1) 예외/null 리턴
// - 예외를 던질 때,
//   - 진짜 예외적인 상황에서만 사용해야 한다.
//   - 예외를 생성할 때 스택 추적 전체를 캡처하므로 비용이 많이 든다.
// - null을 반환할 때,
//   - 별도의 null 처리 코드를 추가해야 한다.
// 2) Optional<T> 리턴
// - T 타입 참조를 하나 담거나 혹은 아무것도 담지 않을 수 있다.
// - 옵셔널은 원소를 최대 1개 가질 수 있는 '불변' 컬렉션이다.
// - 보통 T를 반환해야 하지만, 특정 조건에서 아무것도 반환하지 않아야 할 때,
//   T 대신 Optional<T>를 반환하도록 선언하면 된다.
// - 예외를 던지는 메서드보다 유연하고 사용하기 쉬우며, null을 반환하는 메서드보다 오류 가능성이 작다.

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class Test {

  // 1) 예외/ null 리턴 방식
  static <E extends Comparable<E>> E max(Collection<E> c) {
    if (c.isEmpty()) {
      throw new IllegalArgumentException(); // 또는 return null;
    }

    E result = null;
    for (E e : c) {
      if (result == null || e.compareTo(result) > 0) {
        result = e;
      }
    }
    return result;
  }

  // 2) Optional<T> 리턴 방식
  static <E extends Comparable<E>> Optional<E> max2(Collection<E> c) {
    if (c.isEmpty()) {
      return Optional.empty(); // 빈 옵셔널을 생성하여 반환한다.
    }

    E result = null;
    for (E e : c) {
      if (result == null || e.compareTo(result) > 0) {
        result = e;
      }
    }
    return Optional.of(result); // 값을 옵셔널에 담아 반환한다.
  }

  public static void main(String[] args) {
    // 1) 예외/ null 리턴 방식 사용
    try {
      String result = max(new ArrayList<String>()); // 예외 발생!
      System.out.println("최대값: " + result);
    } catch (IllegalArgumentException e) {
      System.out.println("빈 컬렉션입니다.");
    }

    // 2) Optional<T> 리턴 방식 사용
    Optional<String> result2 = max2(new ArrayList<String>());
    if (result2.isPresent()) {
      System.out.println("최대값: " + result2.get());
    } else {
      System.out.println("빈 컬렉션입니다.");
    }

    // [정리]
    // - 빈 옵셔널은 Optional.empty()로 생성하고,
    //   값을 담은 옵셔널은 Optional.of(value)로 생성한다.
    // - null 값을 담은 옵셔널은 Optional.ofNullable(value)로 생성한다.
    // - 옵셔널을 반환하는 메서드에서는 절대 null을 반환하지 말라.
  }
}
