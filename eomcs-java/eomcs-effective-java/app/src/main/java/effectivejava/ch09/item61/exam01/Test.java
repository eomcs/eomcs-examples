// # 아이템 61. 박싱된 기본 타입보다는 기본 타입을 사용하라
// [자바 타입]
// - 기본타입: byte, short, int, long, float, double, char, boolean
// - 참조타입: 기본 타입 외(클래스, 인터페이스, 배열 등)
// - 박싱된 기본 타입: 기본 타입에 대응하는 참조 타입
//   Byte, Short, Integer, Long, Float, Double, Character, Boolean
//
// [기본 타입과 박싱된 기본 타입의 차이점]
// - 기본 타입은 값만 가지고 있다.
//   박싱된 기본 타입은 값에 더해 식별성(identity)이란 속성을 갖는다.
// - 기본 타입은 언제나 유효하다.
//   박싱된 기본 타입은 유효하지 않은 값 null을 가질 수 있다.
// - 기본 타입은 박싱된 기본 타입보다 시간과 메모리 사용면에서 더 효율적이다.
//
// [박싱된 기본 타입을 사용할 때]
// - 컬렉션의 원소, key, value로 쓴다.
// - parameterized type이나 type parameter에서는
//   기본 타입을 사용할 수 없으므로 박싱된 기본 타입을 쓴다.
// - 리플렉션을 통해 메서드를 호출할 때도 박싱된 기본 타입을 쓴다.
//
package effectivejava.ch09.item61.exam01;

// [주제] 박싱된 기본 타입에 == 연산자를 사용하면 오류가 일어난다.

import java.util.Comparator;

public class Test {

  public static void main(String[] args) {
    Comparator<Integer> naturalOrder = (i, j) -> (i < j) ? -1 : (i == j) ? 0 : 1;

    System.out.println(naturalOrder.compare(Integer.valueOf(127), Integer.valueOf(127))); // 0
    System.out.println(naturalOrder.compare(Integer.valueOf(-128), Integer.valueOf(-128))); // 0
    System.out.println(naturalOrder.compare(Integer.valueOf(128), Integer.valueOf(128))); // 1
    System.out.println(naturalOrder.compare(Integer.valueOf(-129), Integer.valueOf(-129))); // 1

    // [정리]
    // - Integer.valueOf 메서드는 -128~127 범위의 값을 캐싱한다.
    //   즉 이 범위의 값을 박싱할 때는 동일한 객체를 반환한다.
    //   따라서 -128~127 범위의 값을 박싱한 객체끼리는 == 연산자가 true를 반환한다.
    //   왜? 같은 객체니까!
    // - 하지만 이 범위를 벗어난 값을 박싱할 때는 매번 새로운 객체를 생성하여 반환한다.
    //   따라서 이 범위를 벗어난 값을 박싱한 객체끼리는 == 연산자가 false를 반환한다.
    //   왜? 서로 다른 객체니까!
    System.out.println("--------------------------------");

    // [해결책] 박싱된 기본 타입을 비교하기 전에 언박싱하라.
    Comparator<Integer> naturalOrder2 =
        (i, j) -> {
          int ii = i; // 오토 언박싱
          int jj = j; // 오토 언박싱
          return (ii < jj) ? -1 : (ii == jj) ? 0 : 1;
        };
    System.out.println(naturalOrder2.compare(Integer.valueOf(127), Integer.valueOf(127))); // 0
    System.out.println(naturalOrder2.compare(Integer.valueOf(-128), Integer.valueOf(-128))); // 0
    System.out.println(naturalOrder2.compare(Integer.valueOf(128), Integer.valueOf(128))); // 0
    System.out.println(naturalOrder2.compare(Integer.valueOf(-129), Integer.valueOf(-129))); // 0
  }
}
