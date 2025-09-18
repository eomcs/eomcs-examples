// 메서드 레퍼런스 - 인스턴스 메서드 레퍼런스 구현 원리
package com.eomcs.oop.ex12;


public class Exam0620 {

  static class Calculator {
    double rate;

    public Calculator(double rate) {
      this.rate = rate;
    }

    public double year(int money) {
      return money * rate / 100;
    }

    public double month(int money) {
      return money * rate / 100 / 12;
    }

    public double day(int money) {
      return money * rate / 100 / 365;
    }
  }

  static interface Interest {
    double compute(int money);
  }

  public static void main(String[] args) {

    Calculator 보통예금 = new Calculator(0.5);

    // 인스턴스 메서드 레퍼런스로 Calculator 구현체를 만드는 방법
    //
    // Iterest i1 = 보통예금::year;

    // 위의 코드는 내부적으로 다음과 같다.
    // 1) 람다 표현
    //    Interest i1 = money -> 보통예금.year(money);
    //
    // 2) 익명 클래스
    Interest i1 = new Interest() {
      @Override
      public double compute(int money) {
        // 인스턴스 메서드 레퍼런스는 실제
        // 인터페이스 구현체에서 다음과 같이 메서드로 호출된다.
        return 보통예금.year(money);
      }
    };

    System.out.printf("년 이자: %.1f\n", i1.compute(10_0000_0000));
  }
}


