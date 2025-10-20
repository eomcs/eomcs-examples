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
package effectivejava.ch09.item61.exam03;

// [주제] 오토 박싱으로 성능이 저하되는 예

public class Test {

  public static void main(String[] args) {
    // 기본 타입과 박싱된 기본 타입을 혼용한 경우
    Long sum = 0L;
    long start = System.currentTimeMillis();
    for (int i = 0; i <= 2_000_000_000; i++) {
      sum += i;
      // 1) sum을 언박싱하여 i와 더한다.
      // 2) 더한 결과를 다시 박싱하여 sum에 대입한다.
    }
    long end = System.currentTimeMillis();
    System.out.println("Sum: " + sum);
    System.out.println("걸린 시간: " + (end - start) + " ms");
    System.out.println("-----------------------------");

    // 기본 타입만 사용한 경우
    long sum2 = 0L;
    start = System.currentTimeMillis();
    for (int i = 0; i <= 2_000_000_000; i++) {
      sum2 += i; // 오토 박싱/언박싱을 수행하지 않는다.
    }
    end = System.currentTimeMillis();
    System.out.println("Sum2: " + sum2);
    System.out.println("걸린 시간: " + (end - start) + " ms");

    // [정리]
    // - 오토 박싱과 오토 언박싱은 편리하지만 성능 저하를 일으킨다.
    // - 박싱된 기본 타입을 꼭 사용해야 한다면, 오토 박싱과 오토 언박싱이
    //   일어나지 않도록 주의 깊게 코딩하라.
  }
}
