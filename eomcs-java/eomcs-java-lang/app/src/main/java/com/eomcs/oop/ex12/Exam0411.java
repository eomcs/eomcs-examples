// 리턴 문장에 람다(lambda) 활용
package com.eomcs.oop.ex12;

public class Exam0411 {

  static interface Interest {
    double compute(int money);
  }

  static Interest getInterest(final double rate) {
    // 로컬 클래스로 인터페이스 구현한 후 객체 리턴하기
    class InterestImpl implements Interest {
      // 로컬(또는 익명 로컬) 클래스 안에서 바깥 메서드(enclosing method)의 로컬 변수를 사용하면
      // 컴파일러는 자동으로 그 값을 보관하기 위해
      // 필드를 추가한다.
      // 또한 그 값을 받을 수 있도록 생성자를 변경한다.
      // 따라서 개발자가 직접 필드나 생성자를 정의할 필요가 없다.
      //
      @Override
      public double compute(int money) {
        return money + (money * rate / 100);
      }
    }
    // 또한 개발자는 메서드의 로컬 변수 값을
    // 로컬 클래스의 생성자에 전달하기 위해 직접 작성할 필요가 없다.
    // 컴파일러가 자동으로 추가한다.
    //
    return new InterestImpl();
  }

  public static void main(String[] args) {
    Interest i1 = getInterest(1.5);
    System.out.printf("금액: %.2f\n", i1.compute(1_0000_0000));

    Interest i2 = getInterest(2.5);
    System.out.printf("금액: %.2f\n", i2.compute(1_0000_0000));
  }

}


