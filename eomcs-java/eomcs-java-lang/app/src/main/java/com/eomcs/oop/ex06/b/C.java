package com.eomcs.oop.ex06.b;

// 오버로딩 규칙 정리!
//
public class C {

  public void m1() {}

  // 메서드를 찾을 때 이름과 파라미터 타입, 개수로 구분하기 때문에
  // 리턴 타입이 다른 것은 구분할 수 없다.
  //  public int m1() {return 0;} // 컴파일 오류!


  // => 파라미터 타입이 달라야 한다.
  public void m1(float a) {} // OK
  public void m1(byte a) {} // OK
  public int m1(short a) {return 0;} // OK
  public String m1(long a) {return null;} // OK

  // => 파라미터 개수가 달라야 한다.
  public float m1(float a, float b) {return 0f;} // OK
  public void m1(short a, String b) {} // OK

  // => 파라미터 이름이 다른 것으로는 메서드를 구분할 수 없다.
  //  public void m1(float f) {} // 컴파일 오류!

  // => 접근 범위는 상관없다.
  void m1(float a, int b) {} // OK
  private void m1(float a, int b, int c) {} // OK
  protected void m1(float a, int b, int c, String d) {} // OK
}
