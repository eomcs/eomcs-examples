// # 아이템 30. 이왕이면 제네릭 메서드로 만들라

package effectivejava.ch05.item31.exam06;

// [주제] [주제] 한정적 와일드카드 타입을 적용하기: Item30 예제에 적용 한 후 문제점 해결

import java.util.HashSet;
import java.util.Set;

public class After {

  // 파라미터 선언에 한정적 와일드카드 타입을 사용하기
  static <T> Set<T> union(Set<? extends T> s1, Set<? extends T> s2) {
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
    // union()의 파라미터로 전달될 Set의 원소 타입은,
    // union()이 리턴하는 Set의 원소 타입과 같거나 하위 타입이면 된다.
    // 다음과 같이 union()의 파라미터로 전달되는 Set의 원소 타입이 Integer, Double 일 때,
    // union()의 리턴 값을 받을 Set의 원소 타입은 Integer와 Double의 공통 조상이어야 한다.
    // Integer, Double의 공통 조상은 Number 이므로 T는 Number로 추론된다.
    // 따라서 union()의 파라미터 타입 조건에 일치하기 때문에 컴파일 오류가 발생하지 않는다.

    // Java 8 부터는 메서드 타입 인자를 생략할 수 있다.
    // - 목표 타이핑(target typing)을 지원한다.
    //   메서드를 호출할 때 타입 인수(type argument)를 명시할 필요가 없다.
    // - 컴파일러가 문맥을 보고 메서드 타입 인자를 추론한다.
    Set<Number> result = union(integers, doubles); // Java 8 부터 가능
    System.out.println(result);

    // Java 7 이하에서는 메서드 타입 인자를 생략할 수 없다.
    // - 메서드를 호출할 때 타입 인수(type argument)를 반드시 명시해야 한다.
    // - 이것이 코드를 지저분하게 만든다.
    Set<Number> result2 = After.<Number>union(integers, doubles); // Java 7 이하
    System.out.println(result2);
  }
}
