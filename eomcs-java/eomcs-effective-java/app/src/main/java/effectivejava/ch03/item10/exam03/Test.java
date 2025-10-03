// # 아이템 10. equals() 는 일반 규약을 지켜 재정의하라
// [재정의하지 말아야 할 상황]
// - 각 인스턴스가 본질적으로 고유하다.
//   - 값을 표현하는 게 아니라 동작하는 객체를 표현하는 클래스인 경우
// - 인스턴스의 '논리적 동치성(logical equality)'을 검사할 일이 없다.
// - 상위 클래스에서 재정의한 equals()가 하위 클래스에도 딱 들어 맞는다.
// - 클래스가 private이거나, package-private이고 equals() 메서드를 호출할 일이 없다.
//
// [equals() 메서드는 동치관계(equivalence relation)를 구현하며, 다음을 만족한다.]
// 1. 반사성(reflexive):
//    - null 이 아닌 모든 참조 값 x에 대해,
//      x.equals(x)는 true여야 한다.
// 2. 대칭성(symmetric):
//    - null 이 아닌 모든 참조 값 x, y에 대해,
//      x.equals(y)가 true이면, y.equals(x)도 true여야 한다.
// 3. 추이성(transitive):
//    - null 이 아닌 모든 참조 값 x, y, z에 대해,
//      x.equals(y)가 true이고, y.equals(z)가 true이면, x.equals(z)도 true여야 한다.
// 4. 일관성(consistency):
//    - null 이 아닌 모든 참조 값 x, y에 대해,
//      x.equals(y)를 반복해서 호출해도 항상 같은 결과를 반환해야 한다.
// 5. null-아님:
//    - null 이 아닌 모든 참조 값 x에 대해,
//      x.equals(null)는 false여야 한다.

package effectivejava.ch03.item10.exam03;

// 대칭성 위배의 상황 확인!

enum Color {
  RED,
  ORANGE,
  YELLOW,
  GREEN,
  BLUE,
  INDIGO,
  VIOLET
}

class Point {
  private final int x;
  private final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Point)) return false;
    Point p = (Point) o;
    return p.x == x && p.y == y;
  }

  // 인스턴스 필드 값을 기반으로 해시코드를 계산한다.
  // 같은 값을 가진 인스턴스는 같은 해시코드를 가진다.
  // hashCode()의 용도는 아이템 11에서 자세히 다룬다.
  // 현재는 equals() 메서드에 집중하자.
  @Override
  public int hashCode() {
    return 31 * x + y;
  }
}

class ColorPoint extends Point {
  private final Color color;

  public ColorPoint(int x, int y, Color color) {
    super(x, y);
    this.color = color;
  }

  // 하위 클래스에 추가한 color 필드의 비교도 추가하기 위해 equals()를 재정의했다.
  // - 상위 클래스의 인스턴스와 비교하려 할 때 대칭성이 깨질 것이다.
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ColorPoint)) return false;
    return super.equals(o) && ((ColorPoint) o).color == color;
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    Point p1 = new Point(10, 20);
    ColorPoint p2 = new ColorPoint(10, 20, Color.RED);

    // Point의 equals()는 x, y 좌표만 비교한다.
    System.out.println(p1.equals(p2)); // true

    // ColorPoint의 equals()는 x, y 좌표와 color까지 비교한다.
    // - p1이 ColorPoint가 아니므로 false를 반환한다.
    System.out.println(p2.equals(p1)); // false

    // [문제점] 대칭성 위배
    // - x.equals(y)가 true 인데, y.equals(x)은 false이다.
    // - 즉, 대칭성이 깨졌다.

    // [해결책]
    // - 다음 예제를 통해 확인해 보자.
  }
}
