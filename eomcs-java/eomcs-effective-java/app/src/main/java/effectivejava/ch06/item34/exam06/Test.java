// # 아이템 34. int 상수 대신 열거 타입을 사용하라
// - 열거 타입은 일정 개수의 상수 값을 정의한 다음, 그 외의 값은 허용하지 않는 타입이다.
// - 열거 타입의 성능은 정수 상수와 별반 다르지 않다.
//   열거 타입을 메모리에 올리는 공간과 초기화하는 시간이 들긴 하지만 체감될 정도는 아니다.
// - 필요한 원소를 컴파일 타임에 다 알 수 있는 상수 집합이라면 열거 타입을 사용하자.
// - 열거 타입에 정의된 상수 개수가 영원히 고정 불변일 필요는 없다.
//   열거 타입은 나중에 상수가 추가돼도 바이너리 수준에서 호환되도록 설계되었다.

package effectivejava.ch06.item34.exam06;

// [주제] 상수에 데이터를 연결하기: 메서드 구현 + 데이터 연결 + toString()

enum Operation {
  // 각각의 상수에 데이터를 연결할 수 있다.
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
  }; // 새로운 상수 추가

  // 각 상수의 데이터를 담을 필드를 선언한다.
  private final String symbol;

  // 각 상수의 데이터를 초기화하는 생성자를 선언한다.
  Operation(String symbol) {
    this.symbol = symbol;
  }

  // toString()을 재정의하여 상수의 데이터를 문자열로 반환한다.
  @Override
  public String toString() {
    return symbol;
  }

  // 각 상수가 구현해야 할 추상 메서드를 선언한다.
  public abstract double apply(double x, double y);
}

public class Test {
  public static void main(String[] args) throws Exception {
    double x = 2.0;
    double y = 4.0;

    Operation op = Operation.PLUS;
    System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));

    op = Operation.MINUS;
    System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));

    // 상수를 출력할 때 재정의한 toString()이 호출된다.
    op = Operation.MOD;
    System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
  }
}
