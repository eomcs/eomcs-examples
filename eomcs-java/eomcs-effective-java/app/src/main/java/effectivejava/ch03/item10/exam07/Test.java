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

package effectivejava.ch03.item10.exam07;

// 컴포지션을 이용하여 기능 확장하기
// - 여기에 뷰 메서드를 제공하여 서로 다른 객체 간에 비교를 수행할 수 있게 한다.

import java.util.Set;

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
    if (o == null || o.getClass() != this.getClass()) return false;
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

// 상속 대신 컴포지션(composition)으로 기능을 확장한다.
class ColorPoint {
  private final Point point;
  private final Color color;

  public ColorPoint(Point point, Color color) {
    this.point = point;
    this.color = color;
  }

  // Point 객체를 반환하는 뷰 메서드를 제공한다.
  // - 이렇게 하면 ColorPoint를 직접적으로 Point를 대체할 수 없지만,
  //   asPoint() 의 리턴 값을 Point로 사용할 수 있다.
  public Point asPoint() {
    return point;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ColorPoint)) return false;
    ColorPoint cp = (ColorPoint) o;
    return cp.point.equals(this.point) && cp.color.equals(color);
  }

  // 해시 값도 Point의 해시 값과 Color의 해시 값을 합성하여 계산한다.
  @Override
  public int hashCode() {
    return 31 * point.hashCode() + color.hashCode();
  }
}

public class Test {
  public static void main(String[] args) throws Exception {

    // 다음은 반지름이 1인 단위 원 위의 점들을 담은 집합이다.
    // - 정수 좌표만 고려한다.
    Set<Point> pointSet =
        Set.of(new Point(1, 0), new Point(0, 1), new Point(-1, 0), new Point(0, -1));

    // 임의의 점이 집합에 포함되어 있는지 검사한다.
    System.out.println(pointSet.contains(new Point(0, 1))); // true
    System.out.println(pointSet.contains(new Point(1, 1))); // false
    System.out.println("---------------------");

    // ColorPoint의 좌표가 Set에 들어 있는지 검사해 보자.
    ColorPoint colorPoint = new ColorPoint(new Point(0, 1), Color.RED);

    // ColorPoint가 Point의 하위 타입은 아니다.
    // 따라서 contains() 메서드에 ColorPoint를 직접 넘길 수 없다.
    // 대신 asPoint() 메서드를 통해 Point를 얻을 수 있다.
    System.out.println(pointSet.contains(colorPoint.asPoint())); // true
    // - ColorPoint가 하위 타입이 아니므로 리스코프 치환 원칙을 위배하지는 않는다.
    // - 단지 equals()의 규약을 준수하면서,
    //   상속이 아닌 방식으로 Point를 확장해 필드를 추가할 수 있다는 사실이다.
    // - 이런 방법도 있다는 것을 알아두자.

    // [결론]
    // - 상속 대신 컴포지션을 사용하여 값을 추가(기능 확장)할 수 있다.
    // - 이 방법은 리스코프 치환 원칙을 위배하지 않으면서도 equals()의 일반 규약을 지킬 수 있다.
    // - 다만, 값을 비교할 때 그에 대한 뷰 메서드를 제공해야 한다는 번거로움이 있다.
  }
}
