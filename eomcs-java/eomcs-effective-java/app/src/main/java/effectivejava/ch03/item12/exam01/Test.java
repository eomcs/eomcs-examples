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

package effectivejava.ch03.item12.exam01;

// toString()을 재정의: 포맷을 문서화하여 명시한 경우와 명시하지 않은 경우

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

  // [포맷을 문서화하여 명시한 예]
  /**
   * 이 전화번호는 문자열 표현을 반환한다.<br>
   * 이 문자열은 "XXX-YYY-ZZZZ" 형태의 12글자로 구성된다.<br>
   * XXX는 지역코드, YYY는 프리픽스, ZZZZ는 가입자 번호이다.<br>
   * 각각의 대문자는 10진수 숫자 하나를 나타낸다.<br>
   * <br>
   * 전화번호 각 부분의 값이 너무 작어서 자릿수를 채울 수 없다면, 앞쪽을 0으로 채운다.<br>
   * 예컨대 가입자 번호가 123이라면 전화번호의 마지막 네 문자는 "0123"이 될 것이다.
   */

  // [포맷을 문서화하지 않은 예]
  /**
   * 전화번호를 문자열로 반환한다.<br>
   * 상세 형식은 정해지지 않았으며 향후 변경될 수 있다.<br>
   * 예) [전화번호: 지역코드=111, 프리픽스=222, 가입자번호=3333]
   */
  @Override
  public String toString() {
    return String.format("%03d-%03d-%04d", areaCode, prefix, lineNum);
  }

  // 포맷 명시 여부와 상관없이 toString() 반환 값에 포함된 정보를 얻어올 수 있는 메서드를 제공해야 한다.
  // 예를 들어, 지역코드, 프리픽스, 가입자 번호용 접근자를 제공해야 한다.
  public short getAreaCode() {
    return areaCode;
  }

  public short getPrefix() {
    return prefix;
  }

  public short getLineNum() {
    return lineNum;
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    PhoneNumber pn = new PhoneNumber(123, 456, 7890);
    System.out.println(pn); // PhoneNumber.toString() 자동 호출

    // toString()에 포함된 정보를 접근자를 이용해 꺼낼 수 있다.
    System.out.println(pn.getAreaCode());
    System.out.println(pn.getPrefix());
    System.out.println(pn.getLineNum());
  }
}
