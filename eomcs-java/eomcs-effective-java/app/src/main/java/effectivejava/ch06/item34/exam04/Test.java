// # 아이템 34. int 상수 대신 열거 타입을 사용하라
// - 열거 타입은 일정 개수의 상수 값을 정의한 다음, 그 외의 값은 허용하지 않는 타입이다.
//

package effectivejava.ch06.item34.exam04;

// [주제] 값에 따라 분기하는 열거 타입

enum Operation {
  PLUS,
  MINUS,
  TIMES,
  DIVIDE,
  MOD; // 새로운 상수 추가

  // 상수가 뜻하는 연산을 수행하는 메서드
  public double apply(double x, double y) {
    switch (this) {
      case PLUS:
        return x + y;
      case MINUS:
        return x - y;
      case TIMES:
        return x * y;
      case DIVIDE:
        return x / y;
    }
    throw new AssertionError("알 수 없는 연산: " + this);
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    double x = 2.0;
    double y = 4.0;

    Operation op = Operation.PLUS;
    System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));

    op = Operation.MINUS;
    System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));

    // [문제점]
    // - 새로운 상수를 추가하면 해당 case 문을 수정해야 한다.
    // - case 문을 누락시키면 AssertionError가 발생한다.

    op = Operation.MOD;
    System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));

    // [해결책]
    // - 상수 별로 다르게 동작하는 코드를 각 상수에 넣는다.
    // - 다음 예제를 보라.
  }
}
