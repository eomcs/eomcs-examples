// 애노테이션 적용 범위 - 클래스나 인터페이스
package com.eomcs.annotation.ex6;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

// @Target을 사용하여 애노테이션을 붙일 수 있는 범위를 제어할 수 있다.
//@Target(value = {ElementType.TYPE})  // 클래스나 인터페이스 선언에만 붙일 수 있다.
//@Target(value = ElementType.TYPE)  // 한 개의 값만 설정할 경우 중괄호 생략 가능!
@Target(ElementType.TYPE)  // 프로퍼티 이름이 'value'일 경우 이름 생략 가능!
public @interface MyAnnotation {
}


