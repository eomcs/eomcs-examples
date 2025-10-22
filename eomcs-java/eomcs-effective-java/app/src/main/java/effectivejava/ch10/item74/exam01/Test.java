// # 아이템 74. 메서드가 던지는 모든 예외를 문서화하라
// - 메서드가 던지는 예외는 그 메서드를 올바로 사용하는 데 아주 중요한 정보다.
//   따라서 메서드가 던질 가능성이 있는 모든 예외를 문서화하라.
//   검사 예외든 비검사 예외든, 추상 메서드든 구체 메서드든 모두 마찬가지다.
// - 예외를 문서화할 때 javadoc의 @throws 태그를 사용하라.
// - 검사 예외는 메서드 선언의 throws 문에 일일이 선언하고,
//   비검사 예외는 메서드 선언에 기입하지 말라.
// - 발생 가능한 예외를 문서로 남기지 않으면,
//   다른 사람이 그 클래스나 인터페이스를 효과적으로 사용하기 어렵거나 심지어 불가능할 수도 있다.
//
package effectivejava.ch10.item74.exam01;

// [주제] 예외를 문서화하는 방법
//

public class Test {

  // 1) 검사 예외는 항상 따로따로 선언하고,
  //    각 예외가 발생하는 상황을 javadoc의 @throws 태그로 설명하라.
  //    공통 상위 클래스로 뭉뚱그려 선언하는 일은 삼가자.
  /**
   * 메서드 설명
   *
   * @param index 설명
   * @param value 설명
   * @throws NumberFormatException 예외가 발생하는 조건 설명
   * @throws IllegalArgumentException 예외가 발생하는 조건 설명
   * @throws IndexOutOfBoundsException 예외가 발생하는 조건 설명
   */
  static void m1(int index, Object value)
      throws NumberFormatException, IllegalArgumentException, IndexOutOfBoundsException {
    // ...
  }

  // [예외를 잘못 선언한 예]
  // - 상위 클래스인 Exception 하나로 뭉뚱그려 선언했다.
  static void m2(int index, Object value) throws Exception {
    // ...
  }

  // 2) 비검사 예외도 검사 예외처럼 정성껏 문서화하되, throws 절에는 선언하지 말라.
  //    - 비검사 예외는 일반적으로 프로그래밍 오류를 뜻하는데,
  //      자신이 일으킬 수 있는 오류들이 무엇인지 알려주면
  //      프로그래머는 자연스럽게 해당 오류가 나지 않도록 코딩하게 된다.
  //    - 잘 정비된 비검사 예외 문서는 사실상 그 메서드를 성공적으로 수행하기 위한 전제조건이 된다.
  //      public 메서드라면 전제조건을 문서화해야 하면,
  //      그 수단으로 가장 좋은 것이 바로 비검사 예외를 문서화하는 것이다.
  //    - 비검사 예외를 throws 절에 선언하지 않는 이유:
  //      프로그래머는 어느 것이 비검사 예외인지 바로 알 수 있다.
  //
  //    - 예) String 클래스의 생성자 String(int[] codePoints, int offset, int count)의 코드를 확인하라.
  //      - IllegalArgumentException과 IndexOutOfBoundsException 예외를 던질 수 있음을 문서화해두고 있다.
  //      - 둘 다 비검사 예외다.
  //      - 메서드 선언의 throws 절에는 이 예외들이 선언되어 있지 않다.
  static String s = new String(new int[] {65, 66, 67, 68, 69}, 0, 5);

  // 3) 한 클래스에 정의된 많은 클래스가 같은 이유로 같은 예외를 던진다면,
  //    그 예외를 각각의 메서드에 일일이 문서화하는 대신,
  //    클래스 수준에서 문서화하는 것도 고려해볼 만하다.
  //    - 예) String 클래스 선언 부분에 기술한 javadoc 주석을 보라.
  //      - 아규먼트가 null 일 경우 NullPointerException을 던진다는 내용을
  //      - 클래스 수준에서 문서화해두고 있다.

  public static void main(String[] args) throws Exception {}
}
