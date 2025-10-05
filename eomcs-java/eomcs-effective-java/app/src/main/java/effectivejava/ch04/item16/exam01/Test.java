// # 아이템 16. public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라
// - 패키지 바깥에서 접근할 수 있는 public 클래스
//   - 필드는 private으로 숨기고, 접근자 메서드를 제공하라.
//   - 그래야, 내부 표현 방식을 언제든 바꿀 수 있는 유연성을 얻을 수 있다.
//   - public 클래스가 필드를 공개하는 순간, 이를 사용하는 클라이언트가 생겨날 것이고,
//     내부 표현 방식을 마음대로 바꾸기 어려워진다.
// - package-private 클래스 또는 private 중첩 클래스라면 필드를 공개해도 된다.
//   - 클래스 선언 면에서나 이를 사용하는 클라이언트 코드 면에서나
//     접근자 방식보다 훨씬 깔끔하다.
//   - 이런 클래스를 사용하는 클라이언트도 패키지 안에서만 동작하는 코드이기 때문에
//     클래스의 내부 표현 방식이 바뀌어도 그 영향은 패키지 안에서만 머문다.
//     따라서 패키지 바깥 코드에는 전혀 영향을 끼치지 않는다.
//   - private 중첩 클래스의 경우에는 수정에 영향을 받는 범위는 훨씬 제한된다.
//     즉 바깥 클래스까지만 영향을 끼친다.

package effectivejava.ch04.item16.exam01;

// [주제]
// - private 필드와 접근자 메서드의 전형적인 사용 예

// public 필드의 예:
// - 클래스를 변경하면 이 클래스의 필드를 직접 사용한 모든 클라이언트에 영향을 끼친다.
class Point1 {
  public int x;
  public int y;
}

// private 필드 + 접근자 메서드의 예:
// - 필드의 표현 방식을 바꾸면 접근자 메서드만 변경하면 된다.
// - 접근자 메서드를 사용한 클라이언트는 전혀 영향을 받지 않는다.
class Point2 {
  private final int x;
  private final int y;

  public Point2(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    // 만약 Point1의 필드명이 바뀌면 다음 코드를 모두 바꿔야 한다.
    Point1 p1 = new Point1();
    p1.x = 3; // Point1의 필드를 직접 사용
    p1.y = 4; // Point1의 필드를 직접 사용
    System.out.printf("(%d, %d)%n", p1.x, p1.y); // Point1의 필드를 직접 사용

    // 만약 Point2의 필드명이 바뀌어도 다음 코드는 전혀 바꿀 필요가 없다.
    Point2 p2 = new Point2(3, 4);
    System.out.printf("(%d, %d)%n", p2.getX(), p2.getY()); // 접근자 메서드 사용
  }
}
