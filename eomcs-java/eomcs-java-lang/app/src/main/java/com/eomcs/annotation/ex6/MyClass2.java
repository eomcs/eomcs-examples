// 애노테이션 프로퍼티 타입
package com.eomcs.annotation.ex6;

// @MyAnnotation2은 필드에만 붙일 수 있다.
//@MyAnnotation2
public class MyClass2 {

  @MyAnnotation2 int i;
  @MyAnnotation2 static int i2;


  //  @MyAnnotation2
  public void m(/*@MyAnnotation2*/ int p) {
    /*@MyAnnotation2*/ int a;
  }

}
