// 예외 상황 알리기 - 예외 던지기 + 예외 처리 강요
package com.eomcs.exception.ex91;

public class Exam0420 {

  static int sum;
  static float aver;

  static void computeScore(int kor, int eng, int math) throws Exception {
    // 예외를 던지는 경우 메서드 선언에 명시해야 한다.

    if (kor < 0 || kor > 100 ||
        eng < 0 || eng > 100 ||
        math < 0 || math > 100) {
      throw new Exception("입력 점수가 옳지 않습니다.");
      // 애플리케이션에서 던지는 예외는
      // java.lang.Exception 타입이어야 한다.
      // Exception 계열의 예외는
      // 호출자에게 예외 처리를 강요한다.
    }

    sum = kor + eng + math;
    aver = sum / 3f;
  }

  public static void main(String[] args) {

    // 예외 처리 문법을 사용하지 않으면 호출할 수 없다.
    //    computeScore(100, 90, 80);  // 컴파일 오류!

    try {
      computeScore(100, 90, 80);
      System.out.printf("합계=%d, 평균=%f\n", sum, aver);

    } catch (Throwable e) {
      System.out.println(e.getMessage());

    }
  }
}

// 예외 알림 방식 : 리턴 값 vs 예외 던지기
//
// 1) 리턴 값
// - 메서드를 호출한 뒤 항상 조건문을 통해 리턴 값을 검사해야 한다.
// - 작업 흐름을 기술한 코드와
//   예외를 다루는 코드가 섞여 있어
//   코드를 읽기 불편하다.
//
// 2) 예외 던지기
// - try ~ catch ~ 블록을 이용하여 예외를 다뤄야 한다.
// - 작업 흐름을 기술한 코드와
//   예외를 다루는 코드가 분리되어 있어
//   코드를 읽기 편하다.
//















