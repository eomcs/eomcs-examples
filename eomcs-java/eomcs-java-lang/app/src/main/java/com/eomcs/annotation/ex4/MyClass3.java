// 애노테이션 프로퍼티 값 지정하기 - 프로퍼티 이름 생략
package com.eomcs.annotation.ex4;

//@MyAnnotation3 // 오류!
//@MyAnnotation3(value = "홍길동", tel = "222-2222") // OK!

//@MyAnnotation3(tel = "222-2222", value = "홍길동") // OK!
// => 프로퍼티 값을 설정할 때 순서는 상관없다.

//@MyAnnotation3("홍길동",tel="222-2222") // 오류!
// value 외 다른 프로퍼티 값도 지정할 경우,
// value 이름 생략 불가!
// value 값만 지정할 때 생략 가능!
public class MyClass3 {
}
