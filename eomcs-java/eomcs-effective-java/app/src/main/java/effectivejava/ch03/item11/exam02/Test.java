// # 아이템 11. equals()를 재정의하려거든 hashCode()도 재정의하라
// - equals()를 재정의할 때 hashCode()도 반드시 재정의해야 한다.
// - hashCode()를 재정의하지 않으면, 해시 기반 컬렉션에서 제대로 동작하지 않는다.

// Object 명세
// - equals()에 사용되는 정보가 변경되지 않았다면, hashCode()는 항상 같은 값을 반환해야 한다.
// - equals()가 true를 반환하는 두 객체에 대해, 같은 hashCode() 값을 반환해야 한다.
// - equals()가 false를 반환하는 두 객체에 대해, 같은 hashCode() 값을 반환할 수도 있다.
//   (하지만, 해시 기반 컬렉션의 성능을 위해, 다른 객체는 다른 값을 반환하라.)

package effectivejava.ch03.item11.exam02;

import java.util.HashMap;
import java.util.Map;

// hashCode()를 재정의: 단, 고정 값 리턴

final class PhoneNumber {
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

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof PhoneNumber)) return false;
    PhoneNumber pn = (PhoneNumber) o;
    return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
  }

  @Override
  public int hashCode() {
    return 42; // 고정 값 리턴 (나쁜 예제)
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    Map<PhoneNumber, String> m = new HashMap<>();
    m.put(new PhoneNumber(707, 867, 5309), "제니");
    System.out.println(m.get(new PhoneNumber(707, 867, 5309)));

    // - 두 객체 모두 똑 같은 해시 코드를 반환하기 때문에, 버킷에서 찾아 낼 수 있다.
    // [해시 값이 같을 경우]
    // - 버킷 하나에 담겨 마치 연결 리스트처럼 동작한다.
    // - 따라서 평균 수행 시간이 O(1)인 해시 테이블이 O(n)으로 느려져서,
    //   객체가 많아지면 성능이 급격히 떨어진다.
    // - 좋은 해시 함수라면 서로 다른 인스턴스에 다른 해시 값을 반환해야 한다.

    // [해시 값과 버킷]
    // - 해시 테이블은 내부적으로 배열을 사용한다.
    // - 해시 테이블에 객체를 저장할 때, 객체의 hashCode() 메서드를 호출하여 정수 해시 코드를 얻는다.
    // - 이 해시 코드를 배열의 인덱스로 변환하여 객체를 저장할 위치(버킷)를 결정한다.
    //   - 일반적으로 `index = hashCode % array.length` 방식으로 인덱스를 계산한다.
    // - 해시 코드가 동일한 객체들이 같은 버킷에 저장될 수 있다(충돌).
    // - 충돌이 발생하면, 해당 버킷에 연결 리스트나 트리 구조를 사용하여 여러 객체를 저장한다.
    // - 객체를 검색할 때도 동일한 해시 코드를 사용하여 버킷을 찾고,
    //   그 버킷 내에서 equals() 메서드를 사용하여 정확한 객체를 찾는다.
    // - 따라서, hashCode()와 equals() 메서드가 올바르게 구현되어야 해시 테이블이 제대로 동작한다.

    // [HashMap]
    // - Java 8부터 HashMap은 충돌이 많이 발생하는 버킷에 대해,
    //   연결 리스트 대신 트리 구조를 사용하여 성능을 향상시킨다.
    // - 이때 트리 구조는 "레드-블랙 트리"를 사용한다.
    // - 한 버킷에 연결된 노드 수가 8이상이면 레드-블랙 트리로 변환하고,
    //   6이하로 줄어들면 다시 연결 리스트로 되돌린다.

    // [레드-블랙 트리]
    // - 자가 균형 이진 탐색 트리의 일종이다.
    // - 삽입, 삭제, 검색 연산이 모두 O(log n)의 시간 복잡도를 가진다.
    // - 각 노드는 빨강 또는 검정 속성을 두고,
    //   트리의 균형을 유지하기 위한 특정 규칙을 따른다.
    // - 이러한 규칙 덕분에 트리의 높이가 log n 이하로 유지되어,
    //   최악의 경우에도 효율적인 연산이 가능하다.
    // - 자세한 것은 관련 자료구조 책을 참고하라.

    // [문제점]
    // - 모든 PhoneNumber 객체의 해시 값이 같기 때문에 같은 버킷에 저장된다.
    // - 따라서, 해시 테이블의 성능이 O(n)으로 떨어진다.

    // [해결 방법]
    // - 서로 다른 객체에 대해 서로 다른 해시 값을 반환하도록 hashCode()를 재정의하라.
    // - 다음 예제를 통해 확인할 수
  }
}
