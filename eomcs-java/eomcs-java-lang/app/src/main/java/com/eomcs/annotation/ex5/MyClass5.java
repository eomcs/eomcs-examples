// 애노테이션 프로퍼티 타입 - 배열 값 지정
package com.eomcs.annotation.ex5;

@MyAnnotation3(
    //    v1={"임꺽정"},
    //    v2={1111},
    //    v3={1.11f}

    // 배열 값이 한 개일 경우 중괄호를 생략할 수 있다.
    v1="임꺽정",
    v2=1111,
    v3=1.11f
    )
public class MyClass5 {
}
