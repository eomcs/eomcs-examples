// # 아이템 23. 태그 달린 클래스보다는 클래스 계층구조를 활용하라
// - 태그 달린 클래스(tagged class)란, 한 클래스에 여러 종류의 데이터를 담고,
//   그 종류를 나타내는 태그 필드를 둔 클래스를 말한다.
// - 태그 달린 클래스는 태그에 따라 동작이 달라지는 메서드를 포함한다.
// - 태그 달린 클래스는 장황하고, 오류를 내기 쉽고, 비효율적이다.
//   - 여러 구현이 한 클래스에 혼합돼 있어서 가독성이 떨어지고,
//     다른 의미를 위한 코드도 언제나 함께 하니 메모리도 많이 사용한다.
//   - 필드들을 final로 선언하려면 해당 의미에 쓰이지 않는 필드들까지 생성자에서 초기화해야 한다.
//     쓰지 않는 필드를 초기화하는 불필요한 코드가 늘어난다.
//   - 또 다른 의미를 추가하려면 수정해야 할 코드가 많다.
//   - 인스턴스 타입만으로 현재 나타내는 의미를 알 길이 전혀 없다.
//
// [태그 달린 클래스를 클래스 계층구조로 전환]
// - 계층구조의 루트(root)가 될 추상 클래스를 정의한다.
//   - 태그 값에 따라 동작이 달라지는 메서드들을 추상 메서드로 선언한다.
//   - 태그 값에 상관없이 동작이 일정한 메서드들은 루트 클래스의 일반 메서드로 추가한다.
//   - 모든 하위 클래스에서 공통으로 사용하는 데이터 필드들도 전부 루트 클래스로 올린다.
// - 루트 클래스를 확장한 구체 클래스(concrete class)를 의미별로 하나씩 정의한다.
//   - 각 하위 클래스에는 각자의 의미에 해당하는 데이터 필드를 넣는다.
//   - 루트 클래스가 정의한 추상 메서드를 각자의 의미에 맞게 구현한다.

package effectivejava.ch04.item23.exam01;

// [주제] 태그 달린 클래스와 문제점

// 사각형이나 원 데이터를 저장하고 면적을 계산하는 클래스
// - 태그를 이용해 모양을 구분한다.
// - 모양이 추가되면 필드를 추가하고 생성자를 추가해야 한다.
//   또한 area 메서드도 수정해야 한다.
class Figure {
  // 태그 값을 정의하는 열거 타입
  enum Shape {
    RECTANGLE,
    CIRCLE
  };

  // 태그 필드 - 현재의 모양을 나타낸다.
  final Shape shape;

  // 사각형 데이터를 저장하는 필드
  double length;
  double width;

  // 원 데이터를 저장하는 필드
  double radius;

  // 원 데이터를 초기화시키는 생성자 =
  Figure(double radius) {
    shape = Shape.CIRCLE;
    this.radius = radius;
  }

  // 사각형 데이터를 초기화시키는 생성자
  Figure(double length, double width) {
    shape = Shape.RECTANGLE;
    this.length = length;
    this.width = width;
  }

  // 면적을 계산하는 메서드
  // - 태그 값에 따라 동작이 달라진다.
  double area() {
    switch (shape) {
      case RECTANGLE:
        return length * width;
      case CIRCLE:
        return Math.PI * (radius * radius);
      default:
        throw new AssertionError(shape);
    }
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    Figure rectangle = new Figure(2, 3);
    Figure circle = new Figure(2);
    System.out.println("rectangle area: " + rectangle.area());
    System.out.println("circle area: " + circle.area());
  }
}
