// # 아이템 14. Comparable을 구현할지 고려하라
// [Comparable 인터페이스]
// - compareTo 메서드가 선언되어 있다.
// - 단순 동치성 비교에 더해 순서까지 비교할 수 있으며, 제네릭하다.
// - 이 인터페이스를 구현한다는 것은,
//   인스턴스들에 자연적인 순서(natural ordering)가 있음을 뜻한다.
// - 이 인터페이스 구현체의 배열은 Arrays.sort를 사용해 손쉽게 정렬할 수 있다.
// - 검색, 극단값, 자동 정렬되는 컬렉션 관리도 쉽게 할 수 있다.

package effectivejava.ch03.item14.exam06;

// [주제]
// - Comparator 생성 메서드를 활용하여 compareTo() 구현하기

import static java.util.Comparator.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.TreeSet;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
final class PhoneNumber implements Cloneable, Comparable<PhoneNumber> {

  private final short areaCode, prefix, lineNum;

  public PhoneNumber(int areaCode, int prefix, int lineNum) {
    this.areaCode = rangeCheck(areaCode, 999, "area code");
    this.prefix = rangeCheck(prefix, 999, "prefix");
    this.lineNum = rangeCheck(lineNum, 9999, "line num");
  }

  private static short rangeCheck(int val, int max, String arg) {
    if (val < 0 || val > max) throw new IllegalArgumentException(arg + ": " + val);
    return (short) val;
  }

  // Comparator 생성 메서드를 이용하여 Comparator를 만든다.
  private static final Comparator<PhoneNumber> COMPARATOR =
      comparingInt((PhoneNumber pn) -> pn.areaCode)
          .thenComparingInt(pn -> pn.prefix)
          .thenComparingInt(pn -> pn.lineNum);

  // - 처음 comparingInt() 메서드를 호출하여 람다를 전달할 때는 파라미터 타입을 명시해야 한다.
  // - 두 번째 thenComparingInt() 메서드를 호출할 때는 파라미터 타입을 생략해도 된다.
  //   자바의 타입 추론 덕분에 컴파일러가 파라미터 타입을 추론할 수 있다.
  // - 세 번째 thenComparingInt() 메서드도 마찬가지다.
  // - 최종적인 리턴 값은 Comparator<PhoneNumber> 인터페이스 구현 객체이다.

  public int compareTo(PhoneNumber pn) {
    // 생성한 Comparator를 이용하여 객체 비교를 수행한다.
    // 이 방법은 compareTo() 메서드의 코드를 훨씬 간결하게 만들어준다.
    return COMPARATOR.compare(this, pn);
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    PhoneNumber[] phoneNumbers = {
      new PhoneNumber(300, 889, 5301),
      new PhoneNumber(900, 111, 1111),
      new PhoneNumber(400, 555, 2225),
      new PhoneNumber(300, 888, 5302),
      new PhoneNumber(400, 555, 2221),
      new PhoneNumber(500, 333, 3333)
    };
    NavigableSet<PhoneNumber> s = new TreeSet<>();
    Collections.addAll(s, phoneNumbers);
    System.out.println(s);

    // [정리]
    // - Comparator는 수많은 보조 생성 메서드들로 구성된 정적 메서드 집합이다.
    //   - byte, short, int: comparingInt(keyExtractor)
    //   - long: comparingLong(keyExtractor)
    //   - float, double: comparingDouble(keyExtractor)
    //   - 레퍼런스: comparing(keyExtractor), comparing(keyExtractor, keyComparator)
    // - 메서드 체이닝을 통해 여러 필드를 차례로 비교할 수 있는 Comparator를 쉽게 만들 수 있다.
    //   단, 약간의 성능 저하가 있다.
    // - 정적 임포트 기능을 이용하면 코드가 훨씬 간결해진다.
    //   before:
    //     Comparator.comparingInt(...)
    //   after:
    //     import static java.util.Comparator.*;
    //     comparingInt(...)
  }
}
