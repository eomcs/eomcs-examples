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

package effectivejava.ch03.item10.exam05;

// getClass()를 사용해 같은 타입에 대해서만 비교하도록 변경한다.

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

  // 대칭성 및 추이성 위배를 피하기 위해 같은 타입에 대해서만 비교를 수행하도록 변경한다.
  // - instanceof 대신 getClass()를 사용한다.
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

class ColorPoint extends Point {
  private final Color color;

  public ColorPoint(int x, int y, Color color) {
    super(x, y);
    this.color = color;
  }

  @Override
  public boolean equals(Object o) {
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

  // 대칭성을 만족시키기 위해 Point인지 SymbolPoint 인지 따져서 비교한다.
  @Override
  public boolean equals(Object o) {
    if (o == null || o.getClass() != this.getClass()) return false;
    return super.equals(o) && ((SymbolPoint) o).symbol == symbol;
  }
}

public class Test {
  public static void main(String[] args) throws Exception {

    ColorPoint p1 = new ColorPoint(10, 20, Color.RED);
    Point p2 = new Point(10, 20);
    ColorPoint p3 = new ColorPoint(10, 20, Color.RED);
    SymbolPoint p4 = new SymbolPoint(10, 20, '$');

    // Point의 equals() 메서드를 엄격히 같은 타입에 대해서만 비교할 수 있도록 변경한 후에,
    System.out.println(p1.equals(p3)); // true
    System.out.println(p3.equals(p1)); // true

    // 다음은 같은 타입이 아니기 때문에 false를 반환한다.
    System.out.println(p1.equals(p2)); // false
    // - 다른 타입에 대해 비교할 때는 false를 반환한다.
    // - p1 입장에서 비교할 때,
    //   p1은 ColorPoint이고 p2는 Point이기 때문에
    //   색상 필드가 없는 Point 객체와 비교하는 것 자체가 문제가 있다.
    //   따라서 false를 반환하는 것은 옳다.

    // 다음 코드도 마찬가지이다.
    System.out.println(p1.equals(p4)); // false
    System.out.println("---------------------");

    // 대칭성과 추이성은 만족하지만, 문제는 다음이다.
    // 리스코프 치환 원칙(Liskov Substitution Principle)을 위배한다.
    // - 상위 타입을 하위 타입으로 대체할 수 있어야 하는데 안된다는 뜻이다.
    // - 다음 예제를 통해 확인해 보자.
    System.out.println(p2.equals(p1)); // false
    // - p2 입장에서 비교할 때,
    //   p2는 Point이고 p1은 ColorPoint이다.
    //   ColorPoint도 Point의 일종이므로 Point 값과 비교할 수 있어야 한다.
    //   그러나 p2의 타입과 완전히 일치하지 않기 때문에 false를 반환한다.
    // - 이것은 "리스코프 치환 원칙"을 위배하는 것이다.
    // - ColorPoint는 Point의 하위 타입이므로, Point를 대체할 수 있어야 한다.
    //   즉 하위 타입은 상위 타입을 완전히 대체할 수 있어야 한다.

    // 리스코프 치환 원칙을 위배하는 것에 대해 좀 더 자세히 알아 보자.
    // - 다음 예제를 통해 확인해 보자.
  }
}
