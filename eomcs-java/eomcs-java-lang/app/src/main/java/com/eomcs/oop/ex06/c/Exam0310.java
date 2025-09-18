// 오버라이딩(overriding) - private 메서드 오버라이딩?
package com.eomcs.oop.ex06.c;


public class Exam0310 {

  //멤버의 접근 범위
  //private      : 같은 클래스
  //(default)    : 같은 클래스 + 같은 패키지
  //protected    : 같은 클래스 + 같은 패키지 + 서브 클래스
  //public       : 모두
  //

  static class C {
    //private 접근 범위:
    //=> 현재 클래스
    private void m1() {}

    //(default) 접근 범위:
    //=> 현재 클래스 + 같은 패키지 소속 클래스
    void m2() {}

    //protected 접근 범위:
    //=> 현재 클래스 + 같은 패키지 소속 클래스 + 서브 클래스
    protected void m3() {}

    //public 접근 범위:
    //=> 모두
    public void m4() {}
  }

  static class C2 extends C {

    // 다음은 C의 메서드를 오버라이딩 한 게 아니다!
    // 왜?
    // - C의 m1()은 private이기 때문에 오직 C에서만 사용할 수 있다.
    // - C2에서 접근할 수 없다. 접근할 수 없으니, 오버라이딩은 불가능하다!
    //
    //    @Override
    private void m1() {} // 컴파일 오류!

    // 어? 강사님! @Override 빼니까 m1() 정의할 수 있는데요?
    // => 이건 오버라이딩이 아니라 C2에 새로 메서드가 추가된 것이다.
    // => 오버라이딩이라면 @Override 애노테이션을 붙였을 때 오류가 나지 말아야 한다!
    //
    //  private void m1() {}
    // 결론! 접근 권한이 없는 메서드는 오버라이딩 불가!

    // 다음은 전형적인 오버라이딩의 예이다.
    @Override void m2() {} // OK
    @Override protected void m3() {} // OK
    @Override public void m4() {} // OK
  }

  public static void main(String[] args) {
    // C, C2, C3, C4 클래스의 주석을 확인하라!
  }
}








