// 기존의 클래스를 손대지 않고 새 기능만 추가한다.
// 어떻게? 상속 문법을 이용한다.
package com.eomcs.oop.ex05.d;

public class Calculator2 extends Calculator {

  // 상속은 기존 코드를 자동으로 복사해오는 것이 아니다! 아니다! 아니다!
  // 아니다! 아니다! 아니다! 아니다! 아니다! 아니다! 아니다!
  /*
  public int result;

  public void plus(int value) {
    this.result += value;
  }

  public void minus(int value) {
    this.result -= value;
  }
   */

  // 새 기능을 추가한다.
  //
  public void multiple(int value) {
    this.result *= value;
  }

  public void divide(int value) {
    this.result /= value;
  }
}
