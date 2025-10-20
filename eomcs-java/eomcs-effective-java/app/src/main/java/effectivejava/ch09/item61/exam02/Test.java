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
package effectivejava.ch09.item61.exam02;

// [주제] 기본 타입과 박싱된 기본 타입을 혼용할 때 - 박싱된 기본 타입이 자동 언박싱된다.

public class Test {

  static Integer i;

  public static void main(String[] args) {
    if (i == 42) { // i가 언박싱되는 순간 NullPointerException 발생
      System.out.println("i == 42");
    }

    // [언박싱]
    // - 박싱된 기본 타입이 기본 타입으로 변환되는 것
    // - 컴파일러는 박싱된 기본 타입과 기본 타입이 혼용되는 코드를 만나면
    //   자동으로 언박싱 코드를 삽입한다.
    //   예) i == 42  --> i.intValue() == 42
    //   i가 null이면 NullPointerException이 발생한다.
  }
}
