// # 아이템 55. 옵셔널 반환은 신중히 하라
// - 값을 반환하지 못할 가능성이 있고, 호출할 때마다 반환값이 없을 가능성을 염두에 둬하는 경우,
//   옵셔널을 반환하는 것이 유용할 수 있다.
// - 옵셔널 반환에는 성능 저하가 뛰따르니,
//   성능에 민감한 메서드라면 null을 반환하거나 예외를 던지는 편이 나을 수 있다.
// - 옵셔널을 반환값 이외의 용도로 쓰는 경우는 매우 드물다.
//

package effectivejava.ch08.item55.exam02;

// [주제] 스트림 종단 연산과 옵셔널 - Stream.max() 사용
// - 스트림의 종단 연산 중 상당수가 옵셔널을 반환한다.

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
    // Optional<T> 리턴 방식 사용
    //    List<String> names = List.of("홍길동", "김삿갓", "임꺽정");
    List<String> names = List.of();
    Optional<String> result = max(names);
    if (result.isPresent()) {
      System.out.println("최대값: " + result.get());
    } else {
      System.out.println("빈 컬렉션입니다.");
    }
  }
}
