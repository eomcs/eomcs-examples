// # 아이템 30. 이왕이면 제네릭 메서드로 만들라

package effectivejava.ch05.item31.exam06;

// [주제] 한정적 와일드카드 타입을 적용하기: Item30 예제에 적용 전 문제점 확인

import java.util.HashSet;
import java.util.Set;

public class Before {

  // 한정적 와일드카드 타입을 적용하기 전
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
    Set<Integer> integers = Set.of(100, 90, 80);
    Set<Double> doubles = Set.of(100.0, 90.0, 80.0);

    // union()의 아규먼트로 받는 Set과 리턴할 Set의 원소 타입이 같아야 한다.
    // 그런데, 모두 다르다.
    // union()의 첫 번째 파라미터 Set의 원소 타입은 Integer 이다.
    // union()의 두 번째 파라미터 Set의 원소 타입은 Double 이다.
    // union()의 리턴 값 Set의 원소 타입은 Number 이다.
    // 그래서 컴파일 오류가 발생하는 것이다.
    //
    //    Set<Number> result = union(integers, doubles); // 컴파일 오류!
    //    System.out.println(result);
  }
}
