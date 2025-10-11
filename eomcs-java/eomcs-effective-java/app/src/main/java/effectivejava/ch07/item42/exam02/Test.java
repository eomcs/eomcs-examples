// # 아이템 42. 익명 클래스보다는 람다를 사용하라
// [자바에서 함수 타입을 표현하는 방법]
// 1) 추상 메서드를 하나만 담은 인터페이스(드물게 추상 클래스)
//    - 이런 인터페이스의 인스턴스를 함수 객체(function object)라고 부른다.
//    - 특정 함수나 동작을 나타내는 데 썼다.
// 2) 익명 클래스로 함수 객체를 구현
//    - 코드가 너무 길기 때문에 함수형 프로그래밍에 적합하지 않았다.
// 3) 람다 표현식(lambda expression)으로 함수형 인터페이스(functional interface)를 구현
//    - 함수형 인터페이스: 추상 메서드를 하나만 담은 인터페이스
//    - 람다 표현식은 익명 클래스보다 코드가 더 간결하다.
//    - 자질구레한 코드들이 사라지고 어떤 동작을 하는지가 명확하게 드러난다.

package effectivejava.ch07.item42.exam02;

import java.util.function.DoubleBinaryOperator;

// [주제] 열거 타입의 각 상수별 메서드 구현에 람다 표현식 사용하기

enum Operation {
  PLUS("+", (x, y) -> x + y),
  MINUS("-", (x, y) -> x - y),
  TIMES("*", (x, y) -> x * y),
  DIVIDE("/", (x, y) -> x / y);

  private final String symbol;
  private final DoubleBinaryOperator op; // 함수 객체를 저장할 필드

  Operation(String symbol, DoubleBinaryOperator op) {
    this.symbol = symbol;
    this.op = op;
  }

  @Override
  public String toString() {
    return symbol;
  }

  public double apply(double x, double y) {
    // 각 상수의 인스턴스 필드에 저장된 함수 객체를 호출한다.
    return op.applyAsDouble(x, y);
  }
}

public class Test {
  public static void main(String[] args) {
    double x = 2.0;
    double y = 4.0;
    for (Operation op : Operation.values()) {
      System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
    }

    // [정리]
    // - 열거 타입의 각 상수별 메서드를 구현할 때 람다 표현식을 사용하면 코드가 훨씬 간결해진다.
    // - 각 상수가 구현해야 할 메서드를 지정하기 위해 추상 메서드를 선언했는데,
    //   이제는 그럴 필요가 없다.
    //   인스턴스 필드에 저장된 함수 객체를 호출하면 된다.

    // [주의할 점]
    // - 람다 표현식은 이름이 없고 문서화도 못한다.
    //   따라서 코드 자체로 동작이 명확히 설명되지 않거나 코드 줄 수가 많아지면 람다를 쓰지 말아야 한다.
    // - 람다는 한 줄일 때 가장 좋다.
    //   세 줄을 넘어가면 가독성이 심하게 나빠진다.
    // - 람다가 길거나 읽기 어렵다면 더 간단히 줄여보거나 람다를 쓰지 않는 쪽으로 리팩토링하라.
    // - 열거 타입의 인스턴스는 런타임에 만들어지기 때문에,
    //   생성자 안의 람다는 열거 타입의 인스턴스 멤버에 접근할 수 없다.
    // - 상수별 동작을 단 몇 줄로 구혀하기 어렵거나, 인스턴스 필드나 메서드를 사용해야만 하는 상황이라면,
    //   상수별 클래스 몸체를 사용해야 한다.

    // [람다를 사용할 수 없는 경우]
    // - 함수형 인터페이스가 아닌 경우나 추상 클래스의 인스턴스를 만들 때 는 람다를 사용할 수 없다.
    //   익명 클래스를 써야 한다.
    // - 추상 메서드가 여러 개인 인터페이스의 인스턴스를 만들 때도 익명 클래스를 써야 한다.
    // - 람다는 자신을 참조할 수 없다.
    //   this 키워드는 바깥 인스턴스를 가리킨다.
    //   익명 클래스에서의 this는 익명 클래스의 인스턴스 자신을 가리킨다.
    //   그래서 함수 객체가 자신을 참조해야 한다면 반드시 익명 클래스를 써야 한다.
  }
}
