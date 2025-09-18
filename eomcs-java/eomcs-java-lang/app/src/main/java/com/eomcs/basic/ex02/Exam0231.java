// Wrapper 클래스 - wrapper 객체의 값 비교
package com.eomcs.basic.ex02;

public class Exam0231 {
  public static void main(String[] args) {

    Integer obj1 = Integer.valueOf(100);
    Integer obj2 = 100;
    System.out.println(obj1 == obj2);
    // auto-boxing이나 valueOf()로 생성한 wrapper 객체는
    // constants pool에 오직 한 개만 생성되기 때문에
    // 값을 비교할 때 그냥 == 연산자를 사용하여 인스턴스 주소로 비교해도 된다.
    // 단, -128 ~ 127 범위의 값에 대해서만 적용된다.

    // 그러나 다음과 같이 new 연산자로 만든 wrapper 객체는
    // 값이 같더라도 인스턴스가 따로 생성되기 때문에,
    Integer obj3 = new Integer(100); // Heap에 인스턴스 생성
    Integer obj4 = new Integer(100); // Heap에 인스턴스 생성

    // 다음과 같이 == 연산자를 사용하여 비교하면 안된다.
    System.out.println(obj3 == obj4);

    // String 클래스의 경우처럼
    // Integer 클래스가 오버라이딩한 equals()를 호출해야 한다.
    System.out.println(obj3.equals(obj4));

    // 결론:
    // => wrapper 객체를 생성할 때는 new 를 사용하지 말고,
    //    valueOf() 나 auto-boxing 기능을 이용하라.
    // => 값을 비교할 때는 반드시 equals()를 사용하라!
    //
    // '==' 연산자를 사용하면 안되는가?
    // => auto-boxing으로 객체를 만들 경우
    //    -128 ~ 127 범위 내의 숫자인 경우는 캐시에 보관하기 때문에
    //    같은 값은 같은 객체이지만,
    //    이 범위를 벗어나면 값이 같더라도 객체가 다르다.
    //    따라서 일관성을 위해 값을 비교할 때 equals()를 사용하라!
    //
    // 참고:
    // => 모든 wrapper 클래스는 String 클래스처럼
    //    상속 받은 Object의 equals()를 오버라이딩 하였다.
    // => 즉 인스턴스를 비교하는 것이 아니라 값이 같은지를 비교한다.

  }
}


