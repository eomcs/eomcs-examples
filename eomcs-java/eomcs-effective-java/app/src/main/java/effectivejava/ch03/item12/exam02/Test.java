// # 아이템 12. toString을 항상 재정의하라
// - Object의 기본 toString()은 "클래스_이름@16진수_해시코드"를 반환한다.
//   즉, 재정의 하지 않는다면 이와같이 쓸모가 없는 메시지가 출력된다.
// - toString()을 잘 구현한 클래스는 사용하기에 훨씬 즐겁고, 디버깅하기 쉽다.
// - println(), printf(), 문자열 연결 연산자(+), assert() 등에서 자동으로 호출된다.
// - 어떤 컴포넌트가 오류 메시지를 로깅할 때 우리가 만든 클래스의 toString()을 호출할 수 있다.

// toString() 일반 규약
// - 간결하면서 사람이 읽기 쉬운 형태의 유익한 정보를 반환해야 한다.
// - 모든 하위 클래스에서 이 메서드를 재정의하라.
// - 그 객체가 가진 모든 정보를 반환하는 게 좋다.
//   물론 객체가 너무 거대한 경우에는 요약 정보를 리턴한다.
//   중요한 것은 객체를 완벽히 설명하는 문자열이어야 한다.
// - 포맷을 문서화하여 명시할 수 있다.
//   전화번호나 행렬 같은 값 클래스라면 문서화하는 게 좋다.
//   예) "555-1234" 또는 "{{1, 2}, {3, 4}}"
// - 포맷을 명시하면, 표준적이고 명확해서 파싱하기 쉽다.
// - 포맷을 명시하기로 했다면,
//   문자열과 객체를 상호 전환할 수 있는 정적 팩토리나 생성자를 제공하는 게 좋다.
// - 포맷을 문서로 명시할 때의 문제점은,
//   이를 사용하는 개발자들이 포맷에 맞춰 코드를 작성하기 때문에
//   나중에 포맷이 변경되었을 때 이에 종속된 코드가 영향을 받는다는 것이다.
//   따라서 포맷을 문서화할 때는 신중해야 한다.

package effectivejava.ch03.item12.exam02;

// toString()을 재정의: Lombok으로 자동 재정의 및 재정의하지 말아야 하는 경우

import lombok.Getter;
import lombok.ToString;

@ToString // Lombok이 자동으로 toString()을 재정의해준다.
@Getter // Lombok이 자동으로 접근자 메서드를 만들어준다.
class PhoneNumber {
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
}

// enum 은 toString()을 재정의할 이유가 없다.
// - 왜? 자바가 자동으로 toString()을 재정의해준다.
enum Suit {
  CLUB,
  DIAMOND,
  HEART,
  SPADE
}

// 정적 유틸리티 클래스인 경우 toString()을 재정의할 이유가 없다.
// - 왜? 인스턴스가 없고, 상태로 없기 때문에 "문자열 표현"이 필요 없다.
//
// [정적 유틸리티 클래스]
// - 인스턴스를 만들지 않고 정적 필드와 정적 메서드만을 제공하는 클래스이다.
//   예) java.lang.Math, java.util.Arrays, java.util.Collections 등
// - 모든 멤버가 static: 상태를 갖지 않는다.
// - 인스턴스화 금지: 생성자를 private으로 선언한다.
// - 스레드 안전성: 상태가 없으므로 일반적으로 멀티스레드에 안전하다.
// - 다형성/상속 대상 아님: 보통 final로 선언한다.
final class UtilityClass {

  // 컴파일러가 자동으로 default 생성자를 추가하지 못하도록 한다.(인스턴스 생성 방지)
  private UtilityClass() {
    // 내부에서 실수로 생성자를 호출하지 않도록 하기 위함
    throw new AssertionError();
  }

  // 유틸리티 메서드들...
  public static void hello(String name) {
    System.out.println("Hello " + name);
  }
}

public class Test {

  public static void main(String[] args) throws Exception {
    PhoneNumber pn = new PhoneNumber(123, 456, 7890);
    System.out.println(pn); // Lombok이 생성한 toString() 자동 호출

    // toString()에 포함된 정보를 접근자를 이용해 꺼낼 수 있다.
    System.out.println(pn.getAreaCode()); // Lombok이 생성한 접근자 호출
    System.out.println(pn.getPrefix()); // Lombok이 생성한 접근자 호출
    System.out.println(pn.getLineNum()); // Lombok이 생성한 접근자 호출

    // enum의 toString() 호출하기
    System.out.println(Suit.HEART); // Suit.HEART.toString() 자동 호출

    // UtilityClass는 인스턴스를 생성할 수 없기 때문에 toString()을 호출할 수 없다.
  }
}
