// # 아이템 11. equals()를 재정의하려거든 hashCode()도 재정의하라
// - equals()를 재정의할 때 hashCode()도 반드시 재정의해야 한다.
// - hashCode()를 재정의하지 않으면, 해시 기반 컬렉션에서 제대로 동작하지 않는다.

// Object 명세
// - equals()에 사용되는 정보가 변경되지 않았다면, hashCode()는 항상 같은 값을 반환해야 한다.
// - equals()가 true를 반환하는 두 객체에 대해, 같은 hashCode() 값을 반환해야 한다.
// - equals()가 false를 반환하는 두 객체에 대해, 같은 hashCode() 값을 반환할 수도 있다.
//   (하지만, 해시 기반 컬렉션의 성능을 위해, 다른 객체는 다른 값을 반환하라.)

package effectivejava.ch03.item11.exam06;

import java.util.HashMap;
import java.util.Map;
import lombok.EqualsAndHashCode;

// hashCode()를 재정의: 자동 생성 도구 "Lombok" 사용하기
/*
[Lombok 설정 방법]
1. IDE에 Lombok 플러그인을 설치한다.
   - IntelliJ IDEA: Settings > Plugins > Marketplace > Lombok 검색 후 설치
   - Eclipse: Help > Eclipse Marketplace > Lombok 검색 후 설치
   - IDE 재시작
2. build.gradle에 Lombok 플러그인을 추가한다.

   //Gradle 9.1.0 이상: 플러그인만 추가

   plugins {
     id "io.freefair.lombok" version "9.0.0"
   }

   // Gradle 9.1.0 미만: 의존 라이브러리 추가
   dependencies {
     compileOnly("org.projectlombok:lombok:1.18.42")
     annotationProcessor("org.projectlombok:lombok:1.18.42")

     testCompileOnly("org.projectlombok:lombok:1.18.42")
     testAnnotationProcessor("org.projectlombok:lombok:1.18.42")
   }
*/

// Lombok의 @EqualsAndHashCode 애노테이션을 사용하면,
// equals()와 hashCode()를 자동으로 생성해 준다.
// - 핵심 필드가 추가되거나 삭제될 때마다 hashCode()를 수정해야 하는 문제를 해결할 수 있다.
@EqualsAndHashCode
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
}

public class Test {
  public static void main(String[] args) throws Exception {
    PhoneNumber pn1 = new PhoneNumber(707, 867, 5309);
    PhoneNumber pn2 = new PhoneNumber(707, 867, 5309);
    PhoneNumber pn3 = new PhoneNumber(707, 867, 5309);

    // 물리적으로 다른 객체다.
    System.out.println(pn1 == pn2); // false
    System.out.println(pn1 == pn3); // false
    System.out.println(pn2 == pn3); // false
    System.out.println("----------------");

    // 논리적으로 동치인 객체다.
    System.out.println(pn1.equals(pn2)); // true
    System.out.println(pn1.equals(pn3)); // true
    System.out.println(pn2.equals(pn3)); // true
    System.out.println("----------------");

    // 논리적으로 동치인 객체는 같은 해시 코드를 반환해야 한다.
    System.out.println(pn1.hashCode());
    System.out.println(pn2.hashCode());
    System.out.println(pn3.hashCode());
    System.out.println("----------------");

    // HashMap에서 제대로 동작한다.
    Map<PhoneNumber, String> m = new HashMap<>();
    m.put(pn1, "제니");
    System.out.println(m.get(pn3));

    // [결론]
    // - 성능을 높인답시고 해시코드를 계산할 때 핵심 필드를 생략해서는 안된다.
    //   - Java 2 이전에는 String 클래스는 최대 16글자까지만 해시코드를 계산했다.
    //     그 결과, URL처럼 계층적인 이름으로 구성된 경우 앞부분의 글자가 동일하기 때문에
    //     같은 해시 코드가 생성되어 해시 충돌이 자주 발생했다.
    // - hashCode()가 반환하는 값의 생성 규칙을 API 사용자에게 자세히 공표하지 말자.
    //   그래야 클라이언트가 이 갑에 의지하지 않게 되고, 추후에 계산 방식을 바꿀 수도 있다.
  }
}
