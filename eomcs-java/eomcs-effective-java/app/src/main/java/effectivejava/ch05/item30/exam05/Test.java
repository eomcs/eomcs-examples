// # 아이템 30. 이왕이면 제네릭 메서드로 만들라
// - 클래스와 마찬가지로 메서드로 제네릭으로 만들 수 있다.
// - 클라이언트에서 입력 파라미터와 반환 값을 명시적으로 형변환하는 것 보다
//   제네릭 메서드를 사용하는 것이 더 안전하고 편리하다.

package effectivejava.ch05.item30.exam05;

// [주제] 재귀적 타입 한정 (recursive type bound)
// - 제네릭 타입 파라미터가 자신과 같은 타입 파라미터를 갖는 타입으로 한정되는 경우
// - Comparable<T> 인터페이스가 대표적인 예이다.
// - 제네릭 메서드에도 재귀적 타입 한정을 적용할 수 있다.

import java.util.Collection;
import java.util.List;
import java.util.Objects;

class Car {
  private final String model;

  public Car(String model) {
    this.model = model;
  }

  @Override
  public String toString() {
    return model;
  }
}

public class Test {

  // <E extends Comparable<E>> 의 의미
  // - E 타입 파라미터는 Comparable<E>를 구현하는 타입으로 한정한다.
  // - 즉 모든 타입 E는 자기 자신과 비교할 수 있어야 함을 의미한다.
  static <E extends Comparable<E>> E max(Collection<E> c) {
    if (c.isEmpty()) {
      throw new IllegalArgumentException("빈 컬렉션");
    }
    E result = null;
    for (E e : c) {
      if (result == null || e.compareTo(result) > 0) {
        result = Objects.requireNonNull(e);
      }
    }
    return result;
  }

  public static void main(String[] args) throws Exception {
    // String 클래스는 Comparable<String>를 구현하고 있다.
    Collection<String> names = List.of("전우치", "임꺾정", "홍길동", "유관순");
    String maxName = max(names);
    System.out.println(maxName);

    // Car 클래스는 Comparable<Car>를 구현하고 있지 않다.
    //    Collection<Car> cars = List.of(new Car("소나타"), new Car("그랜저"), new Car("제네시스"));
    //    Car maxCar = max(cars); // 컴파일 오류!
    //    System.out.println(maxCar);
  }
}
