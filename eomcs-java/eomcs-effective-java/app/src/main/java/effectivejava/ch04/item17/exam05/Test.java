// # 아이템 17. 변경 가능성을 최소화하라
// - 불변 클래스란 그 인스턴스의 내부 값을 수정할 수 없는 클래스다.
//   불변 인스턴스에 간직된 정보는 고정되어 객체가 파괴되는 순간까지 절대 달라지지 않는다.
//   - 불변 클래스의 예: String, Wrapper 클래스들, BigInteger, BigDecimal, java.time 패키지의 클래스들
// - 불변 클래스의 장점
//   1. 단순성: 불변 클래스는 설계, 구현, 사용이 단순하다.
//   2. 안전성: 불변 클래스는 내부 상태가 바뀌지 않으므로, 공유해도 안전하다.
//   3. 해시코드 캐싱: 불변 클래스는 해시코드를 캐싱해두고 재사용할 수 있다.
//   4. 방어적 복사 불필요: 불변 클래스는 방어적 복사를 할 필요가 없다.
// - 불변 클래스를 만드는 방법
//   1. 객체의 상태를 변경하는 메서드(변경자)를 제공하지 않는다.
//   2. 클래스를 확장할 수 없도록 한다.
//      하위 클래스에서 부주의하게 혹은 나쁜 의도로 객체의 상태를 변하게 만드는 사태를 막아준다.
//      클래스를 final로 선언하거나 생성자를 private으로 선언한다.
//   3. 모든 필드를 final로 선언한다.
//      시스템이 강제하는 수단을 이용해 설계자의 의도를 명확히 드러내는 방법이다.
//      새로 생성된 인스턴스를 동기화 없이 다른 스레드로 건테도 문제없이 동작하게끔 보장한다.
//      (Java Language Spec. 17.5절 참조)
//   4. 모든 필드를 private으로 선언한다.
//      필드가 참조하는 가변 객체를 클라이언트에서 직접 접근해 변경하는 것을 막아준다.
//      기본 타입 필드나 불변 객체를 참조하는 필드에 대해서는 public final로만 선언해도 불변 객체가 된다.
//      하지만, 추후 내부 표현을 바꾸지 못하는 문제가 있으므로 권하지 않는다.
//   5. 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없도록 한다.
//      클래스에 가변 객체를 참조하는 필드가 하나라도 있다면
//      클라이언트에서 그 객체의 참조를 얻을 수 없도록 해야 한다.
//      이런 필드는 절대 클라이언트가 제공한 객체 참조를 가리키게 해서는 안되며,
//      접근자 메서드가 그 필드를 그대로 반환해서도 안된다.
//      왜? 클라이언트(호출자)가 그 객체를 변경할 수 있기 때문이다. 그러면 불변성이 깨진다.
//      생성자, 접근자, readObject() 메서드 모두에서 방어적 복사를 수행하라.

package effectivejava.ch04.item17.exam05;

// [주제]
// 불변 객체가 아닌 것을 파라미터로 받을 때 방어적 복사를 하라

import java.math.BigInteger;

public class Test {

  static BigInteger safeInstance(BigInteger val) {
    return val.getClass() == BigInteger.class ? val : new BigInteger(val.toByteArray());
  }

  public static void main(String[] args) throws Exception {
    BigInteger bigInteger = new BigInteger("123456789");

    // 파라미터가 BigInteger의 인스턴스라면 그대로 반환한다.
    BigInteger safeBigInteger = safeInstance(bigInteger);
    System.out.println(safeBigInteger == bigInteger); // true

    class MyBigInteger extends BigInteger {
      public MyBigInteger(String val) {
        super(val);
      }
      // 하위 클래스에서 불변성을 깨는 메서드를 추가하거나 재정의 할 수 있다.
    }

    // 파라미터가 BigInteger의 하위 클래스 인스턴스라면 복사본을 만들어 반환한다.
    BigInteger myBigInteger = new MyBigInteger("123456789");
    BigInteger safeBigInteger2 = safeInstance(bigInteger);
    System.out.println(safeBigInteger2 == myBigInteger); // false

    // [BigInteger와 BigDecimal]
    // - 초창기 이 클래스를 설계할 당시에 불변 클래스가 될 수 있도록 상속이 불가하게 설계해야 했지만,
    //   이를 처리하지 않아 하위 클래스에 메서드를 모두 재정의할 수 있다.
    // - 이미 이들 클래스는 널리 사용되는 클래스이므로 아직도 이 문제를 고치지 못하고 있다.
    // - 따라서, 이들 클래스를 파라미터로 받는 메서드를 작성할 때는
    //   위 예제처럼 방어적 복사를 해주는 것이 좋다.

    // [정리]
    // - getter가 있다고 무조건 setter를 만들지 마라.
    // - 클래스는 꼭 필요한 경우가 아니라면 불변이어야 한다.
    //   불변 클래스는 장점이 많으며, 단점이라곤 특정 상황에서의 잠재적 성능 저하뿐이다.
    // - PhoneNumber나 Complex 같은 단순 값 객체는 항상 불변 클래스로 만들라.
    // - String과 BigInteger 처럼 무거운 값 객체도 불변 클래스로 만들고,
    //   성능이 문제라면 가변 동반 클래스(예: StringBuilder)를 함께 제공하라.
    // - 불변으로 만들 수 없는 클래스라도 변경할 수 있는 부분을 최소화하라.
    //   객체가 가질 수 있는 상태의 수를 줄이면 예측하기 쉬워지고 오류가 생길 가능성이 줄어든다.
    // - 꼭 변경해야 할 필드를 뺀 나머지 모두를 final로 선언하라.
    //   다른 합당한 이유가 없다면 모든 필드는 private final 이어야 한다.
    // - 생성자는 불변식 설정이 모두 완료된, 초기화가 완벽히 끝난 상태의 객체를 생성해야 한다.
    //   확실한 이유가 없다면 생성자와 정적 팩토리 외에는 어떤 초기화 메서드도 public으로 제공하지 말라.
    //   객체를 재활용할 목적으로 상태를 다시 초기화하는 메서드도 안된다.
    //   복잡성만 커지고 성능 이점은 거의 없다.
    //   예) java.util.concurrent.CountDownLatch 클래스
    //   - 비록 가변 클래스지만 가질 수 있는 상태의 수가 많지 않다.
    //   - 인스턴스를 한 번 사용하고 그걸로 끝이다.
    //   - 카운트가 0에 도달하면 더는 재사용할 수 없는 것이다.
  }
}
