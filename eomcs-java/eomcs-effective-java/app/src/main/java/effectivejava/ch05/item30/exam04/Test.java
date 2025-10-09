// # 아이템 30. 이왕이면 제네릭 메서드로 만들라
// - 클래스와 마찬가지로 메서드로 제네릭으로 만들 수 있다.
// - 클라이언트에서 입력 파라미터와 반환 값을 명시적으로 형변환하는 것 보다
//   제네릭 메서드를 사용하는 것이 더 안전하고 편리하다.

package effectivejava.ch05.item30.exam04;

// [주제] 제네릭 싱글턴 팩토리 패턴

import java.util.function.UnaryOperator;

public class Test {

  // 항등 함수 준비
  // - 입력 값을 그대로 반환하는 함수
  private static UnaryOperator<Object> INTITY_FN = (t) -> t;

  // 항등 함수를 리턴해주는 팩토리 메서드
  // - 메서드를 호출하는 시점에 T의 타입이 결정된다.
  public static <T> UnaryOperator<T> identityFunction() {
    // INTITY_FN는 모든 타입에 대해 동작하는 UnaryOperator<Object> 타입이다.
    // 컴파일러 입장에서는 컴파일 시점에 T 타입이 뭔지 모르기 때문에
    // UnaryOperator<Object>를 UnaryOperator<T>로 형변환할 수 있는지 판단할 수 없다.
    // 그래서 컴파일러는 형변환 경고를 발생시킨다.
    // 하지만, 우리는 T 타입이 무엇인지 상관없이 INTITY_FN가 잘 동작한다는 것을 알고 있다.
    // 입력 받을 값을 그대로 리턴하기 때문이다.
    // 따라서 @SuppressWarnings("unchecked") 애노테이션을 붙여서
    // 컴파일러 경고를 무시하도록 한다.
    @SuppressWarnings("unchecked")
    UnaryOperator<T> t = (UnaryOperator<T>) INTITY_FN;
    return t;
  }

  public static void main(String[] args) throws Exception {
    String[] names = {"홍길동", "임꺾정", "유관순"};
    UnaryOperator<String> sameString = identityFunction();
    for (String name : names) {
      String ret = sameString.apply(name);
      System.out.println(ret);
    }
    System.out.println("-------------------");

    Number[] numbers = {1, 2.3, 4L};
    UnaryOperator<Number> sameNumber = identityFunction();
    for (Number number : numbers) {
      Number ret = sameNumber.apply(number);
      System.out.println(ret);
    }
  }
}
