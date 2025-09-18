// final 사용법: 파라미터
package com.eomcs.oop.ex06.e;

class G {

  public void m1(final int a) {
    // 파라미터는 메서드가 호출될 때 외부의 값을 받는 용도의 변수다.
    // 메서드 안에서 파라미터 값을 임의로 변경하게 되면
    // 처음 받은 파라미터 값을 사용하지 못하는 상황이 발생한다.
    // 그래서 이런 상황을 피하고자,
    // 보통 실무에서 파라미터를 final로 선언한다.
    //
    //    a = 200; // 컴파일 오류!

    System.out.println(a);
  }
}

public final class Exam0510 {
  public static void main(String[] args) {
    G g = new G();
    g.m1(100);

    System.out.println(sigma(10));
  }

  static int sigma(int value) {
    // 파라미터의 값을 변경하는 예
    int result = 0;
    while (value > 0) {
      result += value--;
    }
    return result;
  }
}
