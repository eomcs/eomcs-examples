// # 아이템 49. 매개변수가 유효한지 검사하라
// - "오류는 가능한 한 빨리 발견하는 것이 좋다"는 원칙에 따라,
//   메서드나 생성자의 몸체가 시작되기 전에 검사하는 것이 좋다.
//   오류가 발생한 즉시 잡지 못하면, 해당 오류를 감지하기 어려워지고
//   감지하더라도 오류의 발생 지점을 찾기 어려워진다.
// - 메서드 몸체가 실행되기 전에 매개변수를 확인한다면 잘못된 값이 넘어왔을 때
//   즉각적이고 깔끔한 방식으로 예외를 던질 수 있다.
//   매개변수 검사에 실패하면 "실패 원자성(failure atomicity)"을 어기는 결과를 낳을 수 있다.
// - public과 protected 메서드는 매개변수 값이 잘못됐을 때 던지는 예외를 문서화해야 한다.
//   (예: NullPointerException, IllegalArgumentException 등)
//

package effectivejava.ch08.item49.exam01;

// [주제] 매개변수의 제약을 문서화하기

import java.math.BigInteger;

public class Test {

  /**
   * (현재값 mod m)를 반환한다. 이 메서드는 항상 음이 아닌 BigInteger를 반환한다는 점에서 remainder 메서드와 다르다.
   *
   * @param m 계수(양수여야 한다.)
   * @return 현재 값 mod m
   * @throws ArithmeticException 매개변수 m이 양수가 아닐 때
   */
  public BigInteger mod(BigInteger m) {
    // m이 null일 때 NullPointerException을 던진다
    // 그런데 문서에는 명시하지 않았다.
    // 그 이유는 이런 설명이 BigInteger 클래스 레벨에서 기술되어 있기 때문이다.
    // 이렇게 모든 메서드에 적용되는 주석은 클래스 레벨에서 기술하면 훨씬 효과적이다.
    if (m.signum() <= 0) {
      throw new ArithmeticException("매개변수 b는 양수여야 합니다. " + m);
    }
    // 나머지 구현 생략
    return BigInteger.ZERO;
  }

  public static void main(String[] args) {}
}
