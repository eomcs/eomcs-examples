// 메서드 오버로딩(overloading) - 문법 사용 후
package com.eomcs.oop.ex06.b;

public class Exam0120 {

  static class Calculator {

    // 파라미터의 타입이나 개수가 다르더라도
    // 같은 일을 하는 메서드에 대해서는 같은 이름을 갖게 할 수 있다.
    //
    static int plus(int a, int b) {
      return a + b;
    }

    static int plus(int a) {
      return a + a;
    }

    static float plus(float a, float b) {
      return a + b;
    }
  }

  public static void main(String[] args) {

    // 호출하는 메서드 이름은 같지만,
    // 아규먼트의 타입이나 개수에 따라 호출되는 메서드가 결정된다.
    //
    int r1 = Calculator.plus(100, 200);
    int r2 = Calculator.plus(100);
    float r3 = Calculator.plus(35.7f, 22.2f);
    float r4 = Calculator.plus(35.7f, 22); // plus(float, float) : 형변환 가능!
    //    double f4 = Calculator.plus(35.7, 22.2); // 파라미터 타입이나 개수가 일치하는 메서드가 없다.

    System.out.printf("%d, %d, %.1f, %.1f\n", r1, r2, r3, r4);

    // 오버로딩(overloading)?
    // => 파라미터의 형식(타입과 개수)은 다르지만
    //    같은 기능을 수행하는 메서드에 대해 같은 이름을 부여함으로써
    //    프로그래밍의 일관성을 제공하기 위한 문법이다.


  }
}






