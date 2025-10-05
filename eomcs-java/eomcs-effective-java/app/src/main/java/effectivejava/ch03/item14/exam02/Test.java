// # 아이템 14. Comparable을 구현할지 고려하라
// [Comparable 인터페이스]
// - compareTo 메서드가 선언되어 있다.
// - 단순 동치성 비교에 더해 순서까지 비교할 수 있으며, 제네릭하다.
// - 이 인터페이스를 구현한다는 것은,
//   인스턴스들에 자연적인 순서(natural ordering)가 있음을 뜻한다.
// - 이 인터페이스 구현체의 배열은 Arrays.sort를 사용해 손쉽게 정렬할 수 있다.
// - 검색, 극단값, 자동 정렬되는 컬렉션 관리도 쉽게 할 수 있다.

package effectivejava.ch03.item14.exam02;

// [주제]
// - compareTo() 메서드의 일반 규약을 확인한다.

// [compareTo() 메서드의 일반 규약]
// - 이 객체와 주어진 객체의 순서를 비교한다.
// - 이 객체가 주어진 객체보다 작으면 음의 정수를, 같으면 0을, 크면 양의 정수를 반환한다.
// - 이 객체와 비교할 수 없는 타입의 객체가 주어지면 ClassCastException을 던진다.

// [compareTo() 비교 법칙]
// - sgn(표현식)
//   - 수학에서 말하는 부호함수(signum function)이다.
//   - '표현식'의 값이 음수, 0, 양수일 때 -1, 0, 1을 반환한다.
// 1) 모든 x, y에 대해, sgn(x.compareTo(y)) == -sgn(y.compareTo(x)) 이어야 한다.
//    y.compareTo(x)가 예외를 던진다면 x.compareTo(y)도 예외를 던져야 한다.
// 2) 추이성을 보장해야 한다.
//    x.compareTo(y) > 0 && y.compareTo(z) > 0 이면 x.compareTo(z) > 0 이어야 한다.
// 3) 모든 z에 대해, x.compareTo(y) == 0 이면,
//    sgn(x.compareTo(z)) == sgn(y.compareTo(z)) 이어야 한다.
// 4) 다음 권고는 필수는 아니지만 꼭 지키는 것이 좋다.
//    (x.compareTo(y) == 0) == (x.equals(y)) 이어야 한다.
//    만약 이 권고를 지키지 않는다면, 그 사실을 명확히 문서화해야 한다.
//    예) "주의: 이 클래스의 순서는 equals()와 일관되지 않는다."

// [비교를 활용하는 클래스]
// - TreeSet, TreeMap: 정렬 기능에 활용
// - Collections, Arrays: 검색과 정렬 알고리즘에 활용

public class Test {
  public static void main(String[] args) throws Exception {

    // 첫 번째 규약 테스트: sgn(x.compareTo(y)) == -sgn(y.compareTo(x))
    // - 두 객체의 순서를 바꿔 비교해도 예상한 결과가 나와야 한다.
    String s1 = "abc";
    String s2 = "bcd";

    System.out.println(sgn(s1.compareTo(s2))); // -1
    System.out.println(sgn(s2.compareTo(s1))); // 1
    System.out.println("------------------------");

    // 두 번째 규약 테스트: x.compareTo(y) > 0 && y.compareTo(z) > 0 이면 x.compareTo(z) > 0
    // - 첫 번째가 두 번째 보다 크고, 두 번째가 세 번째보다 크면, 첫 번째는 세 번째보다 커야 한다.
    String s3 = "zzz";
    String s4 = "yyy";
    String s5 = "xxx";

    System.out.println(sgn(s3.compareTo(s4))); // 양수
    System.out.println(sgn(s4.compareTo(s5))); // 양수
    System.out.println(sgn(s3.compareTo(s5))); // 양수
    System.out.println("------------------------");

    // 세 번째 규약 테스트: x.compareTo(y) == 0 이면, sgn(x.compareTo(z)) == sgn(y.compareTo(z))
    // - 같은 객체들 끼리는 어떤 객체와 비교하더라도 항상 같아야 한다.
    String s6 = new String("hello");
    String s7 = new String("hello");
    String s8 = "world";

    System.out.println(sgn(s6.compareTo(s7))); // 0
    System.out.println(sgn(s6.compareTo(s8))); // -1
    System.out.println(sgn(s7.compareTo(s8))); // -1
    System.out.println("------------------------");

    // 네 번째 규약 테스트: (x.compareTo(y) == 0) == (x.equals(y))
    // - 같은 객체라면 equals()도 true를 반환해야 한다.
    // - 필수는 아니지만 지키는 것이 좋은 권고이다.
    String name1 = new String("홍길동");
    String name2 = new String("홍길동");

    System.out.println(name1.compareTo(name2)); // 0
    System.out.println(name1.equals(name2)); // true

    // [정리]
    // - compareTo() 3가지 규약은
    //   equals() 규약과 똑같이 반사성, 대칭성, 추이성을 충족해야 함을 뜻한다.
    // - 그래서 equals()와 주의사항도 똑같다.
    //   - 새로운 필드를 추가한 하위 클래스에서는 compareTo() 규약을 지킬 수 없다.
    //   - 상속 대신 컴포지션과 '뷰 메서드'를 통해 compareTo()를 구현하는 것이 더 유연하다.
    //
  }

  static int sgn(int value) {
    return (value < 0) ? -1 : (value == 0) ? 0 : 1;
  }
}
