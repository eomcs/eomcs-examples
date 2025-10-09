// # 아이템 30. 이왕이면 제네릭 메서드로 만들라
// - 클래스와 마찬가지로 메서드로 제네릭으로 만들 수 있다.
// - 클라이언트에서 입력 파라미터와 반환 값을 명시적으로 형변환하는 것 보다
//   제네릭 메서드를 사용하는 것이 더 안전하고 편리하다.

package effectivejava.ch05.item30.exam06;

// [주제] 재귀적 타입 한정 (recursive type bound) + Optional<E> 활용
// - 컬렉션 객체가 비었을 때 예외를 던진다면 Optional<E>를 반환하는 것이 더 낫다.
// - Optional<E>는 null을 직접 다루지 않도록 도와준다.
// - 즉, 값이 존재하지 않을 때 null을 반환하는 대신 Optional.empty()를 반환하여,
//   호출자가 결과의 존재 여부를 안전하게 확인하고 NullPointerException을 방지할 수 있습니다.
//   이로써 코드의 안정성과 가독성이 높아집니다.

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Test {

  static <E extends Comparable<E>> Optional<E> max(Collection<E> c) {
    E result = null;
    for (E e : c) {
      if (result == null || e.compareTo(result) > 0) {
        result = Objects.requireNonNull(e);
      }
    }
    return Optional.ofNullable(result); // result가 null이면 Optional.empty()를 반환한다.
  }

  public static void main(String[] args) throws Exception {
    // String 클래스는 Comparable<String>를 구현하고 있다.
    Collection<String> names = List.of("전우치", "임꺾정", "홍길동", "유관순");
    String maxName = max(names).orElse("빈 컬렉션"); // 값이 없으면 "빈 컬렉션"을 반환한다.
    System.out.println(maxName);
  }
}
