// 애노테이션 프로퍼티 타입 - 배열
package com.eomcs.annotation.ex5;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation3 {
  //    String[] v1() default {"가나다"};
  //    int[] v2() default {100};
  //    float[] v3() default {3.14f};

  // 배열 값이 한 개일 경우 중괄호를 생략할 수 있다.
  String[] v1() default "가나다";
  int[] v2() default 100;
  float[] v3() default 3.14f;
}








