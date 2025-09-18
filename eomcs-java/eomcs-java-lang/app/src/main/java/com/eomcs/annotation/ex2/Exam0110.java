// 애노테이션 유지 정책 확인
package com.eomcs.annotation.ex2;

//애노테이션 유지 범위 
//=> CLASS
//   - .class 파일까지는 유지된다. 
//   - 그러나 runtime에는 메모리에 로딩되지 않는다.
//   - 애노테이션을 정의할 때 유지 범위를 지정하지 않으면 기본이 CLASS 이다.
//=> SOURCE
//   - 컴파일 할 때 제거된다. 
//   - .class 파일에 포함되지 않는다.
//   - 보통 소스 파일에서 애노테이션 값을 꺼내 다른 파일을 자동 생성하는 도구를 만들 때 많이 사용한다.
//=> RUNTIME
//   - .class 파일까지 유지되고, runtime에 메모리에 로딩된다.
//   - 실행 중에 애노테이션을 참조해야 할 경우에 많이 사용한다.
//

public class Exam0110 {

  public static void main(String[] args) {
    // MyClass.class 파일을 편집기로 열어서 확인해보라!

    // 클래스 정보 객체로부터 애노테이션 정보 추출
    Class<?> clazz = MyClass.class;

    // => 유지정책 : CLASS
    MyAnnotation obj = clazz.getAnnotation(MyAnnotation.class);
    if (obj == null) {
      System.out.println("MyAnnotation을 추출할 수 없습니다!");
    } else {
      // 값을 꺼낼 때는 메서드 호출하듯이 꺼내면 된다.
      System.out.println("MyAnnotation.value=" + obj.value());
    }

    // => 유지정책 : SOURCE
    MyAnnotation2 obj2 = clazz.getAnnotation(MyAnnotation2.class);
    if (obj2 == null) {
      System.out.println("MyAnnotation2를 추출할 수 없습니다!");
    } else {
      System.out.println("MyAnnotation2.value=" + obj2.value());
    }

    // => 유지정책 : RUNTIME
    MyAnnotation3 obj3 = clazz.getAnnotation(MyAnnotation3.class);
    if (obj3 == null) {
      System.out.println("MyAnnotation3를 추출할 수 없습니다!");
    } else {
      System.out.println("MyAnnotation3.value=" + obj3.value());
    }

  }
}


