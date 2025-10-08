// # 아이템 24. 멤버 클래스는 되도록 static으로 만들라
// [중첩 클래스(Nested Class)]
// - 다른 클래스 안에 정의된 클래스이다.
// - 자신을 감싼 클래스에서만 쓰여야 하며, 그 외의 쓰임새가 있다면 top-level 클래스로 만들어야 한다.
// - 중첩 클래스의 종류
//   - 정적 멤버 클래스(static member class)
//   - 내부 클래스(inner class)
//     - 인스턴스 멤버 클래스(instance member class, non-static member class)
//     - 지역 클래스(local class)
//     - 익명 클래스(anonymous class)

package effectivejava.ch04.item24.exam01;

// [주제] 정적 멤버 클래스(static member class) 만들기
// - static으로 선언된 멤버 클래스이다.
// - 바깥 클래스에 대해 다른 정적 멤버와 똑같은 접근 규칙을 적용받는다.
//   예) private으로 선언하면 바깥 클래스만 접근 가능
// - 바깥 클래스와 함께 쓰일 때만 유용한 public 도우미 클래스로 쓰인다.
// - 중첩 클래스의 인스턴스가 바깥 인스턴스와 독립적으로 존재할 때 정적 멤버 클래스로 만든다.
// - 멤버 클래스에서 바깥 인스턴스에 접근할 일이 없다면 무조건 static을 붙여서 정적 멤버 클래스로 만들자.

import effectivejava.ch04.item24.exam01.Calculator.Operation;

// static nested class - public 도우미 클래스
class Calculator {

  // 정적 멤버 클래스
  public static enum Operation {
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE
  }

  // private 정적 멤버 클래스
  private static class Plus {
    public static int execute(int x, int y) {
      return x + y;
    }
  }

  // private 정적 멤버 클래스
  // - 바깥 클래스에서만 접근 가능
  // - 외부에서는 접근 불가
  public static class Minus {
    public static int execute(int x, int y) {
      return x + y;
    }
  }
}

public class Test {
  public static void main(String[] args) throws Exception {

    // public 정적 enum 클래스 사용(O)
    Calculator.Operation op = Calculator.Operation.PLUS;

    // public 정적 멤버 클래스 사용(O)
    int result = Calculator.Minus.execute(100, 200);
    System.out.println("result = " + result);

    // private 정적 멤버 클래스 사용(X)
    //    int result2 = Calculator.Plus.execute(100, 200);
    //    System.out.println("result2 = " + result2);
  }
}
