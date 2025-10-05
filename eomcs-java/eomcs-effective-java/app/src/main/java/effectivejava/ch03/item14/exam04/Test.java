// # 아이템 14. Comparable을 구현할지 고려하라
// [Comparable 인터페이스]
// - compareTo 메서드가 선언되어 있다.
// - 단순 동치성 비교에 더해 순서까지 비교할 수 있으며, 제네릭하다.
// - 이 인터페이스를 구현한다는 것은,
//   인스턴스들에 자연적인 순서(natural ordering)가 있음을 뜻한다.
// - 이 인터페이스 구현체의 배열은 Arrays.sort를 사용해 손쉽게 정렬할 수 있다.
// - 검색, 극단값, 자동 정렬되는 컬렉션 관리도 쉽게 할 수 있다.

package effectivejava.ch03.item14.exam04;

// [주제]
// - compareTo()와 equals()과 일관되지 않을 때 발생하는 문제를 확인한다.
// - BigDecimal 클래스는 compareTo()와 equals()가 일관되지 않는 대표적인 클래스이다.

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.TreeSet;

public class Test {
  public static void main(String[] args) throws Exception {

    BigDecimal v1 = new BigDecimal("1.0");
    BigDecimal v2 = new BigDecimal("1.00");
    System.out.println(v1.equals(v2)); // false
    System.out.println(v1.compareTo(v2)); // 0
    System.out.println("----------------");

    // HashSet은 equals()를 사용하여 객체를 비교한다.
    HashSet<BigDecimal> hashSet = new HashSet<>();
    hashSet.add(v1);
    hashSet.add(v2); // v1.equals(v2)가 false이므로 추가 저장된다.
    System.out.println(hashSet.size()); // 2

    // TreeSet은 compareTo()를 사용하여 객체를 비교한다.
    TreeSet<BigDecimal> treeSet = new TreeSet<>();
    treeSet.add(v1);
    treeSet.add(v2); // v1.compareTo(v2)가 0이므로, 중복으로 간주하여 저장되지 않는다.
    System.out.println(treeSet.size()); // 1
  }
}
