// 애노테이션 프로퍼티 타입 - 배열 값 지정
package com.eomcs.annotation.ex5;

@MyAnnotation3(
    // 배열 값을 지정할 때 중괄호를 사용한다.
    v1 = {"홍길동", "임꺽정", "유관순"},
    v2 = {1000, 2000, 3000, 4000, 5000},
    v3 = {1.12f, 2.23f, 3, 34f})
public class MyClass4 {
}
