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

package effectivejava.ch03.item10.exam06;

// getClass()를 사용하여 타입을 엄격히 비교할 경우 발생하는,
// 리스코프 치환 원칙의 위배를 좀 더 자세히 살펴보자.

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
    // 오직 같은 타입만 비교한다.
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

class ColorPoint extends Point {
  private final Color color;

  public ColorPoint(int x, int y, Color color) {
    super(x, y);
    this.color = color;
  }

  @Override
  public boolean equals(Object o) {
    // 오직 같은 타입만 비교한다.
    if (o == null || o.getClass() != this.getClass()) return false;
    return super.equals(o) && ((ColorPoint) o).color == color;
  }
}

class SymbolPoint extends Point {
  private final char symbol;

  public SymbolPoint(int x, int y, char symbol) {
    super(x, y);
    this.symbol = symbol;
  }

  @Override
  public boolean equals(Object o) {
    // 오직 같은 타입만 비교한다.
    if (o == null || o.getClass() != this.getClass()) return false;
    return super.equals(o) && ((SymbolPoint) o).symbol == symbol;
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
    System.out.println(pointSet.contains(new ColorPoint(0, 1, Color.RED))); // false
    // 결과는 false이다.
    // - Set의 contains() 메서드는 내부적으로 equals() 메서드를 호출하여 값을 비교한다.
    //   즉 Set에 포함된 Point 객체를 하나씩 꺼내 equals()를 호출하여 대상 값과 같은지 비교한다.
    // - 문제는 Point 클래스의 equals() 메서드가 값을 비교할 때,
    //   getClass()로 타입이 같은 지를 따진다는 점이다.
    // - ColorPoint는 분명히 Point의 하위 타입이지만,
    //   getClass()로 검사하면 다른 타입이므로 인식되기 때문에 false를 반환하는 것이다.

    // [결론] 리스코프 치환 원칙 위배
    // - ColorPoint는 Point의 하위 타입이므로, Point를 대체할 수 있어야 한다.
    // - 그런데, Point의 equals()는 ColorPoint를 다른 타입으로 인식한다.
    // - 즉, ColorPoint를 Point로 사용할 수 없으므로 "리스코프 치환 원칙"을 위배한다.

    // [해결책]
    // - 상속 대신 컴포지션(composition)을 사용하여 기능을 확장한다.
    // - 다음 예제를 통해 확인해 보자.
  }
}
