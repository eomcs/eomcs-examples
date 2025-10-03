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

package effectivejava.ch03.item10.exam02;

import java.util.Objects;

// 대칭성 만족의 예:
final class CaseInsensitiveString2 {
  private final String s;

  public CaseInsensitiveString2(String s) {
    this.s = Objects.requireNonNull(s);
  }

  // 대칭성 만족!
  @Override
  public boolean equals(Object o) {
    return o instanceof CaseInsensitiveString2
        && ((CaseInsensitiveString2) o).s.equalsIgnoreCase(s);
  }
}

public class Test2 {
  public static void main(String[] args) throws Exception {
    // 같은 클래스가 아니면 무조건 false를 반환한다.
    CaseInsensitiveString2 cis = new CaseInsensitiveString2("Polish");
    String s = "polish";
    System.out.println(cis.equals(s)); // false
    System.out.println(s.equals(cis)); // false

    System.out.println();

    // 대칭성을 만족한다.
    CaseInsensitiveString2 other = new CaseInsensitiveString2("Polish");
    System.out.println(cis.equals(other)); // true
    System.out.println(other.equals(cis)); // true
  }
}
