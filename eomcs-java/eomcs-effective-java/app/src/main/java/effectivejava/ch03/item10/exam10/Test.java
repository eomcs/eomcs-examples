// # 아이템 10. equals() 는 일반 규약을 지켜 재정의하라
// [재정의하지 말아야 할 상황]
// - 각 인스턴스가 본질적으로 고유하다.
//   - 값을 표현하는 게 아니라 동작하는 객체를 표현하는 클래스인 경우
// - 인스턴스의 '논리적 동치성(logical equality)'을 검사할 일이 없다.
// - 상위 클래스에서 재정의한 equals()가 하위 클래스에도 딱 들어 맞는다.
// - 클래스가 private이거나, package-private이고 equals() 메서드를 호출할 일이 없다.
//
// [equals() 메서드는 동치관계(equivalence relation)를 구현하며, 다음을 만족한다.]
// 1. 반사성(reflexive):
//    - null 이 아닌 모든 참조 값 x에 대해,
//      x.equals(x)는 true여야 한다.
// 2. 대칭성(symmetric):
//    - null 이 아닌 모든 참조 값 x, y에 대해,
//      x.equals(y)가 true이면, y.equals(x)도 true여야 한다.
// 3. 추이성(transitive):
//    - null 이 아닌 모든 참조 값 x, y, z에 대해,
//      x.equals(y)가 true이고, y.equals(z)가 true이면, x.equals(z)도 true여야 한다.
// 4. 일관성(consistency):
//    - null 이 아닌 모든 참조 값 x, y에 대해,
//      x.equals(y)를 반복해서 호출해도 항상 같은 결과를 반환해야 한다.
// 5. null-아님:
//    - null 이 아닌 모든 참조 값 x에 대해,
//      x.equals(null)는 false여야 한다.

package effectivejava.ch03.item10.exam10;

// equals() 메서드를 정의하는 방법
// 1) == 연산자를 사용해 비교하려는 대상 객체가 자기 자신의 참조인지 확인한다.
// 2) instanceof 연산자로 비교하려는 대상 객체가 올바른 타입인지 확인한다.
// 3) 비교하려는 대상 객체를 올바른 타입으로 형변환한다.
// 4) 핵심 필드(비교에 사용되는 필드)를 하나씩 비교한다.

// 필드 비교 방법
// - float과 double을 제외한 기본 타입 필드는 == 연산자로 비교한다.
// - 레퍼런스 필드는 equals() 메서드로 비교한다.
// - float과 double 필드는
//   Float.compare(float, float), Double.compare(double, double) 메서드로 비교한다.
// - float과 double은 NaN, +0.0, -0.0 때문에 == 연산자로 비교하면 안 된다.
// - Float.equals(), Double.equals() 메서드를 사용하지 말라.
//   이 메서드들은 박싱된 객체를 비교하므로 불필요한 오토박싱이 발생한다.
//   성능에 좋지 않다.
// - 배열 필드는 Arrays.equals(배열1, 배열2) 메서드로 비교한다.
// - 레퍼런스 필드 중에서 null도 정상 값으로 취급하는 경우,
//   Objects.equals(레퍼런스1, 레퍼런스2) 메서드로 비교한다.
//   왜? NullsPointerException이 발생하지 않기 때문이다.

// 필드 비교 성능을 높일 수 있는 팁!
// - 성능을 위해, 다를 가능성이 더 크거나 비교하는 비용이 싼 필드를 먼저 비교하자.
// - 동기화용 lock 필드 같이 객체의 논리적 상태와 관련 없는 필드는 비교하지 말라.
// - 핵심 필드로부터 계산해서 얻어지는 필드(파생 필드)는 비교할 필요가 없지만,
//   만약 파생 필드의 값이 객체 전체를 대표하는 값이라면 이 값을 비교하는 것이 더 낫다.

// equals() 구현 한 후 검증
// - 대칭적인가?
// - 추이적인가?
// - 일관적인가?
// - 단위 테스트를 작성해 검증하라!

final class PhoneNumber {
  private final short areaCode, prefix, lineNum;

  public PhoneNumber(int areaCode, int prefix, int lineNum) {
    this.areaCode = rangeCheck(areaCode, 999, "area code");
    this.prefix = rangeCheck(prefix, 999, "prefix");
    this.lineNum = rangeCheck(lineNum, 9999, "line num");
  }

  private static short rangeCheck(int val, int max, String arg) {
    if (val < 0 || val > max) throw new IllegalArgumentException(arg + ": " + val);
    return (short) val;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof PhoneNumber)) return false;
    PhoneNumber pn = (PhoneNumber) o;
    return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
  }

  // equals()를 재정의할 때 hashCode()도 반드시 재정의해야 한다.
  // - 자세한 것은 "아이템 11"에서 설명한다.
}

public class Test {
  public static void main(String[] args) throws Exception {
    // equals() 메서드를 재정의 할 때 주의할 점
    // - 메서드 시그너처를 틀리지 말라!
    //   - @Override 애노테이션을 붙여 컴파일러가 검사하게 하라.
    // - 직접 equals()를 재정의하지 말고
    //   IDE나 롬복(Lombok), Google의 AutoValue 같은 도구를 사용하라!
    // - 꼭 필요한 경우가 아니면 equals()를 재정의하지 말라!
    // - 재정의 할 때는 그 클래스의 핵심 필드 모두를 빠짐없이 비교하라.
    // - 또한 equals()의 다섯 가지 규약을 확실히 지켜라!

  }
}
