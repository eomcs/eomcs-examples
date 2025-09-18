// 애노테이션 프로퍼티 타입
package com.eomcs.annotation.ex6;

// @MyAnnotation4는 로컬 변수에만 붙일 수 있다.
//@MyAnnotation4
public class MyClass4 {

  /*@MyAnnotation4*/ int i;
  /*@MyAnnotation4*/ static int i2;


  //@MyAnnotation4
  public void m(/*@MyAnnotation4*/ int p) {
    @MyAnnotation4 int a;
  }

}
