// 애노테이션 중복 사용
package com.eomcs.annotation.ex7;

// @Employees 애노테이션에 @Employee 반복을 정의하였다.
// 그리고 @Employee 애노테이션에 반복할 수 있음을 선언하였다.
// 따라서 다음과 같이 중복 선언할 수 있다.
//

@Employee("홍길동")
@Employee("임꺽정")
public class MyClass2 {

  @Employee("홍길동")
  @Employee("임꺽정")
  public void m1(int p) {}

  @Employee("홍길동")
  @Employee("임꺽정")
  public void m2(int p) {}
}
