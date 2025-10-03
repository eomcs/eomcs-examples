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

package effectivejava.ch03.item10.exam04;

// 대칭성은 만족!
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

  // 대칭성을 만족시키기 위해 Point인지 ColorPoint 인지 따져서 비교한다.
  @Override
  public boolean equals(Object o) {
    // 같은 타입이 아니라면,
    // - 무조건 false를 반환한다.
    if (!(o instanceof Point)) return false;

    // Point 일 때,
    // - x, y 좌표만 비교한다.
    if (!(o instanceof ColorPoint)) return o.equals(this);

    // 같은 ColorPoint 일 때ㅡ
    // - x, y 좌표와 color까지 비교한다.
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
    // 같은 타입이 아니라면,
    // - 무조건 false를 반환한다.
    if (!(o instanceof Point)) return false;

    // Point 일 때,
    // - x, y 좌표만 비교한다.
    if (!(o instanceof SymbolPoint)) return o.equals(this);

    // 같은 ColorPoint 일 때ㅡ
    // - x, y 좌표와 color까지 비교한다.
    return super.equals(o) && ((SymbolPoint) o).symbol == symbol;
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    ColorPoint p1 = new ColorPoint(10, 20, Color.RED);
    Point p2 = new Point(10, 20);
    ColorPoint p3 = new ColorPoint(10, 20, Color.BLUE);

    // 대칭성을 만족한다.
    // ColorPoint의 equals()는 x, y 좌표만 비교한다.
    System.out.println(p1.equals(p2)); // true

    // Point의 equals()는 x, y 좌표를 비교한다.
    System.out.println(p2.equals(p1)); // true
    System.out.println("---------------------");

    // 그런데, 추이성을 위배한다.
    // ColorPoint vs Point: x, y 좌표만 비교한다.
    System.out.println(p1.equals(p2)); // true

    // ColorPoint vs Point: x, y 좌표만 비교한다.
    System.out.println(p2.equals(p3)); // true

    // ColorPoint vs ColorPoint: x, y 좌표 뿐만 아니라 color까지 비교한다.
    System.out.println(p1.equals(p3)); // false

    // [문제점] 추이성 위배
    // - x.equals(y)가 true 이고, y.equals(z)은 true 인데, x.equals(z)는 false이다.
    System.out.println("---------------------");

    // 추이성 위배는 양반이다.
    // 더 큰 심각한 문제는, 무한 재귀에 빠져 StackOverflowError가 발생할 수 있다.
    SymbolPoint p4 = new SymbolPoint(10, 20, '*');

    // 다음 코드는 p1.equals(p4)를 호출하는 과정에서
    // p4.equals(p1)를 호출하고,
    // 다시 p1.equals(p4)를 호출하는 무한 재귀에 빠져 StackOverflowError가 발생한다.
    System.out.println(p1.equals(p4)); // StackOverflowError 예외 발생!

    // [결론] 상속을 통한 확장에서 equals() 재정의는 매우 어렵다.
    // - 객체 지향 언어의 동치관계에서 나타나는 근본 적인 문제다.
    // - 상위 클래스(concrete class)를 확장해 새로운 값을 추가하면서,
    //   equals() 규약을 만족시킬 방법은 존재하지 않는다.
    // - 객체 지향 추상화의 이점을 포기하지 않는 한 불가능하다는 점을 명심하자.

    // [해결책]
    // - 같은 클래스의 객체와 비교할 때만 true를 반환하도록 해보자.
    // - instanceof 연산자 대신 getClass()를 사용하여 엄격히 타입을 검사하는 것이다.
    // - 다음 예제를 통해 확인해 보자.
  }
}
