// 애노테이션 유지 정책
package com.eomcs.annotation.ex2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 애노테이션의 유지 정책을 지정하지 않으면 기본이 CLASS이다.
// => 컴파일 할 때 .class 파일에 포함된다.
// => 단, 실행할 때 메모리에 로딩되지 않기 때문에 리플랙션 API로 추출할 수 없다.
@Retention(value=RetentionPolicy.CLASS)
public @interface MyAnnotation {
  String value();
}

//애노테이션 유지 범위
//1) SOURCE : 소스 파일에만 남긴다. *.class 파일에 포함 안됨. 즉 컴파일할 때 제거된다.
//2) CLASS (기본) : .class 파일에 포함. 실행할 때 메모리에 로딩 안됨.
//3) RUNTIME : .class 파일에 포함. 실행할 때도 메모리에 로딩됨. 실행 시에 추출할 수 있다.






