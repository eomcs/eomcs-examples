// # 아이템 34. int 상수 대신 열거 타입을 사용하라
// - 열거 타입은 일정 개수의 상수 값을 정의한 다음, 그 외의 값은 허용하지 않는 타입이다.
// - 열거 타입의 성능은 정수 상수와 별반 다르지 않다.
//   열거 타입을 메모리에 올리는 공간과 초기화하는 시간이 들긴 하지만 체감될 정도는 아니다.
// - 필요한 원소를 컴파일 타임에 다 알 수 있는 상수 집합이라면 열거 타입을 사용하자.
// - 열거 타입에 정의된 상수 개수가 영원히 고정 불변일 필요는 없다.
//   열거 타입은 나중에 상수가 추가돼도 바이너리 수준에서 호환되도록 설계되었다.

package effectivejava.ch06.item34.exam07;

// [주제] toString()이 반환하는 문자열로 열거 타입 상수로 변환해주는 fromString() 추가하기

import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

enum Operation {
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
  },
  MOD("%") {
    public double apply(double x, double y) {
      return x % y;
    }
  };

  private final String symbol;

  Operation(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return symbol;
  }

  public abstract double apply(double x, double y);

  // 이 스태틱 필드는 열거 타입의 모든 상수가 생성된 후에 초기화된다.
  // - 열거 타입 생성자가 실행되는 시점에는 정적 필드들이 초기화되기 전이다.
  //   열거 타입의 각 상수는 해당 열거 타입의 인스턴스를 public static final 필드로 선언한 것이다.
  //   즉 static 필드이기 때문에 생성자에게 같은 열거 타입의 정적 필드에 접근할 수 없다는 제약이 적용된다.
  private static final Map<String, Operation> stringToEnum =
      Stream.of(values())
          .collect(
              toMap(
                  Object::toString, // key: toString()이 반환하는 기호 문자열
                  e -> e // value: 열거 타입 상수 자신
                  ));

  // 파라미터로 심볼을 받아서 그 심볼에 해당하는 열거 타입 상수를 Optional로 감싸서 반환한다.
  public static Optional<Operation> fromString(String symbol) {
    return Optional.ofNullable(stringToEnum.get(symbol));
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    double x = 2.0;
    double y = 4.0;

    // 연산자 심볼로 열거 타입 상수를 얻는다.
    Operation op = Operation.fromString("+").orElseThrow();
    System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));

    op = Operation.fromString("-").orElseThrow();
    System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));

    op = Operation.fromString("^").orElseThrow(); // 런타임 오류! 존재하지 않는 심볼이다.
    System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
  }
}
