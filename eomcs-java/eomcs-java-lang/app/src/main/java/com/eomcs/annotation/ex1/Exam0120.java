// 애노테이션 프로퍼티 사용
package com.eomcs.annotation.ex1;

@MyAnnotation2(value="okok1")
public class Exam0120 {

  @MyAnnotation2(value="okok2")
  int a;

  @MyAnnotation2(value="okok3")
  void m() {}
}
