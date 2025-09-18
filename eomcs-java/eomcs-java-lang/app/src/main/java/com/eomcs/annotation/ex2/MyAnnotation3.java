// 애노테이션 유지 정책
package com.eomcs.annotation.ex2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 애노테이션 유지 정책을 RUNTIME라고 지정하면
// => 해당 애노테이션은 .class 파일에 포함된다.
// => 실행할 때 메모리에 로딩되기 때문에 리플랙션 API로 추출할 수 있다.
@Retention(value=RetentionPolicy.RUNTIME)
public @interface MyAnnotation3 {
  String value();
}








