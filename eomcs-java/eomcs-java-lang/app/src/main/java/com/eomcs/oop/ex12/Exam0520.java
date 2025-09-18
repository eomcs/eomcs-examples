// 메서드 레퍼런스 - 스태틱 메서드 레퍼런스 구현 원리
package com.eomcs.oop.ex12;


public class Exam0520 {

  static class MyCalculator {
    public static int plus(int a, int b) {
      return a + b;
    }

    public static int minus(int a, int b) {
      return a - b;
    }

    public static int multiple(int a, int b) {
      return a * b;
    }

    public static int divide(int a, int b) {
      return a / b;
    }

    public static int power(int a) {
      return a * 2;
    }
  }

  interface Calculator {
    int compute(int a, int b);
  }

  public static void main(String[] args) {
    // 스태틱 메서드 레퍼런스로 Calculator 구현체를 만드는 방법
    //
    Calculator c01 = MyCalculator::plus;

    //    // 1) 컴파일러야 나 대신 니가 Calculator 구현 클래스를 작성하라.
    //    class $1 implements Calculator {
    //      @Override
    //      public int compute(int a, int b) {
    //        // 2) 컴파일러야 compute() 메서드가 호출될 때 MyCalculator의 plus()를 호출하도록
    //        //    나 대신 작성하라.
    //        return MyCalculator.plus(a, b);
    //      }
    //    }
    //
    //    // 3) 컴파일러야 나 대신 만든 로컬 클래스의 인스턴스를 생성하여 그 주소를 c01 레퍼런스에 저장하라.
    //    Calculator c01 = new $1();


    Calculator c02 = MyCalculator::minus;
    Calculator c03 = MyCalculator::multiple;
    Calculator c04 = MyCalculator::divide;

    //    Calculator c05 = MyCalculator::power; // 컴파일 오류! 해당 메서드를 가지고 구현체를 만들 수 없다.
    //
    // 컴파일러는 위의 문장을 다음의 코드로 변환하려고 했는데,
    // MyCalculator에는 power(int,int) 규격을 가진 메서드가 없기 때문에
    // 컴파일 오류를 발생시킨 것이다.
    //
    //    class $2 implements Calculator {
    //      @Override
    //      public int compute(int a, int b) {
    //        return MyCalculator.power(a, b); // 문제는 여기서 오류가 발생한다.
    //      }
    //    }
    //
    // 즉 인터페이스의 메서드와 규격이 다르면 다음과 같은 코드를 생성할 수 없기 때문에
    // 컴파일 오류가 발생한다.
    //    Calculator c05 = (a, b) -> MyCalculator.power(a, b);

    // 위의 코드는 내부적으로 다음과 같다.
    //
    Calculator c1 = new Calculator() {
      @Override
      public int compute(int a, int b) {
        // 기존 메서드가 메서드 레퍼런스로 전달 가능한지 여부는
        // 다음 코드를 참고하라.
        // 인터페이스에 정의된 메서드(예: compute())가 호출되었을 때,
        //
        // 그 파라미터 값은 메서드 레퍼런스로 지정된
        // 스태틱 메서드(예: plus())에게 전달될 것이다.
        // => 그래서 스태틱 메서드의 파라미터는 항상
        //    인터페이스 메서드에 정의된 파라미터 값을 받을 수 있어야 한다.
        //
        // 스태틱 메서드의 리턴 값은
        // 인터페이스 메서드에 정의된 대로 리턴할 수 있어야 한다.
        // => 그래서 스태틱 메서드의 리턴 타입은
        //    인터페이스 메서드의 리턴 타입과 일치하거나
        //    그 타입으로 바꿀 수 있어야 한다.
        //
        return MyCalculator.plus(a, b);
      }
    };

    System.out.println(c1.compute(200, 17));
  }
}


