// # 아이템 34. int 상수 대신 열거 타입을 사용하라
// - 열거 타입은 일정 개수의 상수 값을 정의한 다음, 그 외의 값은 허용하지 않는 타입이다.
//

package effectivejava.ch06.item34.exam05;

// [주제] 상수별 메서드 구현을 활용한 열거 타입

enum Operation {
  // 각각의 상수는 열거 타입에 선언된 추상 메서드를 구현해야 한다.
  // - 추상 메서드를 구현하지 않으면 컴파일 오류가 발생한다.
  PLUS {
    public double apply(double x, double y) {
      return x + y;
    }
  },
  MINUS {
    public double apply(double x, double y) {
      return x - y;
    }
  },
  TIMES {
    public double apply(double x, double y) {
      return x * y;
    }
  },
  DIVIDE {
    public double apply(double x, double y) {
      return x / y;
    }
  },
  MOD {
    public double apply(double x, double y) {
      return x % y;
    }
  }; // 새로운 상수 추가

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

    // 이제 새로운 상수를 추가하면서,
    // 계산 기능을 구현하지 않는 상황이 발생하지 않는다.
    op = Operation.MOD;
    System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
  }
}
