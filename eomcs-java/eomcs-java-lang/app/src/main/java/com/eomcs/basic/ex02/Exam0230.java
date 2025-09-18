// Wrapper 클래스 - wrapper 객체 생성
package com.eomcs.basic.ex02;

public class Exam0230 {

  public static void main(String[] args) {

    // new 명령을 사용하여 Integer 객체를 만들면
    // 무조건 새 인스턴스를 생성한다.
    Integer obj1 = new Integer(100); // Heap에 인스턴스 생성
    Integer obj2 = new Integer(100); // Heap에 인스턴스 생성
    System.out.println(obj1 == obj2); // false
    // 설명:
    // => new 연산자를 통해 Integer 객체를 생성하면 Heap에 인스턴스를 생성한다.
    // => 그래서 같은 값이더라도 다른 인스턴스가 생성된다.

    // auto-boxing 으로 Wrapper 객체를 생성할 경우,
    Integer obj3 = 100; // Integer.valueOf(100)
    Integer obj4 = 100; // Integer.valueOf(100);
    System.out.println(obj3 == obj4); // true
    // 설명:
    // => 정수 값이 -128 ~ 127 범위일 경우
    //    자주 사용되는 수이기 때문에
    //    String 리터럴처럼 상수 풀에 Integer 객체를 생성한다.
    // => 메모리를 효율적으로 사용하기 위해
    //    같은 값을 가지는 Integer 객체가 여러 개 존재하지 않게 한다.
    // => 그래서 가능한 이 방법을 사용해야 한다.


    // auto-boxing은 Wrapper 클래스의 valueOf()를 호출하는 코드로 처리된다.
    Integer obj5 = Integer.valueOf(100);
    Integer obj6 = Integer.valueOf(100);
    System.out.println(obj5 == obj6); // true

    // 다음과 같이 auto-boxing으로 생성된 객체와 valueOf()가 리턴한 객체를 비교해 보자!
    System.out.println(obj3 == obj5); // true

    // 주의!
    //   -128 ~ 127 범위를 넘어가는 경우 무조건 새 객체를 만든다.
    // 이유?
    // - 다루는 숫자가 너무 많기 때문에 무조건 상수 풀에 만들기에는
    //   오히려 메모리 낭비가 심해지기 때문이다.
    //   상수풀에 생성된 객체는 JVM이 종료되기 전까지 유지된다.
    //   즉 가비지가 되지 않는다.
    // - 그러나 heap에 생성된 객체는 주소를 잃어 버리면 가비지가 되기 때문에
    //   메모리를 좀 더 효율적으로 사용할 수 있다.
    //
    Integer obj7 = 128;
    Integer obj8 = 128;
    Integer obj9 = 128;
    System.out.println(obj7 == obj8); // false
    System.out.println(obj7 == obj9); // false
    System.out.println(obj8 == obj9); // false

    // 따라서 이렇게 생성된 wrapper 객체의 값을 비교할 때는
    // String 처럼 equals()로 비교하라!
    System.out.println(obj7.equals(obj8));

    // 결론!
    // - wrapper 객체의 값을 비교할 때 == 연산자를 사용하지 말라!
    // - -128 ~ 127 범위 내의 값이라면 == 연산자를 사용하여 비교할 수도 있지만,
    //   매번 비교할 때 마다 범위의 유효성을 생각하는 것이 번거롭다.
    // - 그냥 equals() 메서드를 사용하여 값을 비교하라!
    // - 더 좋은 방법은 auto-unboxing 하여 primitive type 의 값으로 바꾼후에 == 연산자로 비교하라.

    System.out.println(obj7.intValue() == obj8.intValue());
  }
}


