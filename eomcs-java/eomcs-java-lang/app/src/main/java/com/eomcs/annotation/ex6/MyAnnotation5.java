// 애노테이션 적용 범위 - 로컬 변수 + 파라미터 + 필드
package com.eomcs.annotation.ex6;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.LOCAL_VARIABLE, ElementType.PARAMETER, ElementType.FIELD})
public @interface MyAnnotation5 {
}


