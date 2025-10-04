// # 아이템 13. clone 재정의는 주의해서 진행하라
// [Cloneable 인터페이스]
// - Cloneable 인터페이스는 아무 메서드도 선언하지 않는다.
// - 단지 복제해도 되는 클래스임을 명시하는 용도의 믹스인 인터페이스(mixin interface)다.
// - 믹스인 인터페이스(mixin interface)란?
//   그 타입의 본질적 분류(상속 계층)과 무관하게, 추가 능력/역할을 섞어 넣는 용도의 인터페이스를 말한다.
// - Object의 protected 메서드 clone()의 동작 방식을 결정한다.
// - Cloneable 인터페이스를 구현하지 않은 클래스에서 clone()을 호출하면,
//   CloneNotSupportedException 예외가 발생한다.
// - Cloneable 인터페이스를 구현한 클래스는 clone() 메서드를 public으로 재정의해야 한다.

// [clone() 메서드의 일반 규약]
// - x.clone() != x
// - x.clone().getClass() == x.getClass()
// - x.clone().equals(x) == true
// - 관례상 이 메서드가 반환하는 객체는 super.clone()을 호출해서 얻어야 한다.
// - 관례상 반환된 객체와 원본 객체는 독립적이어야 한다.

package effectivejava.ch03.item13.exam01;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// clone() 메서드 재정의하기
// - 클래스 선언에 Cloneable 인터페이스를 구현을 포함해야 한다.
// - Cloneable 인터페이스 구현을 포함하지 않으면, clone()을 호출할 때 예외가 발생한다.
@ToString
@EqualsAndHashCode
@Getter
class PhoneNumber implements Cloneable {
  private final short areaCode;
  private final short prefix;
  private final short lineNum;

  public PhoneNumber(int areaCode, int prefix, int lineNum) {
    this.areaCode = rangeCheck(areaCode, 999, "지역코드");
    this.prefix = rangeCheck(prefix, 999, "프리픽스");
    this.lineNum = rangeCheck(lineNum, 9999, "가입자 번호");
  }

  private static short rangeCheck(int val, int max, String arg) {
    if (val < 0 || val > max) {
      throw new IllegalArgumentException(arg + ": " + val);
    }
    return (short) val;
  }

  // clone() 메서드 재정의하기
  // - clone()을 재정의할 때 리턴 값은 해당 타입으로 지정하라.
  @Override
  public PhoneNumber clone() {
    try {
      // 원본의 완벽한 복제본을 얻는다.
      // 모든 필드는 원본 필드와 똑 같은 값을 갖는다.
      return (PhoneNumber) super.clone(); // PhoneNumber 타입으로 형변환. 무조건 성공한다.
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(); // 발생하지 않는다.
    }
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    PhoneNumber original = new PhoneNumber(707, 867, 5309);
    PhoneNumber copy = original.clone();

    System.out.println(original);
    System.out.println(copy);
    System.out.println(original == copy); // false
    System.out.println(original.equals(copy)); // true
    System.out.println(original.getClass() == copy.getClass()); // true
  }
}
