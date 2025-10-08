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

package effectivejava.ch04.item23.exam02;

// [주제] 태그 달린 클래스를 클래스 계층구조로 전환하기

// 1) 계층구조의 루트(root)가 될 추상 클래스를 정의한다.
abstract class Figure {
  abstract double area();
}

// 2) 루트 클래스를 확장한 구체 클래스(concrete class)를 의미별로 하나씩 정의한다.
// - 각 클래스는 간결하고, 쓸데없는 코드는 모두 사라졌다.
// - 각 클래스에 의미있는 필드는 남겨두고 의미없는 필드는 모두 제거했다.
class Circle extends Figure {

  // - 각 하위 클래스에는 각자의 의미에 해당하는 데이터 필드를 넣는다.
  final double radius;

  Circle(double radius) {
    this.radius = radius;
  }

  // - 루트 클래스가 정의한 추상 메서드를 각자의 의미에 맞게 구현한다.
  @Override
  double area() {
    return Math.PI * (radius * radius);
  }
}

class Rectangle extends Figure {

  // - 각 하위 클래스에는 각자의 의미에 해당하는 데이터 필드를 넣는다.
  final double length;
  final double width;

  Rectangle(double length, double width) {
    this.length = length;
    this.width = width;
  }

  // - 루트 클래스가 정의한 추상 메서드를 각자의 의미에 맞게 구현한다.
  @Override
  double area() {
    return length * width;
  }
}

// 3) 타입 사이의 자연스러운 계층 관계를 반영할 수 있다.
// - 유연성은 물론 컴파일타임 타입 검사 능력을 높여준다.
class Square extends Rectangle {
  Square(double side) {
    super(side, side);
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    Rectangle rectangle = new Rectangle(2, 3);
    Circle circle = new Circle(2);
    Square square = new Square(2);

    System.out.println("rectangle area: " + rectangle.area());
    System.out.println("circle area: " + circle.area());
    System.out.println("square area: " + square.area());

    // [정리]
    // - 새로운 클래스를 작성하는 데 태그 필드가 등장한다면,
    //   태그를 없애고 계층구조로 대체하자.
    // - 기존 클래스가 태그를 사용하고 있다면 계층구조로 리팩토링하자.
  }
}
