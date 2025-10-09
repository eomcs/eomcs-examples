// # 아이템 30. 이왕이면 제네릭 메서드로 만들라
// - 클래스와 마찬가지로 메서드로 제네릭으로 만들 수 있다.
// - 클라이언트에서 입력 파라미터와 반환 값을 명시적으로 형변환하는 것 보다
//   제네릭 메서드를 사용하는 것이 더 안전하고 편리하다.

package effectivejava.ch05.item30.exam01;

// [주제] 일반 메서드

import java.util.HashSet;
import java.util.Set;

public class Test {

  // 일반 메서드
  // - 제네릭을 사용하지 않은 메서드이다.
  // - 컴파일 시 타입 검사가 이루어지지 않는다.
  static Set union(Set s1, Set s2) {
    Set result = new HashSet();
    for (Object e : s1) {
      result.add(e);
    }
    for (Object e : s2) {
      result.add(e);
    }
    return result;
  }

  public static void main(String[] args) throws Exception {
    Set<String> names = Set.of("홍길동", "임꺾정", "유관순");
    Set<Integer> scores = Set.of(100, 90, 80);

    // 다른 종류의 집합을 합치는 것은 문법적으로는 문제가 없지만,
    // 논리적으로 문제가 있다.
    // 이것을 컴파일러가 잡아주지 못한다.
    Set result = union(names, scores);
    System.out.println(result);
  }
}
