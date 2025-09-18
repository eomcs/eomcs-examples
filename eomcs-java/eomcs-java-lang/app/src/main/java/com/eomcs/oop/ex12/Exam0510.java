// 메서드 레퍼런스 - 스태틱 메서드 레퍼런스
package com.eomcs.oop.ex12;


public class Exam0510 {

  static class MyCalculator {
    public static int plus(int a, int b) {return a + b;}
    public static int minus(int a, int b) {return a - b;}
    public static int multiple(int a, int b) {return a * b;}
    public static int divide(int a, int b) {return a / b;}
  }

  interface Calculator {
    int compute(int x, int y);
  }

  public static void main(String[] args) {
    // 메서드 한 개짜리 인터페이스의 구현체를 만들 때,

    // 0) 로컬 클래스 활용
    class CalcImpl implements Calculator {
      @Override
      public int compute(int x, int y) {
        return x + y;
      }
    }
    Calculator obj0 = new CalcImpl();

    // 1) 익명 클래스 활용
    Calculator obj1 = new Calculator() {
      @Override
      public int compute(int x, int y) {
        return x + y;
      }
    };

    // 2) 람다 문법 활용
    Calculator obj2 = (x, y) -> x + y;

    // 3) 람다 문법 활용 : 기존 메서드 호출하기
    Calculator obj3 = (x, y) -> MyCalculator.plus(x, y);

    // 4) 기존에 작성한 클래스의 스태틱 메서드를 람다 대신 사용하기
    // => 인터페이스의 메서드 규격과 일치하는 메서드가 있다면,
    //    그 메서드를 람다 구현체로 대체할 수 있다.
    // => 새로 코드를 작성할 필요가 없어 매우 편리하다.
    // => 규격? 메서드의 파라미터 타입/개수/순서, 리턴 타입
    // => 문법:
    //    클래스명::메서드명
    Calculator c1 = MyCalculator::plus; // MyCalculator의 스태틱 메서드인 plus()를 가지고 구현체를 자동 생성!
    // 컴파일러는 위 코드를 다음의 람다 코드로 변환한다.
    // Calculator c1 = (x, y) -> MyCalculator.plus(x, y);
    //

    Calculator c2 = MyCalculator::minus; // => Calculator c2 = (x, y) -> MyCalculator.minus(x, y);
    Calculator c3 = MyCalculator::multiple; // => Calculator c3 = (x, y) -> MyCalculator.multiple(x, y);
    Calculator c4 = MyCalculator::divide; // => Calculator c4 = (x, y) -> MyCalculator.divide(x, y);

    System.out.println(c1.compute(200, 17)); // compute() ==> plus()
    System.out.println(c2.compute(200, 17)); // compute() ==> minus()
    System.out.println(c3.compute(200, 17)); // compute() ==> multiple()
    System.out.println(c4.compute(200, 17)); // compute() ==> divide()
  }
}


