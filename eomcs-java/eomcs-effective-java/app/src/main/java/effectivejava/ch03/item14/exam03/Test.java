// # 아이템 14. Comparable을 구현할지 고려하라
// [Comparable 인터페이스]
// - compareTo 메서드가 선언되어 있다.
// - 단순 동치성 비교에 더해 순서까지 비교할 수 있으며, 제네릭하다.
// - 이 인터페이스를 구현한다는 것은,
//   인스턴스들에 자연적인 순서(natural ordering)가 있음을 뜻한다.
// - 이 인터페이스 구현체의 배열은 Arrays.sort를 사용해 손쉽게 정렬할 수 있다.
// - 검색, 극단값, 자동 정렬되는 컬렉션 관리도 쉽게 할 수 있다.

package effectivejava.ch03.item14.exam03;

// [주제]
// - compareTo()와 equals()과 일관되지 않을 때 발생하는 문제를 확인한다.

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

class MyValue implements Comparable<MyValue> {
  int value;

  MyValue(int value) {
    this.value = value;
  }

  @Override
  public int compareTo(MyValue o) {
    return this.value < o.value ? -1 : (this.value == o.value ? 0 : 1);
  }

  // Object의 equals()를 오버라이딩 하지 않았다.
  // - 따라서 equals()는 인스턴스가 다르면 무조건 false를 반환한다.
}

public class Test {
  public static void main(String[] args) throws Exception {

    // compareTo()와 equals()가 일관된 경우: String 클래스
    String[] names = {"Kim", "Lee", "Park", "Choi", "Jung", "Kang", "Jo", "Yoon"};
    String name = new String("Park");
    System.out.println(name.equals(names[2])); // true
    System.out.println(name.compareTo(names[2])); // 0

    // - ArrayList는 contains() 메서드에서 equals()를 사용하여 객체를 비교한다.
    List<String> list = new ArrayList<>();
    Collections.addAll(list, names);
    System.out.println(list.contains(name)); // true

    // - HashSet도 equals()를 사용하여 객체를 비교한다.
    Set<String> hashSet = new java.util.HashSet<>();
    Collections.addAll(hashSet, names);
    System.out.println(hashSet.contains(name)); // true

    // - TreeSet은 contains() 메서드에서 compareTo()를 사용하여 객체를 비교한다.
    Set<String> treeSet = new TreeSet<>();
    Collections.addAll(treeSet, names);
    System.out.println(treeSet.contains(name)); // true
    System.out.println("-----------------------------");

    // compareTo()와 equals()가 일관되지 않는 경우: MyValue 클래스
    MyValue[] values = {new MyValue(100), new MyValue(200), new MyValue(300)};
    MyValue value = new MyValue(200);
    System.out.println(value.equals(values[1])); // false
    System.out.println(value.compareTo(values[1])); // 0

    // - ArrayList는 contains() 메서드에서 equals()를 사용하여 객체를 비교한다.
    List<MyValue> list2 = new ArrayList<>();
    Collections.addAll(list2, values);
    System.out.println(list2.contains(value)); // false

    // - HashSet도 equals()를 사용하여 객체를 비교한다.
    Set<MyValue> hashSet2 = new java.util.HashSet<>();
    Collections.addAll(hashSet2, values);
    System.out.println(hashSet2.contains(value)); // false

    // - TreeSet은 contains() 메서드에서 compareTo()를 사용하여 객체를 비교한다.
    Set<MyValue> treeSet2 = new TreeSet<>();
    Collections.addAll(treeSet2, values);
    System.out.println(treeSet2.contains(value)); // true

    // [정리] (x.compareTo(y) == 0) == (x.equals(y)) 규약
    // - 네 번째 규약은 필수는 안니지만 지키는 것이 좋다.
    // - 지키지 않으면, 위 예제처럼 컬렉션을 다룰 때 예상치 못한 동작이 발생할 수 있다.
    // - ArrayList 나 HashSet 에서는 객체를 비교할 때 equals()를 사용하지만,
    //   정렬된 컬렉션인 TreeSet 에서는 compareTo()를 사용하기 때문이다.
  }
}
