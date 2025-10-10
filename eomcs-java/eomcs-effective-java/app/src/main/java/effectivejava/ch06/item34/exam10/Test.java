// # 아이템 34. int 상수 대신 열거 타입을 사용하라
// - 열거 타입은 일정 개수의 상수 값을 정의한 다음, 그 외의 값은 허용하지 않는 타입이다.
// - 열거 타입의 성능은 정수 상수와 별반 다르지 않다.
//   열거 타입을 메모리에 올리는 공간과 초기화하는 시간이 들긴 하지만 체감될 정도는 아니다.
// - 필요한 원소를 컴파일 타임에 다 알 수 있는 상수 집합이라면 열거 타입을 사용하자.
// - 열거 타입에 정의된 상수 개수가 영원히 고정 불변일 필요는 없다.
//   열거 타입은 나중에 상수가 추가돼도 바이너리 수준에서 호환되도록 설계되었다.

package effectivejava.ch06.item34.exam10;

// [주제] 열거 타입에 switch 문을 활용할 때
// - 기존 열거 타입에 상수별 동작을 혼합해 넣을 때 switch 문이 좋은 선택이다.

public class Test {
  public static Operation inverse(Operation op) {
    switch (op) {
      case PLUS:
        return Operation.MINUS;
      case MINUS:
        return Operation.PLUS;
      case TIMES:
        return Operation.DIVIDE;
      case DIVIDE:
        return Operation.TIMES;

      default:
        throw new AssertionError("Unknown op: " + op);
    }
  }

  public static void main(String[] args) throws Exception {
    double x = 2.0;
    double y = 4.0;
    for (Operation op : Operation.values()) {
      Operation invOp = inverse(op);
      System.out.printf(
          "%f %s %f %s %f = %f%n", x, op, y, invOp, y, invOp.apply(op.apply(x, y), y));
    }
  }
}
