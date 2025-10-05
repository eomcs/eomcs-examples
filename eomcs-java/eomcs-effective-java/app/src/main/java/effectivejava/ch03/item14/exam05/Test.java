// # 아이템 14. Comparable을 구현할지 고려하라
// [Comparable 인터페이스]
// - compareTo 메서드가 선언되어 있다.
// - 단순 동치성 비교에 더해 순서까지 비교할 수 있으며, 제네릭하다.
// - 이 인터페이스를 구현한다는 것은,
//   인스턴스들에 자연적인 순서(natural ordering)가 있음을 뜻한다.
// - 이 인터페이스 구현체의 배열은 Arrays.sort를 사용해 손쉽게 정렬할 수 있다.
// - 검색, 극단값, 자동 정렬되는 컬렉션 관리도 쉽게 할 수 있다.

package effectivejava.ch03.item14.exam05;

// [주제]
// - compareTo() 구현 방법

// 1) Comparable 제네릭 인터페이스이므로 타입 매개변수로 클래스 자신을 넘긴다.
class Version implements Comparable<Version> {
  int major;
  int minor;

  Version(int major, int minor) {
    this.major = major;
    this.minor = minor;
  }

  // 2) compareTo()의 파라미터 타입은 Comparable 인터페이스의 타입 매개변수와 일치시킨다.
  @Override
  public int compareTo(Version o) {
    // 3) 파라미터가 null이면 NullPointerException을 던진다.
    // - 파라미터 o가 null이면 어차피 NullPointerException이 발생한다.

    // 4) 핵심 필드가 여러 개일 경우, 가장 중요한 필드부터 비교한다.
    if (this.major != o.major) {
      // - 비교 결과가 0이 아니면 그 결과를 즉시 반환하자.
      return Integer.compare(this.major, o.major);
    }
    return Integer.compare(this.minor, o.minor);

    // [참고]
    // - 기본 타입 필드를 비교할 때는 Wrapper 클래스의 compare 메서드를 사용하라.
    // - 관계 연산자(<, >)를 사용하는 방식은 거추장스럽고 오류를 유발하기 쉽다.
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Version)) return false;
    Version other = (Version) obj;
    return this.major == other.major && this.minor == other.minor;
  }
}

public class Test {
  public static void main(String[] args) throws Exception {

    Version v1 = new Version(1, 0);
    Version v2 = new Version(1, 0);
    Version v3 = new Version(1, 1);

    System.out.println(v1.equals(v2)); // true
    System.out.println(v1.compareTo(v2)); // 0

    System.out.println(v1.equals(v3)); // false
    System.out.println(v1.compareTo(v3)); // 음수
  }
}
