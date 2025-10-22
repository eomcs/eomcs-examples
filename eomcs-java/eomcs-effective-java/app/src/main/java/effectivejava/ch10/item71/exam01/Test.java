// # 아이템 71. 필요 없는 검사 예외 사용은 피하라
// - 검사 예외를 싫어하는 개발자가 많지만, 제대로 활용하면 API와 프로그램의 질을 높일 수 있다.
// - 결과를 코드로 반환하거나 비검사 예외를 던지는 것과 달리,
//   검사 예외는 발생한 문제를 프로그래머가 처리하여 안전성을 높이게끔 해준다.
// - 검사 예외를 과하게 사용하면 오히려 쓰기 불편한 API가 된다.
//   검사 예외를 던지는 메서드를 호출한다면,
//   호출하는 쪽에서 반드시 예외를 처리하거나 더 바깥으로 던져 문제를 전파해야만 한다.
// - 검사 예외를 던지는 메서드는 스트림 안에서 직접 사용할 수 없다.
//   스트림 안에서 검사 예외를 던지는 메서드를 사용하려면
//   예외를 포장하는 래퍼 메서드를 만들어야 한다.
//
// [예외를 던지는 가이드라인]
// 1) API 호출자가 예외 상황에서 복구할 방법이 없다면,
//    비검사 예외를 던져라.
// 2) 복구가 가능하고 호출자가 그 처리를 해주길 바란다면,
//    우선 옵셔널을 반환해도 될지 고민하라.
// 3) 옵셔널만으로는 상황을 처리하기에 충분한 정보를 제공할 수 없을 때만
//    검사 예외를 던져라.
//
package effectivejava.ch10.item71.exam01;

// [주제] 검사 예외를 회피하는 방법 - 적절한 결과 타입을 담은 옵셔널을 반환

import java.util.Optional;

public class Test {

  static Optional<Integer> divide(int x, int y) {
    if (y == 0) {
      return Optional.empty(); // 예외 상황일 때,
    } else {
      return Optional.of(x / y); // 정상적으로 실행할 수 있을 때
    }
  }

  public static void main(String[] args) throws Exception {
    Optional<Integer> result1 = divide(10, 2);
    result1.ifPresentOrElse(
        v -> System.out.println("result1 = " + v), () -> System.out.println("0으로 나눌 수 없습니다."));

    Optional<Integer> result2 = divide(10, 0);
    result2.ifPresentOrElse(
        v -> System.out.println("result2 = " + v), () -> System.out.println("0으로 나눌 수 없습니다."));

    // [옵셔널을 사용하는 방식의 단점]
    // - 예외가 발생한 이유를 알려주는 부가 정보를 제공하지 못한다.
    //   예외를 사용하면 구체적인 예외 타입과 그 타입이 제공하는 메서드들을 활용해 부가 정보를 제공할 수 있다.
  }
}
