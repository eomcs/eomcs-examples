// # 아이템 14. Comparable을 구현할지 고려하라
// [Comparable 인터페이스]
// - compareTo 메서드가 선언되어 있다.
// - 단순 동치성 비교에 더해 순서까지 비교할 수 있으며, 제네릭하다.
// - 이 인터페이스를 구현한다는 것은,
//   인스턴스들에 자연적인 순서(natural ordering)가 있음을 뜻한다.
// - 이 인터페이스 구현체의 배열은 Arrays.sort를 사용해 손쉽게 정렬할 수 있다.
// - 검색, 극단값, 자동 정렬되는 컬렉션 관리도 쉽게 할 수 있다.

package effectivejava.ch03.item14.exam01;

// [주제]
// - String 클래스를 통해 Comparable 인터페이스의 compareTo() 사용 예를 확인한다.

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class Test {
  public static void main(String[] args) throws Exception {

    String[] names = {"Kim", "Lee", "Park", "Choi", "Jung", "Kang", "Jo", "Yoon"};
    Set<String> s = new TreeSet<>();

    // TreeSet에 원소를 추가할 때마다, 원소들의 순서를 결정하기 위해
    // TreeSet 내부에서 String 인스턴스에 대해 compareTo()를 호출한다.
    Collections.addAll(s, names);

    System.out.println(s);

    // - 자바 플랫폼 라이브러리의 모든 값 클래스와 열거 타입이 Comparable을 구현하고 했다.
    // - 알파벳, 숫자, 연대 같이 순서가 명확한 값 클래스를 작성한다면
    //   반드시 Comparable 인터페이스를 구현하라.
  }
}
