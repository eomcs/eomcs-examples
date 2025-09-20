// # 아이템 1. 생성자 대신 정적 팩터리 메서드를 고려하라
//
// ## 장점
// - 이름을 가질 수 있다.
// - 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.
// - 반환 타입의 하위 타입 객체를 반환할 수 있다.
// - 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.
// - 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.
//
// ## 단점
// - 상속을 하려면 public이나 protected 생성자가 필요하다.
// - 정적 팩터리 메서드만 제공하는 클래스는 확장할 수 없다.
// - 정적 팩터리 메서드는 프로그래머가 찾기 어렵다.
// - API 문서에서 정적 팩터리 메서드를 잘 찾아봐야 한다.
// - 따라서 메서드 이름은 널리 알려진 규약을 따라 짓는 것이 좋다.
//   예) `from`, `valueOf`, `of`, `instance` | `getInstance`,
//       `create` | `newInstance`, `getType`, `newType`,  `type`
//       등의 이름을 사용한다.

package effectivejava.ch02.item1;

public class Exam02 {
  public static void main(String[] args) {
    // 장점2: 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.

    before();

    after();

    // [상황]
    // - 불변 클래스(immutable class)에서 유용하다.
    // - 객체 생성 비용이 클 때 고려하라.

    // [관련 기법]
    // - GoF: Flyweight 패턴, Singleton 패턴
  }

  static void before() {
    Boolean b1 = new Boolean(true);
    Boolean b2 = new Boolean(true);
    System.out.println(b1 == b2); // false

    // - 매번 새로운 인스턴스를 생성한다.
    // - 불필요한 객체 생성이 많아진다.
  }

  static void after() {
    Boolean b3 = Boolean.valueOf(true);
    Boolean b4 = Boolean.valueOf(true);
    System.out.println(b3 == b4); // true

    // - 생성한 인스턴스를 cache 해놓고 재활용 할 수 있다.
    // - 불필요한 객체 생성을 피할 수 있다.
    // - 메모리 절약, 성능 향상시킬 수 있다.
  }
}
