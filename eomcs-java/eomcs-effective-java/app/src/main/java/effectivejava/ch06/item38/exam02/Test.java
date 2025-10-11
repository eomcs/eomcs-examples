// # 아이템 38. 확장할 수 있는 열거 타입이 필요하면 인터페이스를 사용하라
// - 열거 타입은 확장할 수 없다.
//   대부분의 상황에서 열거 타입을 확장하는 것은 필요하지 않지만,
//   그럼에도 불구하고 특별한 상황에서 확장할 수 있는 열거 타입이 필요할 때가 있다.
//   열거 타입이 인터페이스를 구현하는 방법으로 확장을 흉내낼 수 있다.
//
package effectivejava.ch06.item38.exam02;

// [주제] Class<T> 타입 토큰 대신에 한정적 와일드카드 타입인 Collection<? extends T>를 사용하기

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

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

    // 열거 타입에서 상수들을 꺼내 List로 전달한다.
    test(Arrays.asList(BasicOperation.values()), x, y);

    // 열거 타입에서 상수들을 꺼내 List로 전달한다.
    test(Arrays.asList(ExtendedOperation.values()), x, y);
    System.out.println("--------------------------");

    // 또는 두 열거 타입의 상수들을 모두 꺼내 하나의 List로 합친다면 List로 전달할 수도 있다.
    List<Operation> allOperations =
        Stream.concat(
                Arrays.stream(BasicOperation.values()), // 기본 열거 타입 상수들
                Arrays.stream(ExtendedOperation.values()) // 확장 열거 타입 상수들
                )
            .collect(toList());
    test(allOperations, x, y);
    // [정리]
    // - test() 메서드가 좀 더 간결해졌다.
  }

  // test() 파라미터로 Collection<? extends Operation>를 사용해도 된다.
  // - 한정적 와일드카드를 사용하면 타입 파라미터를 사용하지 않으니 더 간결하다.
  private static void test(Collection<? extends Operation> opSet, double x, double y) {
    for (Operation op : opSet) {
      // Collection 객체에서 Operation 객체를 꺼내 사용한다.
      System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
    }

    // [인터페이스 구현을 통한 열거 타입 확장 방식의 한계]
    // - 열거 타입끼리 구현을 상속할 수 없다.
    // - 인터페이스에서 default 메서드를 제공하여 구현을 공유할 수 있지만 한계가 있다.
  }
}
