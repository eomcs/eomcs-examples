// 애노테이션 프로퍼티 값 지정하기 - 프로퍼티 이름 생략
package com.eomcs.annotation.ex4;

//@MyAnnotation // 오류! 기본 값이 설정되어 있지 않기 때문에 반드시 프로퍼터 값을 지정해야 한다.
//@MyAnnotation(value="홍길동") // OK!
@MyAnnotation("홍길동") // OK! 'value' 라는 이름을 가진 프로퍼티는 이름 생략 가능!
public class MyClass {
}
