// # 아이템 17. 변경 가능성을 최소화하라
// - 불변 클래스란 그 인스턴스의 내부 값을 수정할 수 없는 클래스다.
//   불변 인스턴스에 간직된 정보는 고정되어 객체가 파괴되는 순간까지 절대 달라지지 않는다.
//   - 불변 클래스의 예: String, Wrapper 클래스들, BigInteger, BigDecimal, java.time 패키지의 클래스들
// - 불변 클래스의 장점
//   1. 단순성: 불변 클래스는 설계, 구현, 사용이 단순하다.
//   2. 안전성: 불변 클래스는 내부 상태가 바뀌지 않으므로, 공유해도 안전하다.
//   3. 해시코드 캐싱: 불변 클래스는 해시코드를 캐싱해두고 재사용할 수 있다.
//   4. 방어적 복사 불필요: 불변 클래스는 방어적 복사를 할 필요가 없다.
// - 불변 클래스를 만드는 방법
//   1. 객체의 상태를 변경하는 메서드(변경자)를 제공하지 않는다.
//   2. 클래스를 확장할 수 없도록 한다.
//      하위 클래스에서 부주의하게 혹은 나쁜 의도로 객체의 상태를 변하게 만드는 사태를 막아준다.
//      클래스를 final로 선언하거나 생성자를 private으로 선언한다.
//   3. 모든 필드를 final로 선언한다.
//      시스템이 강제하는 수단을 이용해 설계자의 의도를 명확히 드러내는 방법이다.
//      새로 생성된 인스턴스를 동기화 없이 다른 스레드로 건테도 문제없이 동작하게끔 보장한다.
//      (Java Language Spec. 17.5절 참조)
//   4. 모든 필드를 private으로 선언한다.
//      필드가 참조하는 가변 객체를 클라이언트에서 직접 접근해 변경하는 것을 막아준다.
//      기본 타입 필드나 불변 객체를 참조하는 필드에 대해서는 public final로만 선언해도 불변 객체가 된다.
//      하지만, 추후 내부 표현을 바꾸지 못하는 문제가 있으므로 권하지 않는다.
//   5. 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없도록 한다.
//      클래스에 가변 객체를 참조하는 필드가 하나라도 있다면
//      클라이언트에서 그 객체의 참조를 얻을 수 없도록 해야 한다.
//      이런 필드는 절대 클라이언트가 제공한 객체 참조를 가리키게 해서는 안되며,
//      접근자 메서드가 그 필드를 그대로 반환해서도 안된다.
//      왜? 클라이언트(호출자)가 그 객체를 변경할 수 있기 때문이다. 그러면 불변성이 깨진다.
//      생성자, 접근자, readObject() 메서드 모두에서 방어적 복사를 수행하라.

package effectivejava.ch04.item17.exam01;

// [주제]
// 불변 클래스를 만들기: 클라이언트가 제공한 객체 레퍼런스를 안전하게 저장하기

import java.util.Date;

final class Period {
  private final Date start;
  private final Date end;

  // 생성자에서 클라이언트가 제공한 객체 레퍼런스를 안전하게 저장하는 방법
  public Period(Date start, Date end) {
    // 먼저 복사한 뒤 (외부 참조 차단)
    Date s = new Date(start.getTime());
    Date e = new Date(end.getTime());

    // 복사본으로 불변식 검증 (TOCTOU 회피)
    // - TOCTOU: Time Of Check To Time Of Use
    // - 검사할 때의 상태와 실제로 사용할 때의 상태가 달라져 버리는 경쟁 조건(race)이다.
    //   즉 검사 결과가 유효하다고 믿고 사용했는데, 사용 바로 전에 상태가 바뀌어 안전/정합성이 깨지는 상황을 말한다.
    //   ex) 파일 접근 권한 검사 후 파일에 접근하는 사이에 권한이 바뀌는 경우
    if (s.after(e)) throw new IllegalArgumentException();

    // 복사본 저장
    this.start = s;
    this.end = e;
  }

  // 접근자도 내부 가변 상태를 그대로 내주지 말고 복사본을 반환
  public Date start() {
    return new Date(start.getTime());
  }

  public Date end() {
    return new Date(end.getTime());
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    Date start = Date.from(java.time.Instant.parse("2025-10-03T00:00:00Z"));
    Date end = Date.from(java.time.Instant.parse("2025-10-09T00:00:00Z"));
    Period period = new Period(start, end);

    // 클라이언트가 제공한 객체 레퍼런스를 변경해도
    // Period 객체의 불변식이 깨지지 않는다.
    end.setYear(124); // 2024년으로 변경
    System.out.println(period.end()); // 2025년 출력

    // 접근자가 반환한 객체 레퍼런스를 변경해도
    // Period 객체의 불변식이 깨지지 않는다.
    period.end().setYear(126); // 2026년으로 변경
    System.out.println(period.end()); // 2025년 출력
  }
}
