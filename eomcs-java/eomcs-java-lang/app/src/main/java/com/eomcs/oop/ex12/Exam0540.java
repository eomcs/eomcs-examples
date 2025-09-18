// 메서드 레퍼런스 - 스태틱 메서드 레퍼런스
package com.eomcs.oop.ex12;


public class Exam0540 {

  static class MyCalculator {
    public static int plus(int a, int b) {
      return a + b;
    }

    public static int plus2() {
      return 100;
    }
  }

  static interface Calculator1 {
    int compute(byte a, byte b);
  }

  static interface Calculator2 {
    int compute(short a, short b);
  }

  static interface Calculator3 {
    int compute(long a, long b);
  }

  static interface Calculator4 {
    int compute(float a, float b);
  }

  static interface Calculator5 {
    int compute(Object a, Object b);
  }

  static interface Calculator6 {
    int compute(String a, String b);
  }

  static interface Calculator7 {
    int compute(Integer a, Integer b);
  }

  static interface Calculator8 {
    int compute(int a);
  }

  static interface Calculator9 {
    int compute(int a, int b, int c);
  }

  public static void main(String[] args) {

    // 파라미터 타입: byte, byte ===> int, int
    Calculator1 c1 = MyCalculator::plus; // OK!
    //
    //    Calculator1 xx = new Calculator1() {
    //      @Override
    //      public int compute(byte a, byte b) {
    //        return MyCalculator.plus(a, b); // OK!
    //      }
    //    };

    // 파라미터 타입: short, short ===> int, int
    Calculator2 c2 = MyCalculator::plus; // OK!
    //
    //    Calculator2 c2 = new Calculator2() {
    //      @Override
    //      public int compute(short a, short b) {
    //        return MyCalculator.plus(a, b); // OK!
    //      }
    //    };

    // 파라미터 타입: long, long ===> int, int
    //    Calculator3 c3 = MyCalculator::plus; // 컴파일 오류!
    //
    //    Calculator3 c3 = new Calculator3() {
    //      @Override
    //      public int compute(long a, long b) {
    //        return MyCalculator.plus(a, b); // 컴파일 오류!
    //      }
    //    };

    // 파라미터 타입: float, float ===> int, int
    // Calculator4 c4 = MyCalculator::plus; // 컴파일 오류!

    // 파라미터 타입: Object, Object ===> int, int
    // Calculator5 c5 = MyCalculator::plus; // 컴파일 오류!

    // 파라미터 타입: String, String ===> int, int
    // Calculator6 c6 = MyCalculator::plus; // 컴파일 오류!

    // 파라미터 타입: Integer, Integer ===> int, int
    Calculator7 c7 = MyCalculator::plus; // OK
    //
    //    Calculator7 c7 = new Calculator7() {
    //      @Override
    //      public int compute(Integer a, Integer b) {
    //        return MyCalculator.plus(a, b); // OK!
    //        // 이유? 오토언박싱 때문이다.
    //        // MyCalculator.plus(a.intValue(), b.intValue()) 코드로 변경된다.
    //      }
    //    };

    // 파라미터 타입: int ===> int, int
    //    Calculator8 c8 = MyCalculator::plus; // 컴파일 오류!
    //
    //    Calculator8 c8 = new Calculator8() {
    //      @Override
    //      public int compute(int a) {
    //        return MyCalculator.plus(a, ?); // 컴파일 오류!
    //        // compute()는 int 값 한 개만 받는데, plus()는 int 값 두 개를 요구한다.
    //      }
    //    };

    // 파라미터 타입: int, int, int ===> int, int
    //    Calculator9 c9 = MyCalculator::plus; // 컴파일 오류!
    //
    //    Calculator9 c9 = new Calculator9() {
    //      @Override
    //      public int compute(int a, int b, int c) {
    //        return MyCalculator.plus(a, b, c); // 컴파일 오류!
    //        // compute()는 int 값 세 개를 받아서 plus()에 세 개 모두 전달한다.
    //        // 그러나 plus()는 int 파라미터가 두 개만 있다.
    //      }
    //    };

    //Calculator9 c99 = MyCalculator::plus2; //컴파일 오류!

    // 메서드 레퍼런스를 지정할 때 파라미터 타입 규칙:
    // => 인터페이스 규칙에 따라 받은 값을
    //    실제 메서드에 그대로 전달할 수 있다면 가능하다.
  }
}


