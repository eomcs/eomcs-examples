// 예외 상황 알리기 - 예외 던지기 + 예외 처리 강요 안함
package com.eomcs.exception.ex91;

public class Exam0430 {

  static int sum;
  static float aver;

  static void computeScore(int kor, int eng, int math) {
    // RuntimeException 타입의 예외는
    // 메서드 선언에 명시하지 않아도 된다.

    if (kor < 0 || kor > 100 ||
        eng < 0 || eng > 100 ||
        math < 0 || math > 100) {
      throw new RuntimeException("입력 점수가 옳지 않습니다.");
      // java.lang.Exception 타입의 예외 중에서
      // java.lang.RuntimeException 예외는
      // 호출자에게 예외 처리를 강요하지 않는다.
    }

    sum = kor + eng + math;
    aver = sum / 3f;
  }

  public static void main(String[] args) {

    // 예외 처리 문법을 사용하지 않더라도 호출할 수 있다.
    // => RuntimeException 계열의 예외를 던지는 경우,
    //    컴파일러는 예외 처리를 했는지 검사하지 않는다.
    computeScore(100, 90, 80);  // OK!
    // 단 예외가 발생했을 때 상위 호출자에게
    // 그 예외를 전달한다.

    // 물론 다음과 같이 예외 처리 문법을 사용해도 된다.
    try {
      computeScore(100, 90, 80);
      System.out.printf("합계=%d, 평균=%f\n", sum, aver);

    } catch (Throwable e) {
      System.out.println(e.getMessage());

    }
  }
}

// Exception vs RuntimeException
//
// 1) Exception
// - 호출자에게 예외 처리를 강요한다.
// - 메서드에서 이 예외를 던지는 경우,
//   해당 메서드의 선언부에 기술해야 한다.
// - 개발자에게 예외를 강조하는 효과가 있다.
//
// 2) RuntimeException
// - 호출자에게 예외 처리를 강요하지 않는다.
// - 메서드에서 이 예외를 던지는 경우
//   해당 메서드의 선언부에 기술할 필요가 없다.
// - 예외를 한 곳에 모아 처리할 때 유용하다.
// - 개발자가 예외 처리를 놓치는 경우가 있다.
//















