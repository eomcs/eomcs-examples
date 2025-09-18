// 애노테이션 적용 범위 - 메서드
package com.eomcs.annotation.ex6;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface MyAnnotation3 {
}


