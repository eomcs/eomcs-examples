// Wrapper 클래스 - wrapper 객체의 값 비교
package com.eomcs.basic.ex02;

public class Exam0232 {
  public static void main(String[] args) {
    // wrapper 객체에 대해 == 연산자를 사용할 때 주의할 점!

    // -128 ~ 127 범위의 값으로 Integer 객체를 만들 때는 
    // 내부적으로 한 개의 인스턴스만 생성한다.
    // 그래서 값을 비교할 때 그냥 인스턴스를 비교해도 된다.
    Integer obj1 = 100;
    Integer obj2 = 100;
    System.out.println(obj1 == obj2); // true

    // 문제는, -128 ~ 127 범위를 벗어나는 경우 무조건 새 인스턴스를 만든다.
    // 따라서 위와 같이 == 연산자로 비교해서는 안된다.
    Integer obj3 = 200;
    Integer obj4 = 200;
    System.out.println(obj3 == obj4); // false

    // 결론!
    // String이나 Wrapper 인스턴스의 값을 비교할 때는 무조건 equals()를 사용하라! 
  }
}


