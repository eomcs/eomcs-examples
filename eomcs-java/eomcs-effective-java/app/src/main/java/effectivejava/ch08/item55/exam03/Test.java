// # 아이템 55. 옵셔널 반환은 신중히 하라
// - 값을 반환하지 못할 가능성이 있고, 호출할 때마다 반환값이 없을 가능성을 염두에 둬하는 경우,
//   옵셔널을 반환하는 것이 유용할 수 있다.
// - 옵셔널 반환에는 성능 저하가 뛰따르니,
//   성능에 민감한 메서드라면 null을 반환하거나 예외를 던지는 편이 나을 수 있다.
// - 옵셔널을 반환값 이외의 용도로 쓰는 경우는 매우 드물다.
//

package effectivejava.ch08.item55.exam03;

// [주제] 옵셔널을 리턴하는 기준과 리턴 값 사용법
// - 옵셔널은 검사 예외(checked exception)와 비슷한 역할을 한다.
//   즉, 호출자에게 "값이 없을 수도 있다"는 사실을 명확히 알릴 필요가 있을 때 옵셔널이 유용하다.
//   비검사 예외(unchecked exception)를 던지거나 null을 반환한다면,
//   API 사용자가 그 사실을 인지하지 못해 끔찍한 결과로 이어질 수 있다.
// - 옵셔널을 리턴하는 메서드를 호출할 경우,
//   클라이언트는 값을 받지 못했을 때 취할 행동을 선택해야 한다.

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Test {

  // Optional<T> 리턴 방식 - 스트림 버전
  static <E extends Comparable<E>> Optional<E> max(Collection<E> c) {
    return c.stream().max(Comparator.naturalOrder());
  }

  public static void main(String[] args) {
    //    List<String> names = List.of("임꺽정", "홍길동", "김삿갓");
    List<String> names = List.of();

    // Optional<T>을 리턴하는 메서드를 호출할 때,
    // 1) 기본 값을 정해둘 수 있다.
    String result1 = max(names).orElse("빈 컬렉션입니다.");
    System.out.println("최대값: " + result1);

    // 2) 원하는 예외를 던질 수 있다.
    //    String result2 = max(names).orElseThrow(IllegalStateException::new);
    //    System.out.println("최대값: " + result2);

    // 3) 항상 값이 채워져 있다고 가정한다.
    // 단 값이 없으면 NoSuchElementException가 던져진다.
    //    String result3 = max(names).get();
    //    System.out.println("최대값: " + result3);

    // 4) 값이 비어 있을 때 기본 값을 제공할 수 있다.
    String result4 = max(names).orElseGet(() -> "익명");
    System.out.println("최대값: " + result4);
  }
}
