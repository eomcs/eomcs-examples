// # 아이템 30. 이왕이면 제네릭 메서드로 만들라
// - 클래스와 마찬가지로 메서드로 제네릭으로 만들 수 있다.
// - 클라이언트에서 입력 파라미터와 반환 값을 명시적으로 형변환하는 것 보다
//   제네릭 메서드를 사용하는 것이 더 안전하고 편리하다.

package effectivejava.ch05.item30.exam02;

// [주제] 제네릭 메서드

import java.util.HashSet;
import java.util.Set;

public class Test {

  // 제네릭 메서드
  // - 제네릭을 사용하여 세 개 집합의 원소 타입을 모두 같게 지정한 메서드이다.
  // - 컴파일 시 타입 검사를 수행한다.
  static <T> Set<T> union(Set<T> s1, Set<T> s2) {
    Set<T> result = new HashSet<>();
    for (T e : s1) {
      result.add(e);
    }
    for (T e : s2) {
      result.add(e);
    }
    return result;
  }

  public static void main(String[] args) throws Exception {
    Set<String> names = Set.of("홍길동", "임꺾정", "유관순");
    Set<Integer> scores = Set.of(100, 90, 80);

    // 다른 종류의 집합을 합치려고 할 때 컴파일러가 오류를 발생시킨다.
    // 즉 컴파일 시에 부적절한 코드를 걸러낼 수 있다.
    //    Set result = union(names, scores);
    //    System.out.println(result);
  }
}
