// # 아이템 30. 이왕이면 제네릭 메서드로 만들라
// - 클래스와 마찬가지로 메서드로 제네릭으로 만들 수 있다.
// - 클라이언트에서 입력 파라미터와 반환 값을 명시적으로 형변환하는 것 보다
//   제네릭 메서드를 사용하는 것이 더 안전하고 편리하다.

package effectivejava.ch05.item30.exam03;

// [주제] 제네릭 메서드 II: 한정적 와일드카드 사용

import java.util.HashSet;
import java.util.Set;

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

class Bus extends Car {
  public Bus(String model) {
    super(model);
  }
}

class Truck extends Car {
  public Truck(String model) {
    super(model);
  }
}

class Sedan extends Car {
  public Sedan(String model) {
    super(model);
  }
}

public class Test {

  // 한정적 와일드카드 타입으로 제네릭 메서드를 정의하면 훨씬 더 유연하게 사용할 수 있다.
  // - 제네릭을 사용하여 세 개 집합의 원소 타입을 모두 같게 지정한 메서드이다.
  // - 컴파일 시 타입 검사를 수행한다.
  static Set<Car> union(Set<? extends Car> s1, Set<? extends Car> s2) {
    Set<Car> result = new HashSet<>();
    for (Car e : s1) {
      result.add(e);
    }
    for (Car e : s2) {
      result.add(e);
    }
    return result;
  }

  public static void main(String[] args) throws Exception {
    Set<Bus> busSet = Set.of(new Bus("버스1"), new Bus("버스2"));
    Set<Truck> truckSet = Set.of(new Truck("트럭1"), new Truck("트럭2"));
    Set<Sedan> sedanSet = Set.of(new Sedan("세단1"), new Sedan("세단2"));

    // 한정적 와일드카드 타입으로 제네릭 메서드를 정의했기 때문에
    // 서로 다른 종류의 집합을 합칠 수 있다.
    Set<Car> result1 = union(busSet, truckSet);
    Set<Car> result2 = union(result1, sedanSet);
    System.out.println(result2);
  }
}
