package effectivejava.ch06.item34.exam10;

import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public enum Operation {
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

  Operation(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return symbol;
  }

  public abstract double apply(double x, double y);

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
