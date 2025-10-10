// # 아이템 34. int 상수 대신 열거 타입을 사용하라
// - 열거 타입은 일정 개수의 상수 값을 정의한 다음, 그 외의 값은 허용하지 않는 타입이다.
// - 열거 타입의 성능은 정수 상수와 별반 다르지 않다.
//   열거 타입을 메모리에 올리는 공간과 초기화하는 시간이 들긴 하지만 체감될 정도는 아니다.
// - 필요한 원소를 컴파일 타임에 다 알 수 있는 상수 집합이라면 열거 타입을 사용하자.
// - 열거 타입에 정의된 상수 개수가 영원히 고정 불변일 필요는 없다.
//   열거 타입은 나중에 상수가 추가돼도 바이너리 수준에서 호환되도록 설계되었다.

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
