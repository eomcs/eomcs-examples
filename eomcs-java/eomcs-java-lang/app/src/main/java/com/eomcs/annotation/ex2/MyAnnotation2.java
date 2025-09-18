// 애노테이션 유지 정책
package com.eomcs.annotation.ex2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 애노테이션 유지 정책을 SOURCE라고 지정하면
// => 해당 애노테이션은 컴파일할 때 제거된다.
@Retention(value=RetentionPolicy.SOURCE)
public @interface MyAnnotation2 {
  String value();
}








