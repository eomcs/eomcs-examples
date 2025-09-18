// 애노테이션 프로퍼티 값 지정하기
package com.eomcs.annotation.ex3;

//@MyAnnotation // 필수 프로퍼티 값을 지정하지 않으면 컴파일 오류!
@MyAnnotation(value="값") // OK!

@MyAnnotation2 // 애노테이션의 프로퍼티 값을 지정하지 않으면 default 값이 사용된다.
//@MyAnnotation2(value="물론 이렇게 프로퍼티 값을 지정해도 된다.")

public class MyClass {
}
