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

import java.math.BigInteger;
import java.util.Random;

public class Exam01 {
  public static void main(String[] args) {
    // 장점1: 이름을 가질 수 있다.

    before();

    after();

    // [상황]
    // - 시그니처(signature)가 같은 생성자가 여러 개 필요할 때 고려하라.
  }

  static void before() {
    BigInteger value = new BigInteger(128, new Random());
    System.out.println(value);

    // - 반환될 객체의 특성을 제대로 설명하지 못한다.
  }

  static void after() {
    BigInteger value = BigInteger.probablePrime(128, new Random());
    System.out.println(value);

    // - 반환될 객체의 특성을 쉽게 묘사할 수 있다.
  }
}
