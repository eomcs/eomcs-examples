// 애노테이션 프로퍼티 - 필수 프로퍼티
package com.eomcs.annotation.ex3;

public @interface MyAnnotation {
  String value(); 
  // default 값을 지정하지 않으면 필수 프로퍼티가 된다.
  // 즉 애노테이션을 사용할 때 반드시 값을 지정해야 한다.
}








