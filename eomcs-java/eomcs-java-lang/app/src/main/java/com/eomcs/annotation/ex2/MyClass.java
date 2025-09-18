// 애노테이션 사용
// => @애노테이션이름(프로퍼티명=값, 프로퍼티명=값, ...)
//
package com.eomcs.annotation.ex2;

@MyAnnotation(value="값1") // 유지정책 => CLASS
@MyAnnotation2(value="값2") // 유지정책 => SOURCE
@MyAnnotation3(value="값3") // 유지정책 => RUNTIME
public class MyClass {
}

// 컴파일 한 후 .class 파일을 확인해 보라!
