// # 아이템 38. 확장할 수 있는 열거 타입이 필요하면 인터페이스를 사용하라
// - 열거 타입은 확장할 수 없다.
//   대부분의 상황에서 열거 타입을 확장하는 것은 필요하지 않지만,
//   그럼에도 불구하고 특별한 상황에서 확장할 수 있는 열거 타입이 필요할 때가 있다.
//   열거 타입이 인터페이스를 구현하는 방법으로 확장을 흉내낼 수 있다.
//
package effectivejava.ch06.item38.exam01;

// [주제] 인터페이스를 이용해 열거 타입 확장을 흉내내기

interface Operation {
  double apply(double x, double y);
}

enum BasicOperation implements Operation {
  PLUS("+") {
    public double apply(double x, double y) {
      return x + y;
    }
  },
  MINUS("-") {
    public double apply(double x, double y) {
      return x - y;
    }
  },
  TIMES("*") {
    public double apply(double x, double y) {
      return x * y;
    }
  },
  DIVIDE("/") {
    public double apply(double x, double y) {
      return x / y;
    }
  };

  private final String symbol;

  BasicOperation(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return symbol;
  }
}

// 열거 타입은 확장할 수 없지만 인터페이스는 확장할 수 있다.
enum ExtendedOperation implements Operation {
  EXP("^") {
    public double apply(double x, double y) {
      return Math.pow(x, y);
    }
  },
  REMAINDER("%") {
    public double apply(double x, double y) {
      return x % y;
    }
  };

  private final String symbol;

  ExtendedOperation(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return symbol;
  }
}

public class Test {
  public static void main(String[] args) {
    double x = 2.0;
    double y = 4.0;

    // 기본 열거 타입에 정의된 상수를 사용하기
    test(BasicOperation.class, x, y);

    // 확장한 열거 타입으로 교체할 수도 있다.
    // 왜? 같은 인터페이스를 구현한 열거 타입이기 때문이다.
    // - 이것이 열거 타입이 인터페이스를 구현한 이유이다.
    test(ExtendedOperation.class, x, y);

    // [정리]
    // - 열거 타입은 확장할 수 없지만 인터페이스는 구현할 수 있다.
    // - 열거 타입이 인터페이스를 구현하게 하면 열거 타입 상수를 인터페이스 구현 객체로 사용할 수 있다.
    // - 인터페이스를 구현한 열거 타입을 여러 개 만들어서,
    //   열거 타입을 확장하는 효과를 얻을 수 있다.
  }

  // test() 파라미터로 받을 타입 토큰은 Enum<T>와 Operation을 모두 만족해야 한다.
  // 즉 T는 열거 타입이면서 Operation 인터페이스를 구현한 타입이어야 한다.
  private static <T extends Enum<T> & Operation> void test(
      Class<T> opEnumType, double x, double y) {
    for (Operation op : opEnumType.getEnumConstants()) {
      // 열거 타입이 Operation 인터페이스를 구현했으므로,
      // 열거 타입 상수를 Operation 의 인스턴스로 간주하여 사용할 수 있다.
      // 이것이 열거 타입이 인터페이스를 구현한 이유이다.
      // 즉 인터페이스 구현 객체로 사용하기 위해서이다.
      System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
    }
  }
}
